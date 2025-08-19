package com.example.localservicesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localservicesapp.data.db.AppDatabase
import com.example.localservicesapp.data.model.Provider
import com.example.localservicesapp.data.repository.ProviderRepository
import kotlinx.coroutines.launch

class ProviderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProviderRepository
    
    init {
        val providerDao = AppDatabase.getDatabase(application).providerDao()
        repository = ProviderRepository(providerDao)
    }
    
    fun getAllProviders() = repository.getAllProviders()
    
    fun getProvidersByServiceId(serviceId: Long) = repository.getProvidersByServiceId(serviceId)
    
    fun getProviderById(id: Long) = viewModelScope.launch {
        repository.getProviderById(id)
    }
    
    fun insert(provider: Provider) = viewModelScope.launch {
        repository.insert(provider)
    }
    
    fun update(provider: Provider) = viewModelScope.launch {
        repository.update(provider)
    }
    
    fun delete(provider: Provider) = viewModelScope.launch {
        repository.delete(provider)
    }
}