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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kazushiki.combat.data.KazushikiApi
import com.kazushiki.combat.ui.theme.KazushikiMuted
import com.kazushiki.combat.ui.theme.KazushikiRed
import com.kazushiki.combat.ui.theme.KazushikiSurface
import com.kazushiki.combat.ui.theme.KazushikiSurfaceAlt
import com.kazushiki.combat.ui.theme.KazushikiWarmWhite
import kotlinx.coroutines.launch

private enum class MainTab(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Filled.Home),
    Train("Train", Icons.Filled.FitnessCenter),
    Coach("Coach", Icons.Filled.Psychology),
    Progress("Progress", Icons.Filled.BarChart)
}

private data class CoachBubble(val fromCoach: Boolean, val text: String)

private data class FightStyle(
    val name: String,
    val description: String,
    val drills: List<String>
)

private val fightStyles = listOf(
    FightStyle(
        "BOXING",
        "Hands, defense, footwork, combinations",
        listOf("Jab · Cross", "Double Jab · Cross", "Jab · Cross · Lead Hook", "Slip · Cross", "Angle Out · Cross")
    ),
    FightStyle(
        "MUAY THAI",
        "Punches, kicks, knees, elbows, checks",
        listOf("Jab · Rear Low Kick", "Cross · Lead Hook · Rear Kick", "Lead Teep · Cross", "Check · Cross · Kick", "Rear Knee · Lead Hook")
    ),
    FightStyle(
        "KICKBOXING",
        "Fast combinations and layered kicking",
        listOf("Jab · Cross · Rear Kick", "Cross · Lead Hook · Lead Kick", "Double Jab · Rear Kick", "Jab Feint · Cross · Kick", "Cross · Hook · Cross")
    ),
    FightStyle(
        "MMA STRIKING",
        "Range, entries, exits, and mixed threats",
        listOf("Jab · Cross · Angle Exit", "Level Feint · Jab · Cross", "Front Kick · Cross · Hook", "Jab · Cross · Low Kick", "Double Feint · Entry")
    )
)

@Composable
fun KazushikiCombatApp() {
    var selected by remember { mutableStateOf(MainTab.Home) }
    var sessionCount by remember { mutableIntStateOf(0) }
    var totalMinutes by remember { mutableIntStateOf(0) }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0D0D0F)) {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selected == tab,
                        onClick = { selected = tab },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = KazushikiRed,
                            selectedTextColor = KazushikiWarmWhite,
                            indicatorColor = Color(0xFF2A1215),
                            unselectedIconColor = KazushikiMuted,
                            unselectedTextColor = KazushikiMuted
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selected) {
                MainTab.Home -> HomeScreen(
                    onTrain = { selected = MainTab.Train },
                    onCoach = { selected = MainTab.Coach }
                )

                MainTab.Train -> TrainScreen(
                    onSessionCompleted = { minutes ->
                        sessionCount += 1
                        totalMinutes += minutes
                        selected = MainTab.Progress
                    }
                )

                MainTab.Coach -> CoachScreen()
                MainTab.Progress -> ProgressScreen(sessionCount = sessionCount, totalMinutes = totalMinutes)
            }
        }
    }
}

@Composable
private fun ScreenHeader(eyebrow: String, title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = eyebrow.uppercase(),
            color = KazushikiRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.4.sp
        )
        Text(
            text = title,
            color = KazushikiWarmWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 32.sp
        )
        Text(
            text = subtitle,
            color = KazushikiMuted,
            fontSize = 15.sp,
            lineHeight = 21.sp
        )
    }
}

