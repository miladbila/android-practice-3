package com.example.androidpractice3

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androidpractice3.ui.theme.AndroidPractice3Theme
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.Duration
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(applicationContext, ForegroundService::class.java).also {
            it.action = ForegroundService.Actions.START.toString()
            startService(it)
        }
        val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
        ).setBackoffCriteria(
            backoffPolicy = BackoffPolicy.LINEAR,
            duration = Duration.ofSeconds(15)
        ).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(mainActivityViewModel: MainActivityViewModel = MainActivityViewModel()) {
    val uiState by mainActivityViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mainActivityViewModel.loadData() }) {
                Icon(Icons.Filled.Refresh, "")
            }
        }
    ) { paddingValues ->
        LazyColumn(Modifier.padding(paddingValues)) {
            items(uiState.data) {
                Info(type = it.type, time = it.time, value = it.value)
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }

}

@Composable
fun Info(type: String, time: String, value: String) {
    Column {
        Text(text = type)
        Text(text = time)
        Text(text = value)
    }
}
