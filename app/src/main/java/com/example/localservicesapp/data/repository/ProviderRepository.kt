package com.example.localservicesapp.data.repository

import com.example.localservicesapp.data.db.ProviderDao
import com.example.localservicesapp.data.model.Provider

class ProviderRepository(private val providerDao: ProviderDao) {
    fun getAllProviders() = providerDao.getAllProviders()
    
    fun getProvidersByServiceId(serviceId: Long) = providerDao.getProvidersByServiceId(serviceId)
    
    suspend fun getProviderById(id: Long) = providerDao.getProviderById(id)
    
    suspend fun insert(provider: Provider) = providerDao.insert(provider)
    
    suspend fun update(provider: Provider) = providerDao.update(provider)
    
    suspend fun delete(provider: Provider) = providerDao.delete(provider)
}