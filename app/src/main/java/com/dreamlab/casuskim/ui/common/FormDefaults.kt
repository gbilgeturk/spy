package com.dreamlab.casuskim.ui.common

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dreamlab.casuskim.ui.theme.GoldAccent
import com.dreamlab.casuskim.ui.theme.NightBlue
import com.dreamlab.casuskim.ui.theme.SoftWhite
import com.dreamlab.casuskim.ui.theme.SteelGray

@Composable
fun darkTextFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedContainerColor = Color(0xFF263445),     // daha açık koyu yüzey
    unfocusedContainerColor = Color(0xFF1F2E40),
    disabledContainerColor = Color(0xFF1F2E40),
    focusedIndicatorColor = Color(0xFFD4AF37),     // altın odak sınırı
    unfocusedIndicatorColor = Color(0x66FFFFFF),
    cursorColor = Color(0xFFD4AF37),
    focusedTextColor = Color(0xFFF5F5F5),
    unfocusedTextColor = Color(0xFFF5F5F5),
    focusedLabelColor = Color(0xFFF5F5F5),
    unfocusedLabelColor = Color(0xFFBFC7D1),
    focusedPlaceholderColor = Color(0x88F5F5F5),
    unfocusedPlaceholderColor = Color(0x66F5F5F5),
    errorIndicatorColor = Color(0xFFFF6B6B),
    errorContainerColor = Color(0xFF2A1E22),
    errorCursorColor = Color(0xFFFF6B6B)
)