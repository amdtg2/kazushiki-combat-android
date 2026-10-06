package com.kazushiki.combat.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.ceil

data class ProgramSchedule(
    val frequency: Int,
    val weekdays: Set<Int>
) {
    val daySummary: String
        get() = weekdays.sortedBy(::displayDayOrder).joinToString(" • ") { shortDayLabel(it) }

    fun estimatedWeeks(totalSessions: Int): Int =
        ceil(totalSessions.toDouble() / frequency.coerceAtLeast(1).toDouble()).toInt().coerceAtLeast(1)
}

class ProgramProgressStore(context: Context) {
    private val prefs = context.getSharedPreferences("kazushiki_program_progress", Context.MODE_PRIVATE)

    var joinedProgramIds by mutableStateOf(prefs.getStringSet(KEY_JOINED, emptySet()).orEmpty().toSet())
        private set

    var completedSessionIds by mutableStateOf(prefs.getStringSet(KEY_COMPLETED, emptySet()).orEmpty().toSet())
        private set

    private val schedules = mutableStateMapOf<String, ProgramSchedule>()

    init {
        TrainingProgramLibrary.all.forEach { program ->
            loadSchedule(program.id)?.let { schedules[program.id] = it }
        }
    }

    val joinedPrograms: List<TrainingProgram>
        get() = TrainingProgramLibrary.all.filter { it.id in joinedProgramIds }

    fun isJoined(program: TrainingProgram): Boolean = program.id in joinedProgramIds

    fun join(program: TrainingProgram, schedule: ProgramSchedule) {
        joinedProgramIds = joinedProgramIds + program.id
        schedules[program.id] = schedule
        persistJoined()
        persistSchedule(program.id, schedule)
    }

    fun leave(program: TrainingProgram) {
        joinedProgramIds = joinedProgramIds - program.id
        persistJoined()
        // Keep completed sessions and the last schedule so rejoining never destroys progress.
    }

    fun updateSchedule(program: TrainingProgram, schedule: ProgramSchedule) {
        schedules[program.id] = schedule
        persistSchedule(program.id, schedule)
    }

    fun scheduleFor(program: TrainingProgram): ProgramSchedule =
        schedules[program.id] ?: recommendedSchedule(3)

    fun isCompleted(sessionId: String): Boolean = sessionId in completedSessionIds

    fun complete(sessionId: String) {
        completedSessionIds = completedSessionIds + sessionId
        prefs.edit().putStringSet(KEY_COMPLETED, completedSessionIds).apply()
    }

    fun completedCount(program: TrainingProgram): Int =
        program.sessions.count { isCompleted(it.id) }

    fun progress(program: TrainingProgram): Float =
        if (program.totalSessions == 0) 0f
        else completedCount(program).toFloat() / program.totalSessions.toFloat()

    fun nextSession(program: TrainingProgram): Pair<ProgramWeek, ProgramSession>? {
        program.weeks.forEach { week ->
            week.sessions.firstOrNull { !isCompleted(it.id) }?.let { return week to it }
        }
        return null
    }

    private fun persistJoined() {
        prefs.edit().putStringSet(KEY_JOINED, joinedProgramIds).apply()
    }

    private fun persistSchedule(programId: String, schedule: ProgramSchedule) {
        prefs.edit()
            .putInt("schedule_${programId}_frequency", schedule.frequency)
            .putString("schedule_${programId}_days", schedule.weekdays.sorted().joinToString(","))
            .apply()
    }

    private fun loadSchedule(programId: String): ProgramSchedule? {
        val daysRaw = prefs.getString("schedule_${programId}_days", null) ?: return null
        val days = daysRaw.split(',').mapNotNull { it.toIntOrNull() }.toSet()
        if (days.isEmpty()) return null
        val frequency = prefs.getInt("schedule_${programId}_frequency", days.size).coerceIn(2, 4)
        return ProgramSchedule(frequency = frequency, weekdays = days)
    }

    companion object {
        private const val KEY_JOINED = "joined_programs"
        private const val KEY_COMPLETED = "completed_program_sessions"
    }
}

fun recommendedSchedule(frequency: Int): ProgramSchedule {
    val safe = frequency.coerceIn(2, 4)
    val days = when (safe) {
        2 -> setOf(3, 7)       // Tue / Sat
        4 -> setOf(2, 3, 5, 7) // Mon / Tue / Thu / Sat
        else -> setOf(2, 4, 6) // Mon / Wed / Fri
    }
    return ProgramSchedule(safe, days)
}

val programWeekdayOrder: List<Int> = listOf(2, 3, 4, 5, 6, 7, 1)

fun veryShortDayLabel(day: Int): String = when (day) {
    1 -> "S"
    2 -> "M"
    3 -> "T"
    4 -> "W"
    5 -> "T"
    6 -> "F"
    7 -> "S"
    else -> "?"
}

fun shortDayLabel(day: Int): String = when (day) {
    1 -> "Sun"
    2 -> "Mon"
    3 -> "Tue"
    4 -> "Wed"
    5 -> "Thu"
    6 -> "Fri"
    7 -> "Sat"
    else -> "?"
}

private fun displayDayOrder(day: Int): Int = programWeekdayOrder.indexOf(day).let { if (it < 0) Int.MAX_VALUE else it }
