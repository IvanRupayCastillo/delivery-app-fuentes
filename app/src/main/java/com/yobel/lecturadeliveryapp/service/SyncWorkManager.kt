package com.yobel.lecturadeliveryapp.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltWorker
class SyncWorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val labelRepository: LabelRepository
) : Worker(context, workerParams) {

    private val workerScope = CoroutineScope(Dispatchers.IO)

    override fun doWork(): Result {
        if (isInternetAvailable()) {
            // Realiza la tarea específica
            performSpecificTask()
            return Result.success()
        } else {
            return Result.failure()
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun performSpecificTask() {
        workerScope.launch {
            labelRepository.syncDataRemote()
        }


    }
}