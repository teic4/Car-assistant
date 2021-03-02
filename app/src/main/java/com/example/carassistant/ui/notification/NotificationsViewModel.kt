package com.example.carassistant.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import com.example.carassistant.data.entities.Service
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.NavigationRes
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NotificationsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val TAG = "NotificationsViewModel"

    private val notificationEventsChannel = Channel<NotificationEvents>()
    val notificationEvents = notificationEventsChannel.receiveAsFlow()

    val refuelingLiveData = repository.getLastRefuel().asLiveData()
    val serviceLiveData = repository.getLastService().asLiveData()
    val bigServiceLiveData = repository.getLastBigService().asLiveData()

    fun checkCarSelected() = viewModelScope.launch {
        val carSize = repository.getCarsSize()
        Log.d(TAG, "$carSize")

        if (carSize != 1) {
            notificationEventsChannel.send(NotificationEvents.NavigateToSelectCarFragment)
        }
    }

    fun onSaveServiceClick(serviceType: String, description: String, price: Float?, date: String) =
        viewModelScope.launch {
            if (price == null) {
                notificationEventsChannel.send(NotificationEvents.ShowMessage("Enter price"))
                return@launch
            }

            if (date.length != 11) {
                notificationEventsChannel.send(NotificationEvents.ShowMessage("Enter correct date format (dd/mm/yyy)"))
                return@launch
            }

            val service = Service(0, serviceType, date, price, description)

            repository.insertService(service)
            notificationEventsChannel.send(NotificationEvents.ShowMessage("Successfully saved"))

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

    fun onUpdateActivityClick() {

    }

    sealed class NotificationEvents {
        object NavigateToSelectCarFragment : NotificationEvents()
        data class ShowMessage(val message: String) : NotificationEvents()
    }
}