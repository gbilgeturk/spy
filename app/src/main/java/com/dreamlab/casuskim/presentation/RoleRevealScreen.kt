package com.dreamlab.casuskim.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.GoldAccent
import com.dreamlab.casuskim.util.gradientBackground

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RoleRevealScreen(
    players: List<Player>,
    currentIndex: Int,
    onNext: (Int) -> Unit,
    onFinish: () -> Unit
) {
    var showRole by remember { mutableStateOf(false) }
    val player = players[currentIndex]

    // Görsel boyutu animasyonu
    val imageSize by animateDpAsState(
        targetValue = if (showRole) 180.dp else 140.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    // Yazı boyutu animasyonu
    val textScale by animateFloatAsState(
        targetValue = if (showRole) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

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
                AnimatedContent(
                    targetState = showRole,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { isShowing ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize * textScale
                            ),
                            color = GoldAccent,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(8.dp))

                        when {
                            !isShowing -> {
                                Image(
                                    painter = painterResource(R.drawable.hidden_role_icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(CircleShape)
                                        .border(3.dp, GoldAccent, CircleShape)
                                )
                                Text(
                                    "Rolünü görmek için butona bas.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                            player.isSpy -> {
                                Image(
                                    painter = painterResource(R.drawable.spy_icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(CircleShape)
                                        .border(3.dp, GoldAccent, CircleShape)
                                )
                                Text(
                                    "Casus sensin",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize * textScale
                                    ),
                                    color = GoldAccent
                                )
                            }
                            else -> {
                                Image(
                                    painter = painterResource(R.drawable.role_icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(imageSize)
                                        .clip(CircleShape)
                                        .border(3.dp, GoldAccent, CircleShape)
                                )
                                Text(
                                    "Mekan: ${player.roleLocation}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize * textScale
                                    ),
                                    color = Color.White
                                )
                                Text(
                                    "Rolün: ${player.role}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize * textScale
                                    ),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            PrimaryGradientButton(
                text = when {
                    !showRole -> "Rolünü Gör"
                    currentIndex < players.lastIndex -> "Sıradaki Oyuncu"
                    else -> "Oyuna Başla"
                },
                onClick = {
                    if (!showRole) {
                        showRole = true
                    } else {
                        showRole = false
                        if (currentIndex < players.lastIndex) {
                            onNext(currentIndex + 1)
                        } else {
                            onFinish()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}