@Composable
private fun HomeScreen(onTrain: () -> Unit, onCoach: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenHeader(
                eyebrow = "Kazushiki Combat",
                title = "YOUR FIGHT CAMP.\nIN YOUR POCKET.",
                subtitle = "Boxing, Muay Thai, kickboxing, and MMA striking — built around how you want to train."
            )
        }

        item {
            ActionCard(
                title = "QUICK TRAIN",
                body = "Build a focused striking session around your style, time, and equipment.",
                button = "START TRAINING",
                onClick = onTrain
            )
        }

        item {
            ActionCard(
                title = "AI COACH",
                body = "Short, direct striking feedback powered by the same Kazushiki backend as iPhone.",
                button = "ASK COACH",
                onClick = onCoach
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MiniFeature("4", "FIGHT STYLES", Modifier.weight(1f))
                MiniFeature("10–60", "MIN SESSIONS", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ActionCard(title: String, body: String, button: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
        border = BorderStroke(1.dp, Color(0xFF2B2B2F)),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 19.sp)
            Text(body, color = KazushikiMuted, lineHeight = 20.sp)
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(button, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MiniFeature(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = KazushikiSurfaceAlt),
        border = BorderStroke(1.dp, Color(0xFF303034)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, color = KazushikiRed, fontSize = 25.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(5.dp))
            Text(label, color = KazushikiMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun TrainScreen(onSessionCompleted: (Int) -> Unit) {
    var selectedStyle by remember { mutableStateOf<FightStyle?>(null) }
    var selectedDuration by remember { mutableIntStateOf(20) }
    var selectedEquipment by remember { mutableStateOf("NONE") }
    var workoutStarted by remember { mutableStateOf(false) }

    if (workoutStarted && selectedStyle != null) {
        WorkoutScreen(
            style = selectedStyle!!,
            duration = selectedDuration,
            equipment = selectedEquipment,
            onBack = { workoutStarted = false },
            onComplete = { onSessionCompleted(selectedDuration) }
        )
        return
    }

    if (selectedStyle != null) {
        TrainingSetupScreen(
            style = selectedStyle!!,
            selectedDuration = selectedDuration,
            selectedEquipment = selectedEquipment,
            onDurationChange = { selectedDuration = it },
            onEquipmentChange = { selectedEquipment = it },
            onBack = { selectedStyle = null },
            onStart = { workoutStarted = true }
        )
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            ScreenHeader(
                eyebrow = "Train",
                title = "CHOOSE YOUR STYLE.",
                subtitle = "Pick your striking style, then build a quick session around your time and equipment."
            )
        }

        items(fightStyles) { style ->
            StyleCard(style = style, onClick = { selectedStyle = style })
        }
    }
}

@Composable
private fun StyleCard(style: FightStyle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
        border = BorderStroke(1.dp, Color(0xFF303034)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(KazushikiRed, RoundedCornerShape(99.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(style.name, color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 18.sp)
                Spacer(Modifier.height(3.dp))
                Text(style.description, color = KazushikiMuted, fontSize = 13.sp)
            }
            Text("›", color = KazushikiRed, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun TrainingSetupScreen(
    style: FightStyle,
    selectedDuration: Int,
    selectedEquipment: String,
    onDurationChange: (Int) -> Unit,
    onEquipmentChange: (String) -> Unit,
    onBack: () -> Unit,
    onStart: () -> Unit
) {
    val durations = listOf(10, 20, 30, 45, 60)
    val equipment = listOf("NONE", "HEAVY BAG", "JUMP ROPE", "DUMBBELLS", "FULL GYM")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("‹ CHANGE STYLE", color = KazushikiWarmWhite, fontWeight = FontWeight.Bold)
            }
        }

        item {
            ScreenHeader(
                eyebrow = style.name,
                title = "BUILD YOUR SESSION.",
                subtitle = "Choose how long you want to train and what equipment you have available."
            )
        }

        item { SectionLabel("DURATION") }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                durations.take(3).forEach { minutes ->
                    SelectionButton(
                        text = "$minutes MIN",
                        selected = selectedDuration == minutes,
                        modifier = Modifier.weight(1f),
                        onClick = { onDurationChange(minutes) }
                    )
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                durations.drop(3).forEach { minutes ->
                    SelectionButton(
                        text = "$minutes MIN",
                        selected = selectedDuration == minutes,
                        modifier = Modifier.weight(1f),
                        onClick = { onDurationChange(minutes) }
                    )
                }
                Spacer(Modifier.weight(1f))
            }
        }

        item { SectionLabel("EQUIPMENT") }

        items(equipment) { item ->
            SelectionCard(
                text = item,
                selected = selectedEquipment == item,
                onClick = { onEquipmentChange(item) }
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                Text("BUILD WORKOUT", fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = KazushikiMuted,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp
    )
}

@Composable
private fun SelectionButton(text: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) KazushikiRed else KazushikiSurfaceAlt,
            contentColor = KazushikiWarmWhite
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 4.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
private fun SelectionCard(text: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF321316) else KazushikiSurface
        ),
        border = BorderStroke(1.dp, if (selected) KazushikiRed else Color(0xFF303034)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(if (selected) KazushikiRed else Color(0xFF4A4A50), RoundedCornerShape(99.dp))
            )
            Spacer(Modifier.width(12.dp))
            Text(text, color = KazushikiWarmWhite, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun WorkoutScreen(
    style: FightStyle,
    duration: Int,
    equipment: String,
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    val rounds = when {
        duration <= 10 -> 3
        duration <= 20 -> 4
        duration <= 30 -> 5
        duration <= 45 -> 6
        else -> 7
    }
    val drills = List(rounds) { index -> style.drills[index % style.drills.size] }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiSurfaceAlt),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("‹ EDIT WORKOUT", color = KazushikiWarmWhite, fontWeight = FontWeight.Bold)
            }
        }

        item {
            ScreenHeader(
                eyebrow = "${style.name} · $duration MIN",
                title = "TODAY'S WORK.",
                subtitle = if (equipment == "NONE") "No equipment. Just space to move." else "Equipment: $equipment"
            )
        }

        items(drills.size) { index ->
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
                border = BorderStroke(1.dp, Color(0xFF303034)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("ROUND ${index + 1}", color = KazushikiRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(drills[index], color = KazushikiWarmWhite, fontSize = 18.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(5.dp))
                    Text("Stay relaxed, sharp, and technically clean.", color = KazushikiMuted, fontSize = 13.sp)
                }
            }
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onComplete,
                colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                Text("COMPLETE SESSION", fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun CoachScreen() {
    val messages = remember {
        mutableStateListOf(
            CoachBubble(
                fromCoach = true,
                text = "Tell me what you're working on and I'll give you one clear thing to focus on."
            )
        )
    }
    var input by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun send() {
        val text = input.trim()
        if (text.isBlank() || loading) return
        input = ""
        messages += CoachBubble(false, text)
        loading = true
        scope.launch {
            KazushikiApi.askCoach(text)
                .onSuccess { messages += CoachBubble(true, it) }
                .onFailure { messages += CoachBubble(true, it.message ?: "Coach is unavailable right now.") }
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)) {
            ScreenHeader(
                eyebrow = "Pro",
                title = "AI COACH",
                subtitle = "Android is already talking to our Rork-independent Cloudflare backend."
            )
        }

        HorizontalDivider(color = Color(0xFF28282C))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { message -> CoachMessageBubble(message) }
            if (loading) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = KazushikiRed
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("Coach is thinking...", color = KazushikiMuted, fontSize = 13.sp)
                    }
                }
            }
        }

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = { Text("Ask about your striking...") },
            enabled = !loading,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { send() }),
            trailingIcon = {
                Button(
                    onClick = { send() },
                    enabled = input.isNotBlank() && !loading,
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KazushikiRed)
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KazushikiRed,
                unfocusedBorderColor = Color(0xFF3A3A3F),
                focusedTextColor = KazushikiWarmWhite,
                unfocusedTextColor = KazushikiWarmWhite,
                cursorColor = KazushikiRed
            )
        )
    }
}

