package com.example.localservicesapp.data.db

import androidx.room.*
import com.example.localservicesapp.data.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM services WHERE id = :id LIMIT 1")
    suspend fun getServiceById(id: Long): Service?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(service: Service): Long

    @Update
    suspend fun update(service: Service)

    @Delete
    suspend fun delete(service: Service)
}