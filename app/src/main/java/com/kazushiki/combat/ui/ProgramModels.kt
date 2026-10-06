package com.kazushiki.combat.ui

enum class ProgramLevel(val label: String) {
    FOUNDATIONS("FOUNDATIONS"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED")
}

data class ProgramSession(
    val id: String,
    val day: Int,
    val title: String,
    val summary: String,
    val durationMinutes: Int,
    val drillIds: List<String>,
    val restAfterSeconds: Int = 30
) {
    val drillNames: List<String>
        get() = drillIds.map(::displayDrillName)
}

data class ProgramWeek(
    val number: Int,
    val title: String,
    val focus: String,
    val sessions: List<ProgramSession>
)

data class TrainingProgram(
    val id: String,
    val title: String,
    val tagline: String,
    val styleName: String,
    val level: ProgramLevel,
    val summary: String,
    val weeks: List<ProgramWeek>
) {
    val sessions: List<ProgramSession> get() = weeks.flatMap { it.sessions }
    val totalSessions: Int get() = sessions.size
}

fun programSession(
    id: String,
    day: Int,
    title: String,
    summary: String,
    minutes: Int,
    vararg drills: String,
    rest: Int = 30
) = ProgramSession(id, day, title, summary, minutes, drills.toList(), rest)

fun programWeek(number: Int, title: String, focus: String, vararg sessions: ProgramSession) =
    ProgramWeek(number, title, focus, sessions.toList())

private fun displayDrillName(id: String): String {
    val overrides = mapOf(
        "boxing.foundations.stance-guard" to "Stance & Guard",
        "boxing.foundations.jab" to "Jab Mechanics",
        "boxing.foundations.cross" to "Cross Mechanics",
        "boxing.foundations.one-two" to "Jab · Cross",
        "muaythai.foundations.stance-guard" to "Thai Stance & Guard",
        "kickboxing.foundations.stance-guard" to "Kickboxing Stance & Guard",
        "mma.foundations.stance-guard" to "MMA Stance & Guard"
    )
    overrides[id]?.let { return it }
    return id.substringAfterLast('.')
        .split('-')
        .joinToString(" ") { token -> token.replaceFirstChar { it.uppercase() } }
}

object TrainingProgramLibrary {
    val all: List<TrainingProgram> by lazy {
        foundationsPrograms() + intermediatePrograms() + advancedPrograms()
    }

    fun forStyle(styleName: String): List<TrainingProgram> =
        all.filter { it.styleName == styleName }.sortedBy { it.level.ordinal }

    fun program(id: String): TrainingProgram? = all.firstOrNull { it.id == id }
}
