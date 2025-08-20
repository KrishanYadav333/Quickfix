package com.quickfix.services.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceName: String,
    val description: String,
    val imageUrl: String = "",
    val isVisible: Boolean = true
)