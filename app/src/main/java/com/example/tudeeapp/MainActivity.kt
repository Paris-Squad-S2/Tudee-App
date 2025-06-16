package com.example.tudeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.tudeeapp.presentation.navigation.TudeeNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<MainViewModel>()
        viewModel.loadPredefinedCategories()
        enableEdgeToEdge()
        setContent {
            TudeeNavGraph()
        }
    }

}
