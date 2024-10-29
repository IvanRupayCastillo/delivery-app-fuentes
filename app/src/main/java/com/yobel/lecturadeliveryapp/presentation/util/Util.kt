package com.yobel.lecturadeliveryapp.presentation.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context

class Util {

    companion object {



        @SuppressLint("MissingPermission")
        fun isPrinterConnected(printerAddress: String, context: Context): Boolean {
            val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

            pairedDevices?.forEach { device ->
                if (device.address == printerAddress) {
                    return true // La impresora está conectada
                }
            }
            return false // La impresora no está conectada
        }
    }
}