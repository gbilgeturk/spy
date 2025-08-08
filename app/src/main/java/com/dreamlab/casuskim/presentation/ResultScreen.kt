package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
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

    val isSpyWinner = spyGuessedRight
    val title = if (isSpyWinner) "Casus Kazandı!" else "Sivil Takım Kazandı!"
    val subtitle = if (isSpyWinner) {
        "Casus mekan tahminini doğru yaptı."
    } else {
        "Siviller casusu buldu!"
    }

    val bgColor = if (isSpyWinner) Color(0xFFB71C1C) else Color(0xFF1B5E20) // kırmızı / yeşil
    val icon = if (isSpyWinner) Icons.Default.Close else Icons.Default.CheckCircle

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
            // Üst panel
            ScreenPanel(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Kazanan ikonu
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(bgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = bgColor
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray.copy(alpha = 0.4f)
                    )

                    Text("Casus: ${spy?.name ?: "—"}", style = MaterialTheme.typography.titleMedium)
                    Text("Mekan: $location", style = MaterialTheme.typography.titleMedium)
                }
            }

            PrimaryGradientButton(
                text = "Yeni Oyun",
                onClick = onNewGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
        }
    }
}