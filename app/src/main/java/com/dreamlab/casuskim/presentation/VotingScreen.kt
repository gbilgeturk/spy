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
import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Oylama", style = MaterialTheme.typography.headlineSmall)
                    if (!revealed) {
                        players.forEach { p ->
                            OutlinedButton(
                                onClick = { selected = p },
                                modifier = Modifier.fillMaxWidth(),
                                border = if (selected == p) ButtonDefaults.outlinedButtonBorder(true)
                                else ButtonDefaults.outlinedButtonBorder(false)
                            ) { Text(p.name) }
                        }
                    } else {
                        if (selected?.isSpy == true && !guessMode) {
                            Text("Casus bulundu: ${selected?.name}. Mekanı doğru tahmin ederse kazanır.")
                            Spacer(Modifier.height(8.dp))
                            allLocations.shuffled().take(6).forEach { loc ->
                                OutlinedButton(
                                    onClick = {
                                        val win = (loc.name == actualLocation)
                                        onFinish(win)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) { Text(loc.name) }
                            }
                            guessMode = true
                        } else {
                            Text("Yanlış seçim. Casus farklı biri.")
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