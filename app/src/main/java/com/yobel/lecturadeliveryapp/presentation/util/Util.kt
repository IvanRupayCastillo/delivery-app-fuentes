package com.yobel.lecturadeliveryapp.presentation.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yobel.lecturadeliveryapp.service.SyncWorkManager
import java.util.concurrent.TimeUnit

class Util {

    companion object {

        @SuppressLint("MissingPermission")
        fun isPrinterConnected(printerAddress: String, context2: Context): Boolean {
            val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

            pairedDevices?.forEach { device ->
                if (device.address == printerAddress) {
                    //Toast.makeText(context2,"true",Toast.LENGTH_LONG).show()
                    return true // La impresora está conectada
                }
            }
            //Toast.makeText(context2,"false",Toast.LENGTH_LONG).show()
            return false // La impresora no está conectada

        }

        fun scheduleInternetCheckWork(context: Context) {

            //WorkManager.getInstance(context).cancelUniqueWork("InternetCheckWork")

            val workRequest = PeriodicWorkRequestBuilder<SyncWorkManager>(15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "InternetCheckWork",
                //ExistingPeriodicWorkPolicy.KEEP,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )

            //TEST
            //val workRequest = OneTimeWorkRequestBuilder<SyncWorkManager>().build()
            //WorkManager.getInstance(context).enqueue(
            //    workRequest
            //)
        }

        val PRINTER_ADDRESS = "8C:D5:4A:10:D4:7F"
    }
}