package com.dreamlab.casuskim.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryGradientButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFE3C76A), Color(0xFFD4AF37)), // altın geçiş
        start = Offset(0f, 0f),
        end = Offset(300f, 300f) // diyagonal
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color(0x33D4AF37),
            contentColor = Color.White,
            disabledContentColor = Color(0xFFB5B5B5)
        ),
        modifier = modifier
            .background(gradient, RoundedCornerShape(14.dp))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            style = LocalTextStyle.current.copy(
                shadow = Shadow(
                    color = Color(0x80000000),
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            )
        )
    }
}