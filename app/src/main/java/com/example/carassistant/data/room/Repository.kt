package com.example.carassistant.data.room

import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.entities.Service
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: CarAssistantDatabase
) {

    suspend fun insertCar(car: Car) = db.getDao().insertCar(car)

    suspend fun getCarsSize() = db.getDao().getCarsSize()

    fun getCar() = db.getDao().getCar()

    suspend fun insertService(service: Service) = db.getDao().insertService(service)

}