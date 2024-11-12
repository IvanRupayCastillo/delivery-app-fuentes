package com.yobel.lecturadeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jotadev.jetcompose_2024_ii_ecoeats.navigation.SetupNavigation
import com.yobel.lecturadeliveryapp.presentation.util.Printer
import com.yobel.lecturadeliveryapp.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint
//import com.zebra.sdk.comm.BluetoothConnection



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SetupNavigation()
        }
    }
}

@Composable
fun DemoPrint() {
    val context = LocalContext.current
    Util.isPrinterConnected(Util.PRINTER_ADDRESS,context)

    Button(onClick = {
        Printer.sendZplOverBluetoothDemo2(Util.PRINTER_ADDRESS)
    }
    ){
        Text(text = "Imprimir")
    }

    /*val context = LocalContext.current
    val requestBluetoothPermissions = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.RequestPermission()
    ) {
        when(it) {
            true -> {
               val response =  Util.isPrinterConnected("",context)
                println("$response")
            }
            false -> {
                println("No se pudo conectar")
            }
        }
    }*/

    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
     //   requestBluetoothPermissions.launch(android.Manifest.permission.BLUETOOTH_CONNECT)
    //}
}


