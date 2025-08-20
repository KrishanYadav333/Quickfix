package com.quickfix.services.data.dao

import androidx.room.*
import com.quickfix.services.data.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<Service>>

    @Insert
    suspend fun insert(service: Service)

    @Delete
    suspend fun delete(service: Service)
}