@Composable
private fun CoachMessageBubble(message: CoachBubble) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromCoach) Arrangement.Start else Arrangement.End
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.86f),
            colors = CardDefaults.cardColors(
                containerColor = if (message.fromCoach) KazushikiSurfaceAlt else Color(0xFF421317)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(14.dp),
                color = KazushikiWarmWhite,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ProgressScreen(sessionCount: Int, totalMinutes: Int) {
    val streak = if (sessionCount > 0) 1 else 0
    val weeklyGoal = 3

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenHeader(
                eyebrow = "Progress",
                title = "SEE YOUR TRAINING ADD UP.",
                subtitle = "Sessions, minutes, streaks, and review progress will build here as you train."
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ProgressStat(sessionCount.toString(), "SESSIONS", Modifier.weight(1f))
                ProgressStat(totalMinutes.toString(), "MINUTES", Modifier.weight(1f))
                ProgressStat(streak.toString(), "STREAK", Modifier.weight(1f))
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
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("THIS WEEK", color = KazushikiWarmWhite, fontSize = 18.sp, fontWeight = FontWeight.Black)
                    Text(
                        "$sessionCount of $weeklyGoal training sessions",
                        color = KazushikiMuted,
                        fontSize = 14.sp
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        repeat(weeklyGoal) { index ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(7.dp)
                                    .background(
                                        if (index < sessionCount.coerceAtMost(weeklyGoal)) KazushikiRed else Color(0xFF343438),
                                        RoundedCornerShape(99.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }

        item { SectionLabel("RECENT ACTIVITY") }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = KazushikiSurfaceAlt),
                border = BorderStroke(1.dp, Color(0xFF303034)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (sessionCount == 0) "NO SESSIONS YET" else "SESSION COMPLETE",
                        color = KazushikiWarmWhite,
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp
                    )
                    Spacer(Modifier.height(7.dp))
                    Text(
                        if (sessionCount == 0)
                            "Complete your first Quick Train session and your activity will start showing up here."
                        else
                            "Nice work. Your latest training session has been added to your totals.",
                        color = KazushikiMuted,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressStat(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = KazushikiSurface),
        border = BorderStroke(1.dp, Color(0xFF303034)),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, color = KazushikiRed, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(4.dp))
            Text(label, color = KazushikiMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}
