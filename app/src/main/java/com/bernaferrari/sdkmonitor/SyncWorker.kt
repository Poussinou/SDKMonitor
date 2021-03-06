package com.bernaferrari.sdkmonitor

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bernaferrari.sdkmonitor.core.AppManager
import com.orhanobut.logger.Logger
import io.karn.notify.Notify
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.runBlocking

class SyncWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val debugLog = StringBuilder()
    private val isDebugEnabled = false //Injector.get().sharedPrefs().getBoolean("debug", true)

    override fun doWork(): Result {
        heavyWork()
        WorkerHelper.updateWorkerWithConstraints(Injector.get().sharedPrefs(), false)
        return Result.SUCCESS
    }

    private fun heavyWork() = runBlocking(Dispatchers.IO) {
        Logger.d("Doing background work! ")

        debugLog.setLength(0)

        AppManager.getPlayStorePackages()
            // this condition will only happen when app is run on emulator.
            .let { if (it.isEmpty()) AppManager.getPackages() else it }
            .forEach {
                AppManager.insertNewApp(it)
                AppManager.insertNewVersion(it)
            }

        if (isDebugEnabled) {
            Notify.with(context)
                .meta {
                    this.clickIntent = PendingIntent.getActivity(
                        context, 0,
                        Intent(context, MainActivity::class.java), 0
                    )
                }
                .asBigText {
                    title = "[Debug] There has been a sync"
                    text = "Expand to see the full log"
                    expandedText = "..."
                    bigText = debugLog
                }
                .show()
        }
    }
}
