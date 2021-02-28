package com.example.carassistant.ui.selectcar

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.entities.CarModel
import com.example.carassistant.data.firebase.FirebaseRepository
import com.example.carassistant.data.room.Repository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.Exception

class SelectCarViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val firebaseRepository = FirebaseRepository()

    private val TAG = "SelectCarViewModel"

    private val selectCarEventsChannel = Channel<SelectCarEvents>()
    val selectCarEvents = selectCarEventsChannel.receiveAsFlow()

    var allCarModels = mutableListOf<CarModel>()
    val carCompaniesStateFlow = MutableStateFlow(mutableListOf<String>())

    val carNamesLiveData = MutableLiveData<MutableList<String>>()
    val cars = mutableListOf<Car>()

    fun getAllCarCompanies() = viewModelScope.launch {
        allCarModels.clear()
        val querySnapshot = firebaseRepository.getAllCarCompaniesSnapshot()
        val carModels = mutableListOf<String>()
        for (document in querySnapshot.documents) {
            val carModel = document.toObject(CarModel::class.java)
            carModel?.let {
                carModels.add(it.naziv)
                allCarModels.add(it)
            }
        }
        carCompaniesStateFlow.value = carModels
    }


    fun getAllCarModels(carCompany: String) = viewModelScope.launch {
        //filtriraj listu svih auto kompanija di je ime jednako odabranon imenu iz spinnera (tribalo bi vracat samo 1 document)
        try {
            cars.clear()
            Log.d(TAG, "$allCarModels")
            val carModel = allCarModels.single {
                it.naziv == carCompany
            }
            val querySnapshot = firebaseRepository.getCarModelDocumentSnapshot(carModel.id)
            val carsNames = mutableListOf<String>()

            for (document in querySnapshot.documents) {
                val car = document.toObject(Car::class.java)
                car?.let {
                    carsNames.add(car.nazivModela)
                    cars.add(car)
                }
            }

            carNamesLiveData.value = carsNames
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }
    }

    fun onSaveCarClick(carName: String) = viewModelScope.launch {
        if (carName == "Car") {
            selectCarEventsChannel.send(SelectCarEvents.ShowMessage("Please select car"))
            return@launch
        }

        val car = cars.single {
            it.nazivModela == carName
        }
        selectCarEventsChannel.send(SelectCarEvents.ConfirmCar(car))
    }

    sealed class SelectCarEvents() {
        data class ShowMessage(val message: String) : SelectCarEvents()
        data class ConfirmCar(val car: Car) : SelectCarEvents()
    }
}





















