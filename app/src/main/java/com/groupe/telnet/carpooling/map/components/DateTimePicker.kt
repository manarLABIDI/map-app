package com.groupe.telnet.carpooling.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import com.groupe.telnet.carpooling.map.utils.TimePickerDialog


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun datetime() {

    val pickedDate = rememberDatePickerState()
    val pickedTime = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var textFieldText by remember {mutableStateOf("")}



    //Text(text = "Pick up date", modifier = Modifier.padding(bottom = 16.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = textFieldText,
            onValueChange = { textFieldText = it },
            modifier = Modifier.weight(1f)
                .background(color = BackgroundColor),
            label = { Text("Pickup date") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )

        )
        Spacer(modifier = Modifier.padding(20.dp))
        calenderButton(onClick = {
            showDatePicker = true
        })
    }



    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        showTimePicker = true
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Cancel") }
            },
        ) {
            DatePicker(
                state = pickedDate,
            )
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            },
        ) {
            TimePicker(state = pickedTime)
        }
    }
}



@Composable
fun calenderButton(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = SkyBlueColor,
        onClick = { onClick() },
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            tint = Color.White,
            imageVector = Icons.Default.DateRange,
            contentDescription = "Calendar Icon"
        )
    }
}

