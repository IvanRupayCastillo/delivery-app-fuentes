package com.yobel.lecturadeliveryapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jotadev.jetcompose_2024_ii_ecoeats.navigation.SetupNavigation
import com.yobel.lecturadeliveryapp.presentation.util.Util
import com.yobel.lecturadeliveryapp.ui.theme.LecturaDeliveryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            //SetupNavigation()
            A()
        }
    }
}

@Composable
fun A(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val requestBluetoothPermissions = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.RequestPermission()
    ) {
        when(it) {
            true -> {
                Util.isPrinterConnected("",context)
            }
            false -> {
                println("No se pudo conectar")
            }
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        requestBluetoothPermissions.launch(android.Manifest.permission.BLUETOOTH_CONNECT)
    }
}

