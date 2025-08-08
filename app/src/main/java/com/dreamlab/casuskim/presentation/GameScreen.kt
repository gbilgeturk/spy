package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SecondaryGradientButton
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onStartVoting: () -> Unit,
    onSpyWantsToAnswer: () -> Unit
) {
    var secondsLeft by remember { mutableStateOf(viewModel.roundTimeSeconds) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            kotlinx.coroutines.delay(1000)
            secondsLeft--
        }
    }

    val progress = secondsLeft / viewModel.roundTimeSeconds.toFloat()
    val mm = secondsLeft / 60
    val ss = secondsLeft % 60

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .systemBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenPanel(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Tartışma Zamanı", style = MaterialTheme.typography.headlineSmall)

                    // DAİRESEL İLERLEME GÖSTERGESİ
                    Box(
                        modifier = Modifier.size(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = progress,
                            strokeWidth = 10.dp,
                            color = GoldAccent,
                            trackColor = Color(0xFF334155), // arka plan rengi
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = String.format("%02d:%02d", mm, ss),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }

                    Text(
                        "Sorular sorun, casusu bulmaya çalışın.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PrimaryGradientButton(
                    text = "Oylamaya Başla",
                    onClick = onStartVoting,
                    modifier = Modifier.fillMaxWidth()
                )
                SecondaryGradientButton(
                    text = "Casus Tahminde Bulunmak İstiyor",
                    onClick = onSpyWantsToAnswer,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}