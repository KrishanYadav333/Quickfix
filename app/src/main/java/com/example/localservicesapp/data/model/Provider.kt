package com.example.localservicesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "providers")
data class Provider(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceId: Long,
    val name: String,
    val contact: String
)