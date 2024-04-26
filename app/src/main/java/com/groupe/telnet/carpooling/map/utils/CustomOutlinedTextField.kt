package com.groupe.telnet.carpooling.map.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.groupe.telnet.carpooling.map.common.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    labelText: String,
    value: String,
    leadingIcon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .background(color = BackgroundColor)
            .clickable { onClick() },
        label = { Text(
            text =labelText,
            color = TextColor) },
        readOnly = true,
        leadingIcon =  leadingIcon ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
    )
}