package com.example.customproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tableNumber: String,
    val customerName: String,
    val phone: String,
    val date: String,
    val time: String,
    val numPeople: Int
)
