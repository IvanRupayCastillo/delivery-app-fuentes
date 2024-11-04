package com.yobel.lecturadeliveryapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jotadev.jetcompose_2024_ii_ecoeats.navigation.SetupNavigation
import com.yobel.lecturadeliveryapp.presentation.util.Util
import com.yobel.lecturadeliveryapp.ui.theme.LecturaDeliveryAppTheme
import com.zebra.sdk.comm.BluetoothConnection
import dagger.hilt.android.AndroidEntryPoint
//import com.zebra.sdk.comm.BluetoothConnection



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SetupNavigation()
            //A()
        }
    }
}

@Composable
fun A(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Util.isPrinterConnected("8C:D5:4A:10:D4:7F",context)

    Button(onClick = {
        //printText("8C:D5:4A:10:D4:7F",context)
        //val printer = Printer()
        Printer.sendZplOverBluetooth22("8C:D5:4A:10:D4:7F")
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

fun printText(printerAddress: String, context: Context) {

    val connection = BluetoothConnection(printerAddress)

    try {
        connection.open()
        val msg = "Texto a imprimir"
        val printData = msg.toByteArray()
        connection.write(printData)
        connection.close()
        Toast.makeText(context, "Impresión enviada", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error al imprimir: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
