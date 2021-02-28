package com.example.carassistant.notifications.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.carassistant.notifications.NotificationHelper

class SummerChangeTiresBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationHelper = NotificationHelper(context)
            val notification = notificationHelper.channelNotification("Its getting hotter",
                "It's time for a tires change")
            notificationHelper.manager?.notify(2, notification.build())
        }
    }
}