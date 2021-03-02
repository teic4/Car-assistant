package com.example.carassistant.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carassistant.data.entities.Car
import com.example.carassistant.data.entities.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Car::class)
    suspend fun insertCar(car: Car)

    @Query("SELECT COUNT(id) FROM CAR")
    suspend fun getCarsSize() : Int

    @Query("SELECT * FROM CAR LIMIT 1")
    fun getCar(): Flow<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Service::class)
    suspend fun insertService(service: Service)

    @Query("SELECT * FROM SERVICE WHERE serviceType == 'Refuel' ORDER BY id LIMIT 1")
    fun getLastRefuel(): Flow<Service>

    @Query("SELECT * FROM SERVICE WHERE serviceType == 'Service' ORDER BY id LIMIT 1")
    fun getLastService(): Flow<Service>

    @Query("SELECT * FROM SERVICE WHERE serviceType == 'Big service' ORDER BY id LIMIT 1")
    fun getLastBigService(): Flow<Service>

}