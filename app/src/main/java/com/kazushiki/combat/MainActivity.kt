package com.kazushiki.combat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kazushiki.combat.ui.KazushikiCombatApp
import com.kazushiki.combat.ui.theme.KazushikiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KazushikiTheme {
                KazushikiCombatApp()
            }
        }
    }
}
