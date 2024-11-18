package com.yobel.lecturadeliveryapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yobel.lecturadeliveryapp.presentation.util.Util

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Util.scheduleInternetCheckWork(context)
        }
    }
}