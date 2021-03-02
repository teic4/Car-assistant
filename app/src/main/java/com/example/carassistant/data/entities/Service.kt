package com.example.carassistant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val serviceType: String,
    val date: String,
    val price: Float,
    val description: String
) {

}