package com.dreamlab.casuskim.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dreamlab.casuskim.R
import com.dreamlab.casuskim.presentation.navigation.Screen
import com.dreamlab.casuskim.ui.common.PrimaryGradientButton
import com.dreamlab.casuskim.ui.common.ScreenPanel
import com.dreamlab.casuskim.ui.common.SpyBackground
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavController, onFinish: () -> Unit) {

    val pages = listOf(
        OnboardingPage(
            image = R.drawable.ic_onboarding_spy,
            title = "Hoş Geldin Casus!",
            description = "Oyunun amacı, aranızdaki casusu bulmak veya casus olarak mekanı tahmin etmek."
        ),
        OnboardingPage(
            image = R.drawable.ic_onboarding_roles,
            title = "Roller Dağıtılır",
            description = "Herkese aynı mekanda farklı roller atanır. Casusun ise rolü yoktur."
        ),
        OnboardingPage(
            image = R.drawable.ic_onboarding_play,
            title = "Sorular Sor",
            description = "Oyuncular sırayla birbirine mekânla ilgili sorular sorar, casusu bulmaya çalışır."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope() // 👈

    SpyBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .navigationBarsPadding()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(10.dp))
            HorizontalPager(state = pagerState) { page ->
                val item = pages[page]
                ScreenPanel(Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            PrimaryGradientButton(
                text = if (pagerState.currentPage == pages.lastIndex) "Hadi Başlayalım" else "Devam Et",
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinish()
                    } else {
                        val next = (pagerState.currentPage + 1).coerceAtMost(pages.lastIndex)
                        scope.launch { pagerState.animateScrollToPage(next) } // 👈
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            )
        }
    }
}

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)