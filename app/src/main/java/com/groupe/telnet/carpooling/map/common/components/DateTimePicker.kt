package com.groupe.telnet.carpooling.map.common.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groupe.telnet.carpooling.map.common.iconButtons.calenderButton
import com.groupe.telnet.carpooling.map.utils.CustomOutlinedTextField
import com.groupe.telnet.carpooling.map.utils.TimePickerDialog
import java.time.Instant
import java.time.ZoneId


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DateTime(/*rideRequestViewModel: RideRequestViewModel = hiltViewModel()*/) {

    val pickedDate = rememberDatePickerState()
    val pickedTime = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var dateFieldText by remember { mutableStateOf("") }
   // val date = rideRequestViewModel.dateState.value
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {

        CustomOutlinedTextField(
            labelText = "Pick up date",
            value = dateFieldText,
            leadingIcon=  { calenderButton() },
            onClick = {showDatePicker = true })

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
                        dateFieldText = "$selectedDate AT $selectedHour:$selectedMinute"
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

