package com.example.carassistant.ui.mycar

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.room.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyCarViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    var car = repository.getCar().asLiveData()

}