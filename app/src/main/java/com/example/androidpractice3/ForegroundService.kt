package com.example.androidpractice3

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.IBinder
import androidx.core.app.NotificationCompat


class ForegroundService: Service() {
    val notification = NotificationCompat.Builder(
        this, "service_channel");
    private val broadcast = Broadcast()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start();
            Actions.STOP.toString() -> stop();
        }

        return super.onStartCommand(intent, flags, startId)
    }
    private fun start() {
        registerReceiver(
            broadcast,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
        broadcast.notification = notification
        broadcast.service = this
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service is Running")
            .setContentText("Here is the notification content")
            .build()
        startForeground(10, notification.build())
    }

    private fun stop() {
        stopSelf()
    }

    enum class Actions {
        START, STOP
    }
}