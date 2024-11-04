package com.yobel.lecturadeliveryapp.presentation.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.widget.Toast

class Util {

    companion object {



        @SuppressLint("MissingPermission")
        fun isPrinterConnected(printerAddress: String, context2: Context): Boolean {
            val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

            pairedDevices?.forEach { device ->
                if (device.address == printerAddress) {
                    Toast.makeText(context2,"true",Toast.LENGTH_LONG).show()
                    return true // La impresora está conectada


                }
            }
            Toast.makeText(context2,"false",Toast.LENGTH_LONG).show()
            return false // La impresora no está conectada

        }
    }
}