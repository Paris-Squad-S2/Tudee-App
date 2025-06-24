package com.example.tudeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tudeeapp.presentation.TudeeAppScaffold
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<MainViewModel>()
        viewModel.loadPredefinedCategories()
        enableEdgeToEdge()
        setContent {
            TudeeAppScaffold()
        }
    }

}
