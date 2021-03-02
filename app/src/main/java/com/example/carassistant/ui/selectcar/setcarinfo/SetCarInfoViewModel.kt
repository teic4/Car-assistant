package com.example.carassistant.ui.selectcar.setcarinfo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.entities.Service
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SetCarInfoViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private val setCarInfoEventsChannel = Channel<SetCarInfoEvents>()
    val setCarInfoEvents = setCarInfoEventsChannel.receiveAsFlow()

    fun onConfirmClick(day: String, month: String) = viewModelScope.launch{
        val date = "$day.$month.2021."
        val service = Service(1, "", date,2000f, "Registration and insurance")
        repository.insertService(service)
        setCarInfoEventsChannel.send(SetCarInfoEvents.CarInfoSetUp)
    }

    sealed class SetCarInfoEvents {
        object CarInfoSetUp : SetCarInfoEvents()
    }
}