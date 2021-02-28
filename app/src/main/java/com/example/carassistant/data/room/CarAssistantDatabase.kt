package com.example.carassistant.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.entities.Service


@Database(
    entities = [Car::class, Service::class],
    version = 7
)
abstract class CarAssistantDatabase() : RoomDatabase() {

    abstract fun getDao() : CarDao
}