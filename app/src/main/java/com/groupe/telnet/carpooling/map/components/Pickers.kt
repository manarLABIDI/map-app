import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.ui.theme.SkyBlueColor
import com.groupe.telnet.carpooling.map.utils.TimePickerDialog



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun datetime() {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }



        Text(text = "Pick up date", modifier = Modifier.padding(bottom = 16.dp))


        Button(
            onClick = {
                showDatePicker = true
            },
            colors = ButtonDefaults.buttonColors(
                SkyBlueColor,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Pick Date")
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar Icon"
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
                state = datePickerState,
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
            TimePicker(state = timePickerState)
        }
    }
}


@Composable
fun pickUpLocationButton() {

        Text(text = "Pick up location", modifier = Modifier.padding(bottom = 16.dp))

        TextButton(
            onClick = {
                // Handle location picking action here
            },
            colors = ButtonDefaults.buttonColors(
                SkyBlueColor,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Pick up location")
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.padding(end = 8.dp)
            )
        }

}
