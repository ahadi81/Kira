package com.ahadi.kira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ahadi.kira.navigation.AppNavHost
import com.ahadi.kira.ui.theme.KiraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KiraTheme {
                AppNavHost()
            }
        }
    }
}
