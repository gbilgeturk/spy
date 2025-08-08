package com.dreamlab.casuskim

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dreamlab.casuskim.data.local.SettingsDataStore
import com.dreamlab.casuskim.presentation.GameViewModel
import com.dreamlab.casuskim.presentation.GameViewModelFactory
import com.dreamlab.casuskim.presentation.navigation.AppNavHost
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.theme.CasusKimTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kenarlara taşma kontrolü
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        // SharedPreferences ile onboarding flag okuma
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val onboardingSeen = prefs.getBoolean("onboarding_seen", false)

        setContent {
            CasusKimTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val dataStoreManager = remember { SettingsDataStore(context) }
                val viewModel: GameViewModel = viewModel(
                    factory = GameViewModelFactory(dataStoreManager)
                )

                val startDestination = if (onboardingSeen) {
                    Screen.PlayerEntry.route
                } else {
                    Screen.Onboarding.route
                }

                AppNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    startDestination = startDestination,
                    onOnboardingFinished = {
                        prefs.edit().putBoolean("onboarding_seen", true).apply()
                    }
                )
            }
        }
    }
}