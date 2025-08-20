package com.quickfix.services.data.dao

import androidx.room.*
import com.quickfix.services.data.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM services WHERE isVisible = 1")
    fun getVisibleServices(): Flow<List<Service>>

    @Query("UPDATE services SET isVisible = :isVisible WHERE id = :serviceId")
    suspend fun updateVisibility(serviceId: Long, isVisible: Boolean)

    @Insert
    suspend fun insert(service: Service)

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)
}