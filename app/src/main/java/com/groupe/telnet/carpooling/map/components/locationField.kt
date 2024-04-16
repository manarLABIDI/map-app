package com.groupe.telnet.carpooling.map.components

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.groupe.telnet.carpooling.map.iconButtons.locationIcon
import com.groupe.telnet.carpooling.map.ui.theme.BackgroundColor


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun locationFiled(labelText: String) {
    val context = LocalContext.current
    var textFieldText by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        Toast.makeText(context,"isGranted= $isGranted",
            Toast.LENGTH_SHORT).show()
    }



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
            label = { Text(labelText) },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            leadingIcon = {
                locationIcon(onClick = {
                    launcher.launch(
                       Manifest.permission.ACCESS_FINE_LOCATION
                    )
                })
            }
        )


    }
}
