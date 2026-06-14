package com.example.beautyclub.ui.component

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.beautyclub.ui.theme.Background
import com.example.beautyclub.ui.theme.DividerColor
import com.example.beautyclub.ui.theme.Primary
import com.example.beautyclub.ui.theme.Surface

@Composable
fun glowOutlinedTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Primary,
        unfocusedBorderColor = DividerColor,
        focusedLabelColor = Primary,
        cursorColor = Primary,
        focusedContainerColor = Surface,
        unfocusedContainerColor = Background
    )