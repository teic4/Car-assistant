package com.example.carassistant.ui.selectcar.confirmcar

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ConfirmCarViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val confirmCarEventsChannel = Channel<ConfirmCarEvents>()
    val confirmCarEvents = confirmCarEventsChannel.receiveAsFlow()

    val car = state.get<Car>("car")!!

    fun onConfirmClicked() = viewModelScope.launch {
        repository.insertCar(car)
        confirmCarEventsChannel.send(ConfirmCarEvents.CarConfirmed)
    }

    sealed class ConfirmCarEvents {
        object CarConfirmed : ConfirmCarEvents()
    }



}