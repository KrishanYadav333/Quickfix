package com.quickfix.services.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceName: String,
    val providerName: String,
    val customerName: String,
    val customerEmail: String,
    val customerAddress: String,
    val customerContact: String,
    val bookingDate: String,
    val bookingTime: String,
    val status: String = "Pending"
)