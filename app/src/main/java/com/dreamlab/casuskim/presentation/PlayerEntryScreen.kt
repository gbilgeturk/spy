package com.dreamlab.casuskim.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.common.darkTextFieldColors
import com.dreamlab.casuskim.ui.theme.GoldAccent
import com.dreamlab.casuskim.util.gradientBackground
import kotlinx.coroutines.launch

@Composable
fun PlayerEntryScreen(
    navController: NavController,
    viewModel: GameViewModel,
    onStart: (List<String>) -> Unit
) {
    val context = LocalContext.current
    val playerNames = remember { mutableStateListOf("", "", "", "") }
    val canStart by derivedStateOf { playerNames.count { it.isNotBlank() } >= 3 }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        // Arka plan
        SpyBackground(Modifier.matchParentSize()){}

        // Scaffold üstte, Snackbar burada
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .systemBarsPadding()
                        .navigationBarsPadding()
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .systemBarsPadding()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Oyuncu İsimlerini Girin",
                            style = MaterialTheme.typography.headlineMedium,
                            color = GoldAccent
                        )
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Ayarlar",
                                tint = GoldAccent
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))

                    ScreenPanel(Modifier.fillMaxWidth()) {
                        repeat(playerNames.size) { index ->
                            OutlinedTextField(
                                value = playerNames[index],
                                onValueChange = { playerNames[index] = it },
                                label = { Text("Oyuncu ${index + 1}") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = GoldAccent
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                colors = darkTextFieldColors(),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = { if (playerNames.size < 8) playerNames.add("") },
                                enabled = playerNames.size < 8
                            ) { Text("Oyuncu Ekle") }

                            Text(
                                text = "${playerNames.count { it.isNotBlank() }}/8 oyuncu",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

                PrimaryGradientButton(
                    text = "Oyuna Başla",
                    enabled = true, // aktif kalsın ama kontrol bizde
                    onClick = {
                        if (!canStart) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Başlamak için en az 3 oyuncu gerekli",
                                    withDismissAction = true
                                )
                            }
                        } else {
                            viewModel.setPlayerNames(playerNames)
                            viewModel.assignRoles(context)
                            navController.navigate(Screen.RoleReveal.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                )
            }
        }
    }
}