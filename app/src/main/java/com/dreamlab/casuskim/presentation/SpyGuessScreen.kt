package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreamlab.casuskim.domain.model.Location
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SecondaryGradientButton
import com.dreamlab.casuskim.ui.common.SpyBackground

@Composable
fun SpyGuessScreen(
    allLocations: List<Location>,
    actualLocation: String,
    onSpyWins: () -> Unit,
    onSpyLoses: () -> Unit
) {
    // Doğru mekan + 5 rastgele yanlış mekan
    val locationOptions = remember {
        val wrongs = allLocations.filter { it.name != actualLocation }
            .shuffled()
            .take(5)
        (wrongs + allLocations.first { it.name == actualLocation }).shuffled()
    }

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .systemBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenPanel(Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Casus Mekan Tahmini", style = MaterialTheme.typography.headlineSmall)

                    locationOptions.forEach { loc ->
                        SecondaryGradientButton(
                            text = loc.name,
                            onClick = {
                                if (loc.name == actualLocation) {
                                    onSpyWins()
                                } else {
                                    onSpyLoses()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}