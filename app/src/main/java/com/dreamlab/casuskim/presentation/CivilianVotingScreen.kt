package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SecondaryGradientButton
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.*

@Composable
fun CivilianVotingScreen(
    players: List<Player>,
    onSpyFound: () -> Unit,
    onSpyNotFound: () -> Unit
) {
    var selected by remember { mutableStateOf<Player?>(null) }
    var revealed by remember { mutableStateOf(false) }

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
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
                    Text("Sivil Oylaması", style = MaterialTheme.typography.headlineSmall)

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
                        if (selected?.isSpy == true) {
                            onSpyFound() // Sivil kazandı
                        } else {
                            onSpyNotFound() // Casus tahmin yapacak
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