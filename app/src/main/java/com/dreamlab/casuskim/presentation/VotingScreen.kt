package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SecondaryGradientButton
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.*
import com.dreamlab.casuskim.util.gradientBackground

@Composable
fun VotingScreen(
    players: List<Player>,
    allLocations: List<Location>,
    onFinish: (spyGuessedRight: Boolean) -> Unit
) {
    var selected by remember { mutableStateOf<Player?>(null) }
    var revealed by remember { mutableStateOf(false) }
    var guessMode by remember { mutableStateOf(false) }

    val actualLocation = players.firstOrNull { !it.isSpy }?.roleLocation.orEmpty()

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .imePadding()
                .systemBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ScreenPanel(Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Oylama", style = MaterialTheme.typography.headlineSmall)

                    if (!revealed) {
                        players.forEach { p ->
                            OutlinedButton(
                                onClick = { selected = p },
                                modifier = Modifier.fillMaxWidth(),
                                border = BorderStroke(
                                    2.dp,
                                    if (selected == p) GoldAccent else Color.Gray
                                ),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (selected == p) GoldAccent.copy(alpha = 0.1f) else Color.Transparent,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(p.name, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    } else {
                        if (selected?.isSpy == true && !guessMode) {
                            Text(
                                "Casus bulundu: ${selected?.name}. Mekanı doğru tahmin ederse kazanır.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                allLocations.shuffled().take(6).forEach { loc ->
                                    SecondaryGradientButton(
                                        text = loc.name,
                                        onClick = {
                                            val win = (loc.name == actualLocation)
                                            guessMode = true // ✅ Mekân seçildikten sonra ayarlanıyor
                                            onFinish(win)
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        } else if (selected?.isSpy != true) {
                            Text(
                                "Yanlış seçim. Casus farklı biri.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(8.dp))
                            PrimaryGradientButton(
                                text = "Sonuca Git",
                                onClick = { onFinish(false) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            if (!revealed) {
                PrimaryGradientButton(
                    text = "Sonucu Göster",
                    enabled = selected != null,
                    onClick = { revealed = true },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}