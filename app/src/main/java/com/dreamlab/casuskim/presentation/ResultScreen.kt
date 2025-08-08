package com.dreamlab.casuskim.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.domain.model.Player
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import com.dreamlab.casuskim.ui.theme.GoldAccent
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

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
    val subtitle = if (isSpyWinner)
        "Casus doğru mekanı tahmin etti."
    else
        "Siviller casusu doğru buldu."

    SpyBackground(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            ConfettiBurst(
                enabled = true,
                winnerIsSpy = isSpyWinner,
                durationMs = 1400
            )

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
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AnimatedContent(
                            targetState = isSpyWinner,
                            transitionSpec = {
                                (fadeIn(tween(300)) + scaleIn(initialScale = 0.8f)) togetherWith
                                        (fadeOut(tween(200)) + scaleOut(targetScale = 0.8f))
                            },
                            label = "winnerIcon"
                        ) { spyWins ->
                            Image(
                                painter = painterResource(R.drawable.ic_result_success),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(180.dp)
                                    .clip(CircleShape)
                                    .border(3.dp, GoldAccent, CircleShape)
                            )
                        }

                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = GoldAccent
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )

                        Divider(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .alpha(0.5f)
                        )

                        Text(
                            text = "Casus: ${spy?.name ?: "—"}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Mekan: $location",
                            style = MaterialTheme.typography.titleMedium
                        )
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
}

@Composable
private fun ConfettiBurst(
    enabled: Boolean,
    winnerIsSpy: Boolean,
    durationMs: Long = 2000
) {
    if (!enabled) return

    val colorsSpy = listOf(GoldAccent, Color(0xFFE3C76A), Color(0xFF8C6A00))
    val colorsCiv = listOf(Color(0xFF34D399), Color(0xFF22D3EE), Color(0xFFA78BFA))

    var show by remember { mutableStateOf(true) }
    val particles = remember {
        List(80) {
            ConfettiParticle(
                x = Random.nextFloat(), // 0..1 ekran genişliğine göre
                y = 0f,
                speed = Random.nextFloat() * 200f + 150f, // düşme hızı px/sn
                size = Random.nextFloat() * 8f + 6f,
                color = if (winnerIsSpy) colorsSpy.random() else colorsCiv.random(),
                rotationSpeed = Random.nextFloat() * 180f - 90f
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "confettiAnim")
    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "timeAnim"
    )

    LaunchedEffect(Unit) {
        delay(durationMs)
        show = false
    }

    AnimatedVisibility(
        visible = show,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(300))
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            particles.forEachIndexed { index, p ->
                val progress = time + (index * 0.01f) % 1f
                val yPos = progress * h
                val xPos = p.x * w

                rotate(progress * p.rotationSpeed, pivot = Offset(xPos, yPos)) {
                    drawRect(
                        color = p.color,
                        topLeft = Offset(xPos, yPos),
                        size = androidx.compose.ui.geometry.Size(p.size, p.size * 1.5f)
                    )
                }
            }
        }
    }
}

private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val rotationSpeed: Float
)