package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.GoldAccent
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: GameViewModel
) {
    var minutes by remember { mutableStateOf(viewModel.roundTimeSeconds / 60) }

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Üst başlık + geri butonu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Ayarlar",
                        style = MaterialTheme.typography.headlineMedium,
                        color = GoldAccent
                    )
                }

                Spacer(Modifier.height(24.dp))

                ScreenPanel(Modifier.fillMaxWidth()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tur Süresi: ${minutes} dakika",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Slider(
                            value = minutes.toFloat(),
                            onValueChange = { minutes = it.toInt() },
                            valueRange = 1f..15f,
                            steps = 13 // 1-15 dakika arası
                        )
                    }
                }
            }

            // Kaydet butonu
            PrimaryGradientButton(
                text = "Kaydet",
                onClick = {
                    viewModel.setRoundTime(minutes * 60)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}