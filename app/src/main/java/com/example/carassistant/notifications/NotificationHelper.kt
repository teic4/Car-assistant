package com.example.carassistant.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.carassistant.R


@RequiresApi(Build.VERSION_CODES.O)
class NotificationHelper(base: Context?) :
    ContextWrapper(base) {

    init {
        createChannel()
    }

    companion object {
        const val channelID = "channelID"
        const val channelName = "channelName"
    }

    private var mManager: NotificationManager? = null

    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    lightColor = Color.RED
                    enableLights(true)
                }
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    fun channelNotification(title: String, message: String) = NotificationCompat
        .Builder(applicationContext, channelID)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSmallIcon(R.drawable.logo_red)

}