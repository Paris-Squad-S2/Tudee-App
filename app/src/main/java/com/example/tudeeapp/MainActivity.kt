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
import com.example.tudeeapp.presentation.navigation.TudeeNavGraph

class MainActivity : ComponentActivity() {

    val viewModel by viewModel<MainViewModel>()

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadingResources()
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            TudeeNavGraph()
        }
    }

    private fun loadingResources() {
        viewModel.mainState.value.let {
            if (it.isSuccess) {
                finish()
            }
        }
    }


}
