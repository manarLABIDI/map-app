package com.groupe.telnet.carpooling.map.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat

class Common {
    companion object {
        fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
            return (ContextCompat.checkSelfPermission(context, permission)) == PackageManager.PERMISSION_GRANTED
        }



    }

}