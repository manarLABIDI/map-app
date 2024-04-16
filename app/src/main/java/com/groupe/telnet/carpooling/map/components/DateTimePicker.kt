package com.groupe.telnet.carpooling.map.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.groupe.telnet.carpooling.map.iconButtons.calenderButton
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import com.groupe.telnet.carpooling.map.utils.TimePickerDialog
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun datetime() {

    val pickedDate = rememberDatePickerState()
    val pickedTime = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var textFieldText by remember { mutableStateOf("") }
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = textFieldText,
            onValueChange = {},
            modifier = Modifier
                .background(color = BackgroundColor),
            label = { Text("Pick up date") },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,

                ),

            leadingIcon = {
                calenderButton(onClick = { showDatePicker = true })
            }
        )

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


                        val selectedDate = Instant.ofEpochMilli(pickedDate.selectedDateMillis ?: 0)
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                        val selectedHour = pickedTime.hour
                        val selectedMinute = pickedTime.minute
                        textFieldText = "$selectedDate AT $selectedHour:$selectedMinute"
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
            TimeInput(state = pickedTime)
        }
    }


}

