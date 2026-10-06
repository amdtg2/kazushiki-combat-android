package com.kazushiki.combat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kazushiki.combat.ui.theme.KazushikiMuted
import com.kazushiki.combat.ui.theme.KazushikiRed
import com.kazushiki.combat.ui.theme.KazushikiSurface
import com.kazushiki.combat.ui.theme.KazushikiSurfaceAlt
import com.kazushiki.combat.ui.theme.KazushikiWarmWhite

@Composable
fun TrainingHubScreen(
    programStore: ProgramProgressStore,
    onQuickTrain: () -> Unit,
    onPrograms: (String?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            ProgramHeader(
                eyebrow = "TRAIN",
                title = "CHOOSE HOW YOU WANT TO TRAIN TODAY.",
                subtitle = "Build a one-off session or continue a structured program."
            )
        }

        if (programStore.joinedPrograms.isNotEmpty()) {
            item {
                Text("CONTINUE TRAINING", color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 18.sp)
            }

            items(programStore.joinedPrograms, key = { it.id }) { program ->
                val next = programStore.nextSession(program)
                val progress = (programStore.progress(program) * 100).toInt()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPrograms(program.id) },
                    colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                    border = BorderStroke(1.dp, Color(0xFF4C1A1E)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(17.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(Color(0xFF2A1215), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("▶", color = KazushikiRed, fontWeight = FontWeight.Black)
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(program.title.uppercase(), color = KazushikiMuted, fontSize = 10.sp, fontWeight = FontWeight.Black)
                            Text(next?.second?.title ?: "Program complete", color = KazushikiWarmWhite, fontSize = 17.sp, fontWeight = FontWeight.Black)
                            Text(
                                if (next != null) "Block ${next.first.number} • Session ${next.second.day} • $progress%"
                                else "12/12 sessions • 100%",
                                color = KazushikiMuted,
                                fontSize = 12.sp
                            )
                        }
                        Text("›", color = KazushikiRed, fontSize = 28.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        item {
            Text("TRAINING MODES", color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 18.sp)
        }

        item {
            TrainingModeCard(
                eyebrow = "QUICK TRAIN",
                title = "Build a workout",
                subtitle = "Choose your style, duration, and equipment for one session.",
                action = "BUILD SESSION",
                onClick = onQuickTrain
            )
        }

        item {
            TrainingModeCard(
                eyebrow = "PROGRAMS",
                title = "Follow a training path",
                subtitle = if (programStore.joinedPrograms.isEmpty())
                    "Choose a 12-session Foundations, Intermediate, or Advanced path."
                else "${programStore.joinedPrograms.size} joined • Browse or manage your plans.",
                action = "VIEW PROGRAMS",
                onClick = { onPrograms(null) }
            )
        }
    }
}

@Composable
private fun TrainingModeCard(
    eyebrow: String,
    title: String,
    subtitle: String,
    action: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
        border = BorderStroke(1.dp, Color(0xFF303034)),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFF2A1215), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(if (eyebrow == "QUICK TRAIN") "⚡" else "▦", color = KazushikiRed, fontSize = 22.sp)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(eyebrow, color = KazushikiRed, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
                Text(title, color = KazushikiWarmWhite, fontSize = 20.sp, fontWeight = FontWeight.Black)
                Text(subtitle, color = KazushikiMuted, fontSize = 13.sp, lineHeight = 18.sp)
                Text(action, color = KazushikiMuted, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            }
            Text("›", color = KazushikiMuted, fontSize = 28.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun ProgramsScreen(
    programStore: ProgramProgressStore,
    initialProgramId: String?,
    onBackToHub: () -> Unit,
    onStartSession: (TrainingProgram, ProgramWeek, ProgramSession) -> Unit
) {
    var selectedProgram by remember(initialProgramId) {
        mutableStateOf(initialProgramId?.let(TrainingProgramLibrary::program))
    }

    selectedProgram?.let { program ->
        ProgramDetailScreen(
            program = program,
            programStore = programStore,
            onBack = { selectedProgram = null },
            onStartSession = { week, session -> onStartSession(program, week, session) }
        )
        return
    }

    ProgramsListScreen(
        programStore = programStore,
        onBack = onBackToHub,
        onProgram = { selectedProgram = it }
    )
}

@Composable
private fun ProgramsListScreen(
    programStore: ProgramProgressStore,
    onBack: () -> Unit,
    onProgram: (TrainingProgram) -> Unit
) {
    val styles = listOf("BOXING", "MUAY THAI", "KICKBOXING", "MMA STRIKING")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item { BackButton("‹ TRAIN", onBack) }
        item {
            ProgramHeader(
                eyebrow = "STRUCTURED TRAINING",
                title = "CHOOSE YOUR STYLE. THEN CHOOSE YOUR LEVEL.",
                subtitle = "Every path has 12 sessions. Train 2, 3, or 4 days per week and keep your progress saved on this device."
            )
        }

        if (programStore.joinedPrograms.isNotEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                    border = BorderStroke(1.dp, Color(0xFF4C1A1E)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("MY PROGRAMS", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
                        programStore.joinedPrograms.forEachIndexed { index, program ->
                            val completed = programStore.completedCount(program)
                            val schedule = programStore.scheduleFor(program)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onProgram(program) }
                                    .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Text(program.title.uppercase(), color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 14.sp)
                                    Text("${schedule.frequency}x/week • ${schedule.daySummary}", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text("$completed/${program.totalSessions} sessions • ${(programStore.progress(program) * 100).toInt()}%", color = KazushikiMuted, fontSize = 11.sp)
                                }
                                Text("›", color = KazushikiMuted, fontSize = 24.sp)
                            }
                            if (index != programStore.joinedPrograms.lastIndex) HorizontalDivider(color = Color(0xFF303034))
                        }
                    }
                }
            }
        }

        styles.forEach { style ->
            item {
                Text(style, color = KazushikiWarmWhite, fontSize = 19.sp, fontWeight = FontWeight.Black)
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                    border = BorderStroke(1.dp, Color(0xFF303034)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        val programs = TrainingProgramLibrary.forStyle(style)
                        programs.forEachIndexed { index, program ->
                            ProgramRow(program, programStore, onProgram)
                            if (index != programs.lastIndex) HorizontalDivider(color = Color(0xFF303034))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgramRow(
    program: TrainingProgram,
    programStore: ProgramProgressStore,
    onProgram: (TrainingProgram) -> Unit
) {
    val joined = programStore.isJoined(program)
    val progress = (programStore.progress(program) * 100).toInt()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProgram(program) }
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(program.level.label, color = KazushikiWarmWhite, fontSize = 14.sp, fontWeight = FontWeight.Black)
                if (joined) Text("JOINED", color = KazushikiRed, fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
            Text(program.tagline, color = KazushikiMuted, fontSize = 12.sp, lineHeight = 17.sp)
            Text(
                if (joined) "12 SESSIONS • ${programStore.scheduleFor(program).frequency}X / WEEK • $progress% DONE"
                else "12 SESSIONS • 2–4X / WEEK",
                color = if (joined && progress > 0) KazushikiRed else KazushikiMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text("›", color = KazushikiMuted, fontSize = 25.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun ProgramDetailScreen(
    program: TrainingProgram,
    programStore: ProgramProgressStore,
    onBack: () -> Unit,
    onStartSession: (ProgramWeek, ProgramSession) -> Unit
) {
    val storedSchedule = programStore.scheduleFor(program)
    var frequency by remember(program.id) { mutableIntStateOf(storedSchedule.frequency) }
    var weekdays by remember(program.id) { mutableStateOf(storedSchedule.weekdays) }
    val completed = programStore.completedCount(program)
    val progress = programStore.progress(program)
    val joined = programStore.isJoined(program)
    val next = programStore.nextSession(program)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item { BackButton("‹ PROGRAMS", onBack) }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                border = BorderStroke(1.dp, Color(0xFF4C1A1E)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(11.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(program.level.label, color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        if (joined) {
                            Text(
                                "JOINED",
                                color = KazushikiWarmWhite,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier
                                    .background(KazushikiRed, RoundedCornerShape(99.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Text(program.title, color = KazushikiWarmWhite, fontSize = 28.sp, lineHeight = 30.sp, fontWeight = FontWeight.Black)
                    Text(program.tagline, color = KazushikiMuted, fontSize = 14.sp, lineHeight = 20.sp)
                    Text("12 SESSIONS • 2–4X / WEEK", color = KazushikiMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("PROGRAM PROGRESS", color = KazushikiMuted, fontSize = 10.sp, fontWeight = FontWeight.Black, modifier = Modifier.weight(1f))
                        Text("$completed/12 • ${(progress * 100).toInt()}%", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black)
                    }
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = KazushikiRed,
                        trackColor = Color(0xFF343438)
                    )
                    Text(program.summary, color = KazushikiMuted, fontSize = 12.sp, lineHeight = 18.sp)
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                border = BorderStroke(1.dp, Color(0xFF303034)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("YOUR TRAINING PACE", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
                    Text("How often do you want to train?", color = KazushikiWarmWhite, fontSize = 19.sp, fontWeight = FontWeight.Black)

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(2, 3, 4).forEach { option ->
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    frequency = option
                                    weekdays = recommendedSchedule(option).weekdays
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (frequency == option) Color(0xFF421317) else KazushikiSurfaceAlt,
                                    contentColor = KazushikiWarmWhite
                                ),
                                border = BorderStroke(1.dp, if (frequency == option) KazushikiRed else Color(0xFF303034)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 3.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(option.toString(), fontWeight = FontWeight.Black, fontSize = 20.sp)
                                    Text(if (option == 3) "RECOMMENDED" else "DAYS/WK", fontSize = 8.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        programWeekdayOrder.forEach { day ->
                            val selected = day in weekdays
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(42.dp)
                                    .background(if (selected) KazushikiRed else KazushikiSurfaceAlt, CircleShape)
                                    .clickable {
                                        weekdays = when {
                                            selected -> weekdays - day
                                            weekdays.size < frequency -> weekdays + day
                                            else -> weekdays
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(veryShortDayLabel(day), color = if (selected) KazushikiWarmWhite else KazushikiMuted, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    Text(
                        if (frequency == 3) "3 days per week keeps the original 4-week pace."
                        else "The 12 workouts stay in order. Only your calendar pace changes.",
                        color = KazushikiMuted,
                        fontSize = 11.sp
                    )

                    if (!joined) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { programStore.join(program, ProgramSchedule(frequency, weekdays)) },
                            enabled = weekdays.size == frequency,
                            colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            Text("JOIN WITH THIS SCHEDULE", fontWeight = FontWeight.Black)
                        }
                    } else {
                        val hasChanges = frequency != storedSchedule.frequency || weekdays != storedSchedule.weekdays
                        if (hasChanges) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { programStore.updateSchedule(program, ProgramSchedule(frequency, weekdays)) },
                                enabled = weekdays.size == frequency,
                                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("SAVE SCHEDULE", fontWeight = FontWeight.Black)
                            }
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { programStore.leave(program) },
                            colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("LEAVE PROGRAM", color = KazushikiMuted, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text("12-SESSION CURRICULUM", color = KazushikiMuted, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
        }

        program.weeks.forEach { week ->
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                    border = BorderStroke(1.dp, Color(0xFF303034)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(17.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        val weekCompleted = week.sessions.count { programStore.isCompleted(it.id) }
                        Text("BLOCK ${week.number} • $weekCompleted/3 COMPLETE", color = KazushikiRed, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        Text(week.title, color = KazushikiWarmWhite, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        Text(week.focus, color = KazushikiMuted, fontSize = 12.sp, lineHeight = 17.sp)
                        HorizontalDivider(color = Color(0xFF303034))

                        week.sessions.forEachIndexed { index, session ->
                            val done = programStore.isCompleted(session.id)
                            val isNext = next?.second?.id == session.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onStartSession(week, session) }
                                    .padding(vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .background(
                                            when {
                                                done -> Color(0xFF173521)
                                                isNext -> Color(0xFF421317)
                                                else -> KazushikiSurfaceAlt
                                            },
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(if (done) "✓" else session.day.toString(), color = if (done) Color(0xFF65D68A) else KazushikiWarmWhite, fontWeight = FontWeight.Black)
                                }
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(session.title, color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 15.sp)
                                        if (isNext && !done) Text("NEXT", color = KazushikiRed, fontSize = 8.sp, fontWeight = FontWeight.Black)
                                    }
                                    Text(session.summary, color = KazushikiMuted, fontSize = 11.sp, lineHeight = 15.sp)
                                    Text("${session.durationMinutes} MIN", color = KazushikiMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                                Text("▶", color = KazushikiRed, fontSize = 13.sp)
                            }
                            if (index != week.sessions.lastIndex) HorizontalDivider(color = Color(0xFF28282C))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgramHeader(eyebrow: String, title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(eyebrow, color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.4.sp)
        Text(title, color = KazushikiWarmWhite, fontSize = 28.sp, lineHeight = 30.sp, fontWeight = FontWeight.Black)
        Text(subtitle, color = KazushikiMuted, fontSize = 14.sp, lineHeight = 20.sp)
    }
}

@Composable
private fun BackButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 9.dp)
    ) {
        Text(label, color = KazushikiWarmWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
