package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.*
import com.dreamlab.casuskim.util.gradientBackground

@Composable
fun ResultScreen(
    players: List<Player>,
    spyGuessedRight: Boolean,
    onNewGame: () -> Unit
) {
    val spy = players.find { it.isSpy }
    val location = players.firstOrNull { !it.isSpy }?.roleLocation ?: "Bilinmiyor"

    val title = when {
        spyGuessedRight -> "Casus Kazandı!"
        else -> "Sivil Takım Kazandı!"
    }

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .systemBarsPadding()
                .imePadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenPanel(Modifier.fillMaxWidth()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(title, style = MaterialTheme.typography.headlineSmall)
                    Text("Casus: ${spy?.name ?: "—"}", style = MaterialTheme.typography.titleMedium)
                    Text("Mekan: $location", style = MaterialTheme.typography.titleMedium)
                }
            }

            PrimaryGradientButton(
                text = "Yeni Oyun",
                onClick = onNewGame,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}