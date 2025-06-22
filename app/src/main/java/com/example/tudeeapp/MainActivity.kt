package com.example.tudeeapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.tudeeapp.presentation.navigation.TudeeAppScaffold

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<MainViewModel>()
        viewModel.loadPredefinedCategories()
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            TudeeAppScaffold()
        }
    }

}
