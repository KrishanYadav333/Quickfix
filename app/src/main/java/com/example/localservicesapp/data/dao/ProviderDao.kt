package com.example.localservicesapp.data.dao

import androidx.room.*
import com.example.localservicesapp.data.model.Provider
import kotlinx.coroutines.flow.Flow

@Dao
interface ProviderDao {
    @Query("SELECT * FROM providers WHERE serviceId = :serviceId")
    fun getProvidersByServiceId(serviceId: Long): Flow<List<Provider>>

    @Insert
    suspend fun insert(provider: Provider)
}