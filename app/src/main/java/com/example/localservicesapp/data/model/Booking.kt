package com.example.localservicesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val providerId: Long,
    val address: String,
    val contactInfo: String
)