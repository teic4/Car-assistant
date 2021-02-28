package com.example.carassistant.notifications.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.carassistant.notifications.NotificationHelper

class WinterChangeTiresBroadcastReceiver : BroadcastReceiver() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationHelper = NotificationHelper(context)
            val notification = notificationHelper.channelNotification("Do you feel the fresh air of Winter?",
                "It's time for a tires change")
            Log.d("TAG", "jbgdfa")
            notificationHelper.manager?.notify(1, notification.build())
        }
    }
}