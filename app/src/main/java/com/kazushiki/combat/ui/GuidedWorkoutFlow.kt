package com.kazushiki.combat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay

private enum class WorkoutStage { DRILL, REST, COMPLETE }

private data class GuidedBlock(
    val name: String,
    val cue: String,
    val durationSeconds: Int,
    val restAfterSeconds: Int
)

@Composable
fun GuidedWorkoutFlow(
    styleName: String,
    drillNames: List<String>,
    durationMinutes: Int,
    equipment: String,
    onExit: () -> Unit,
    onCompleted: () -> Unit
) {
    val blocks = remember(styleName, drillNames, durationMinutes, equipment) {
        buildGuidedBlocks(drillNames, durationMinutes, equipment)
    }

    var started by remember { mutableStateOf(false) }
    var stage by remember { mutableStateOf(WorkoutStage.DRILL) }
    var blockIndex by remember { mutableIntStateOf(0) }
    var remainingSeconds by remember { mutableIntStateOf(blocks.firstOrNull()?.durationSeconds ?: 0) }
    var stageDurationSeconds by remember { mutableIntStateOf(blocks.firstOrNull()?.durationSeconds ?: 1) }
    var paused by remember { mutableStateOf(false) }
    var showTechnique by remember { mutableStateOf(false) }
    var resumeAfterTechnique by remember { mutableStateOf(false) }

    val currentBlock = blocks.getOrNull(blockIndex)
    val nextBlock = blocks.getOrNull(blockIndex + 1)

    fun loadDrill(index: Int) {
        blockIndex = index
        stage = WorkoutStage.DRILL
        paused = false
        showTechnique = false
        val block = blocks[index]
        stageDurationSeconds = block.durationSeconds
        remainingSeconds = block.durationSeconds
    }

    fun advanceStage() {
        val block = blocks.getOrNull(blockIndex) ?: run {
            stage = WorkoutStage.COMPLETE
            return
        }

        when (stage) {
            WorkoutStage.DRILL -> {
                if (blockIndex < blocks.lastIndex && block.restAfterSeconds > 0) {
                    stage = WorkoutStage.REST
                    paused = false
                    showTechnique = false
                    stageDurationSeconds = block.restAfterSeconds
                    remainingSeconds = block.restAfterSeconds
                } else if (blockIndex < blocks.lastIndex) {
                    loadDrill(blockIndex + 1)
                } else {
                    stage = WorkoutStage.COMPLETE
                    remainingSeconds = 0
                    paused = false
                }
            }
            WorkoutStage.REST -> {
                if (blockIndex < blocks.lastIndex) loadDrill(blockIndex + 1)
                else stage = WorkoutStage.COMPLETE
            }
            WorkoutStage.COMPLETE -> Unit
        }
    }

    LaunchedEffect(started, stage, blockIndex, remainingSeconds, paused, showTechnique) {
        if (!started || stage == WorkoutStage.COMPLETE || paused || showTechnique) return@LaunchedEffect
        if (remainingSeconds > 0) {
            delay(1_000)
            remainingSeconds -= 1
        } else {
            advanceStage()
        }
    }

    if (!started) {
        WorkoutPreview(
            styleName = styleName,
            durationMinutes = durationMinutes,
            equipment = equipment,
            blocks = blocks,
            onExit = onExit,
            onStart = {
                started = true
                loadDrill(0)
            }
        )
        return
    }

    if (stage == WorkoutStage.COMPLETE) {
        WorkoutComplete(
            durationMinutes = durationMinutes,
            blockCount = blocks.size,
            onDone = onCompleted,
            onRestart = {
                started = true
                loadDrill(0)
            }
        )
        return
    }

    val stageProgress = if (stageDurationSeconds <= 0) 0f
    else (1f - remainingSeconds.toFloat() / stageDurationSeconds.toFloat()).coerceIn(0f, 1f)
    val overallProgress = if (blocks.isEmpty()) 0f else {
        val blockFraction = if (stage == WorkoutStage.REST) 1f else stageProgress
        ((blockIndex.toFloat() + blockFraction) / blocks.size.toFloat()).coerceIn(0f, 1f)
    }
    val percent = (overallProgress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070708))
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                shape = RoundedCornerShape(99.dp),
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Text("✕", color = KazushikiWarmWhite, fontWeight = FontWeight.Black)
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$styleName QUICK TRAIN",
                    color = KazushikiWarmWhite,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
                Text(
                    "BLOCK ${blockIndex + 1} OF ${blocks.size}",
                    color = KazushikiMuted,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }

            Text(
                "$percent%",
                color = KazushikiRed,
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                modifier = Modifier.width(44.dp),
                textAlign = TextAlign.End
            )
        }

        LinearProgressIndicator(
            progress = { overallProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            color = KazushikiRed,
            trackColor = KazushikiSurfaceAlt
        )

        Spacer(Modifier.height(4.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = if (stage == WorkoutStage.REST) Color(0xFF171719) else Color(0xFF111113)
            ),
            border = BorderStroke(
                1.dp,
                if (stage == WorkoutStage.REST) Color(0xFF3A3A3E) else Color(0xFF4C1A1E)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    if (stage == WorkoutStage.REST) "REST" else "TRAIN",
                    color = if (stage == WorkoutStage.REST) KazushikiMuted else KazushikiRed,
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    formatTime(remainingSeconds),
                    color = KazushikiWarmWhite,
                    fontSize = 66.sp,
                    fontWeight = FontWeight.Black
                )

                if (paused) {
                    Text(
                        "PAUSED",
                        color = KazushikiRed,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                if (stage == WorkoutStage.REST) {
                    Text("UP NEXT", color = KazushikiRed, fontWeight = FontWeight.Black, fontSize = 11.sp)
                    Spacer(Modifier.height(7.dp))
                    Text(
                        nextBlock?.name ?: "FINISH",
                        color = KazushikiWarmWhite,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    nextBlock?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            it.cue,
                            color = KazushikiMuted,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        currentBlock?.name.orEmpty(),
                        color = KazushikiWarmWhite,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        currentBlock?.cue.orEmpty(),
                        color = KazushikiMuted,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(18.dp))

                    Button(
                        onClick = {
                            resumeAfterTechnique = !paused
                            if (resumeAfterTechnique) paused = true
                            showTechnique = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A1215)),
                        shape = RoundedCornerShape(99.dp)
                    ) {
                        Text("VIEW TECHNIQUE", color = KazushikiRed, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
            }
        }

        if (showTechnique && currentBlock != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                border = BorderStroke(1.dp, Color(0xFF343438)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "TECHNIQUE NOTES",
                            color = KazushikiRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.4.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                showTechnique = false
                                if (resumeAfterTechnique) paused = false
                                resumeAfterTechnique = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                            shape = RoundedCornerShape(99.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Text("DONE", color = KazushikiWarmWhite, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    Text(currentBlock.name, color = KazushikiWarmWhite, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    Text(
                        techniqueInstructions(currentBlock.name),
                        color = KazushikiMuted,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "Workout paused while you review technique.",
                        color = KazushikiMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { paused = !paused },
                enabled = !showTechnique,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(if (paused) "RESUME" else "PAUSE", color = KazushikiWarmWhite, fontWeight = FontWeight.Black)
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { advanceStage() },
                enabled = !showTechnique,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("SKIP", fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun WorkoutPreview(
    styleName: String,
    durationMinutes: Int,
    equipment: String,
    blocks: List<GuidedBlock>,
    onExit: () -> Unit,
    onStart: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070708)),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("‹ EDIT WORKOUT", color = KazushikiWarmWhite, fontWeight = FontWeight.Bold)
            }
        }

        item {
            Text(
                "$styleName · $durationMinutes MIN",
                color = KazushikiRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.3.sp
            )
            Spacer(Modifier.height(5.dp))
            Text("TODAY'S WORK.", color = KazushikiWarmWhite, fontSize = 30.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(5.dp))
            Text(
                if (equipment == "NONE") "No equipment. Just space to move." else "Equipment: $equipment",
                color = KazushikiMuted,
                fontSize = 14.sp
            )
        }

        itemsIndexed(blocks) { index, block ->
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                border = BorderStroke(1.dp, Color(0xFF303034)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("BLOCK ${index + 1}", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(6.dp))
                    Text(block.name, color = KazushikiWarmWhite, fontSize = 18.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(5.dp))
                    Text(block.cue, color = KazushikiMuted, fontSize = 13.sp, lineHeight = 18.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "${formatTime(block.durationSeconds)} work" + if (block.restAfterSeconds > 0) " · ${block.restAfterSeconds}s rest" else "",
                        color = KazushikiMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text("START WORKOUT", fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun WorkoutComplete(
    durationMinutes: Int,
    blockCount: Int,
    onDone: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070708))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SESSION COMPLETE", color = KazushikiRed, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
        Spacer(Modifier.height(12.dp))
        Text("WORK DONE.", color = KazushikiWarmWhite, fontSize = 38.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.height(10.dp))
        Text(
            "$blockCount blocks · $durationMinutes min session",
            color = KazushikiMuted,
            fontSize = 15.sp
        )
        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDone,
            colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text("DONE", fontWeight = FontWeight.Black)
        }
        Spacer(Modifier.height(10.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text("RESTART", color = KazushikiWarmWhite, fontWeight = FontWeight.Black)
        }
    }
}

private fun buildGuidedBlocks(drills: List<String>, durationMinutes: Int, equipment: String): List<GuidedBlock> {
    val blockCount = when {
        durationMinutes <= 10 -> 3
        durationMinutes <= 20 -> 4
        durationMinutes <= 30 -> 5
        durationMinutes <= 45 -> 6
        else -> 7
    }
    val restSeconds = when {
        durationMinutes <= 20 -> 30
        durationMinutes <= 45 -> 45
        else -> 60
    }
    val restTotal = restSeconds * (blockCount - 1)
    val workTotal = (durationMinutes * 60 - restTotal).coerceAtLeast(blockCount * 45)
    val secondsPerBlock = (workTotal / blockCount).coerceAtLeast(45)

    return List(blockCount) { index ->
        val baseName = drills[index % drills.size]
        val adjustedName = equipmentAdjustedName(baseName, equipment, index)
        GuidedBlock(
            name = adjustedName,
            cue = cueFor(adjustedName, equipment),
            durationSeconds = secondsPerBlock,
            restAfterSeconds = if (index < blockCount - 1) restSeconds else 0
        )
    }
}

private fun selectedEquipment(equipment: String): Set<String> {
    if (equipment == "NONE") return emptySet()
    if (equipment == "FULL GYM") return setOf("FULL GYM")
    return equipment.split(" + ").map { it.trim() }.filter { it.isNotEmpty() }.toSet()
}

private fun equipmentAdjustedName(name: String, equipment: String, index: Int): String {
    val selected = selectedEquipment(equipment)

    if ("FULL GYM" in selected) {
        return when (index) {
            0 -> "Cardio + Rotation Warmup"
            1 -> "Fighter Strength Circuit"
            else -> if (index % 2 == 0) "$name · BAG" else name
        }
    }

    if ("JUMP ROPE" in selected && index == 0) return "Boxer-Step Rope Warmup"

    val dumbbellIndex = if ("JUMP ROPE" in selected) 1 else 0
    if ("DUMBBELLS" in selected && index == dumbbellIndex) return "Fighter Strength Circuit"

    if ("HEAVY BAG" in selected && index % 2 == 0) return "$name · BAG"

    return name
}

private fun cueFor(name: String, equipment: String): String {
    val selected = selectedEquipment(equipment)
    if (name.contains("Rope", ignoreCase = true)) return "Stay light on the feet and keep the shoulders relaxed."
    if (name.contains("Strength", ignoreCase = true) || name.contains("Rotation", ignoreCase = true)) {
        return "Move with control and keep your fighting stance connected."
    }
    if (name.endsWith("· BAG") || "HEAVY BAG" in selected || "FULL GYM" in selected) {
        return "Touch the bag clean, recover your guard, then reset your stance."
    }
    if (name.contains("Kick", ignoreCase = true) || name.contains("Teep", ignoreCase = true)) {
        return "Stay balanced on the support leg and return to stance after every kick."
    }
    if (name.contains("Feint", ignoreCase = true)) return "Sell the first look without overcommitting, then fire the real entry."
    if (name.contains("Slip", ignoreCase = true) || name.contains("Angle", ignoreCase = true)) {
        return "Move your head or feet just enough, then answer from a balanced position."
    }
    return "Stay relaxed, snap the combination clean, and bring every strike back to guard."
}

private fun techniqueInstructions(name: String): String {
    return "Start from a balanced guard. Work through ${name.replace(" · ", " → ")} in order, keep your chin tucked, and finish back in stance before repeating."
}

private fun formatTime(seconds: Int): String {
    val safe = seconds.coerceAtLeast(0)
    return "%d:%02d".format(safe / 60, safe % 60)
}
