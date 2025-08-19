package com.example.localservicesapp.data.db

import androidx.room.*
import com.example.localservicesapp.data.model.Provider
import kotlinx.coroutines.flow.Flow

@Dao
interface ProviderDao {
    @Query("SELECT * FROM providers")
    fun getAllProviders(): Flow<List<Provider>>

    @Query("SELECT * FROM providers WHERE serviceId = :serviceId")
    fun getProvidersByServiceId(serviceId: Long): Flow<List<Provider>>

    @Query("SELECT * FROM providers WHERE id = :id LIMIT 1")
    suspend fun getProviderById(id: Long): Provider?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(provider: Provider): Long

    @Update
    suspend fun update(provider: Provider)

    @Delete
    suspend fun delete(provider: Provider)
}