package com.space.movieapp.ui.screen

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.space.movieapp.navigation.MainScreen
import com.space.movieapp.ui.vm.MainActivityVm
import com.space.ui.theme.MovieAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val vm: MainActivityVm by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            vm.state.value.isLoading
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT)
        )
        setContent {
            val state by vm.state.collectAsStateWithLifecycle()
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MovieAppTheme.colors.background
                ) {
                    if (!state.isLoading) {
                        MainScreen(isOnline = state.isOnline)
                    }
                }
            }
        }
    }
}