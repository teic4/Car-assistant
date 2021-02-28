package com.example.carassistant.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.NavigationRes
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NotificationsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(){

    private val TAG = "NotificationsViewModel"

    private val notificationEventsChannel = Channel<NotificationEvents>()
    val notificationEvents = notificationEventsChannel.receiveAsFlow()

    fun checkCarSelected() = viewModelScope.launch{
        val carSize = repository.getCarsSize()
        Log.d(TAG, "$carSize")

        if (carSize != 1) {
            notificationEventsChannel.send(NotificationEvents.NavigateToSelectCarFragment)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setSummerChangeTiresAlarm(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        val calendar = Calendar.getInstance()
        calendar.set(2021, 10, 10)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setWinterChangeTiresAlarm(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        val calendar = Calendar.getInstance()
        calendar.set(2021, 3, 10)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    sealed class NotificationEvents {
        object NavigateToSelectCarFragment : NotificationEvents()
    }
}