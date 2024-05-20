package com.example.androidpractice3

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.core.app.NotificationCompat


class Broadcast : BroadcastReceiver() {
    lateinit var notification: NotificationCompat.Builder
    lateinit var service: Service
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
            val wifiModeOn = Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.WIFI_ON,
            ) != 0
            if (wifiModeOn) {
                notification.setContentText("wifi is active")
                service.startForeground(10, notification.build());
            } else {
                notification.setContentText("wifi is not active")
                service.startForeground(10, notification.build());
            }

        }
    }
}