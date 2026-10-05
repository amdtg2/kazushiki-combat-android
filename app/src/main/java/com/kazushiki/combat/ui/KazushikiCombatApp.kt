package com.kazushiki.combat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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

@Composable
fun KazushikiCombatApp() {
    var selected by remember { mutableStateOf(MainTab.Home) }

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
                MainTab.Home -> HomeScreen(onTrain = { selected = MainTab.Train }, onCoach = { selected = MainTab.Coach })
                MainTab.Train -> TrainScreen()
                MainTab.Coach -> CoachScreen()
                MainTab.Progress -> ProgressScreen()
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

        item {
            Text(
                text = "ANDROID PORT FOUNDATION",
                color = KazushikiMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp
            )
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
private fun TrainScreen() {
    val styles = listOf(
        "BOXING" to "Hands, defense, footwork, combinations",
        "MUAY THAI" to "Punches, kicks, knees, elbows, checks",
        "KICKBOXING" to "Fast combinations and layered kicking",
        "MMA STRIKING" to "Range, entries, exits, and mixed threats"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            ScreenHeader(
                eyebrow = "Train",
                title = "CHOOSE YOUR STYLE.",
                subtitle = "This Android foundation will mirror the proven iPhone training library rather than redesign it."
            )
        }
        items(styles) { (name, description) ->
            Card(
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
                    Column {
                        Text(name, color = KazushikiWarmWhite, fontWeight = FontWeight.Black, fontSize = 18.sp)
                        Spacer(Modifier.height(3.dp))
                        Text(description, color = KazushikiMuted, fontSize = 13.sp)
                    }
                }
            }
        }
        item {
            Text(
                "Next porting milestone: copy the stable iOS drills, programs, equipment logic, guided workout flow, and motion demos.",
                color = KazushikiMuted,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
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
private fun ProgressScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ScreenHeader(
                eyebrow = "Progress",
                title = "SEE YOUR TRAINING ADD UP.",
                subtitle = "Android will mirror the iPhone training history, streaks, and AI review progress experience."
            )
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("0", color = KazushikiRed, fontSize = 44.sp, fontWeight = FontWeight.Black)
                    Text("ANDROID SESSIONS", color = KazushikiMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Once the training library is ported, session history will persist locally just like the iPhone build.",
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
