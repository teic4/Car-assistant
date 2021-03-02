package com.example.carassistant

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private val mainActivityEventsChannel = Channel<MainActivityEvents>()
    val mainActivityEvents = mainActivityEventsChannel.receiveAsFlow()

    val carLiveData = repository.getCar().asLiveData()

    private val fiatForum = "https://www.fiatforum.com/"
    private val bmwForum = "https://www.bmwautoklub.hr/forum/"
    private val skodaForum = "https://www.skodaclubcroatia.com/forum/index.php"
    private val audiForum = "https://www.audiworld.com/forums/"


    fun onJoinCommunityClick(car: Car) = viewModelScope.launch{
        Log.d("TAG", "$car")
        when (car.brand) {
            "Fiat" -> {
                mainActivityEventsChannel.send(MainActivityEvents.JoinCommunity(fiatForum))
            }
            "BMW" -> {
                mainActivityEventsChannel.send(MainActivityEvents.JoinCommunity(bmwForum))
            }
            "Audi" -> {
                mainActivityEventsChannel.send(MainActivityEvents.JoinCommunity(audiForum))
            }
            "Škoda" -> {
                mainActivityEventsChannel.send(MainActivityEvents.JoinCommunity(skodaForum))
            }
        }
    }

    sealed class MainActivityEvents {
        data class JoinCommunity(val url: String) : MainActivityEvents()
    }
}