package com.example.androidpractice3

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CustomWorker constructor(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val airPlaneModeOn = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON
        ) != 0
        if (airPlaneModeOn) {
            Log.d("Custom Worker", "airplane mode on")
            val fileWriter = FileWriter()
            fileWriter.writeData("airplane worker", "active")
        } else {
            Log.d("Custom Worker", "airplane mode off")
            val fileWriter = FileWriter()
            fileWriter.writeData("airplane worker", "not active")
        }
        val bluetoothOn = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.BLUETOOTH_ON
        ) != 0
        if (bluetoothOn) {
            Log.d("Custom Worker", "bluetooth mode on")
            val fileWriter = FileWriter()
            fileWriter.writeData("bluetooth worker", "active")
        } else {
            Log.d("Custom Worker", "bluetooth mode off")
            val fileWriter = FileWriter()
            fileWriter.writeData("bluetooth worker", "not active")
        }
        return Result.success()
    }
}