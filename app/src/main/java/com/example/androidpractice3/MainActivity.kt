package com.example.androidpractice3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androidpractice3.ui.theme.AndroidPractice3Theme
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
        ).setBackoffCriteria(
            backoffPolicy = BackoffPolicy.LINEAR,
            duration = Duration.ofSeconds(15)
        ).build()


        setContent {
            AndroidPractice3Theme {
                LaunchedEffect(key1 = Unit) {
                    val workManager = WorkManager.getInstance(applicationContext)
                    workManager.enqueueUniquePeriodicWork(
                        "myWorkerName",
                        ExistingPeriodicWorkPolicy.REPLACE,
                        workRequest
                    )
                }
            }
        }
    }
}