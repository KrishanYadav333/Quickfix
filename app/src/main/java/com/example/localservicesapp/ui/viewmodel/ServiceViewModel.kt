package com.example.localservicesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localservicesapp.data.db.AppDatabase
import com.example.localservicesapp.data.model.Service
import com.example.localservicesapp.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class ServiceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ServiceRepository
    
    init {
        val serviceDao = AppDatabase.getDatabase(application).serviceDao()
        repository = ServiceRepository(serviceDao)
    }
    
    fun getAllServices() = repository.getAllServices()
    
    fun getServiceById(id: Long) = viewModelScope.launch {
        repository.getServiceById(id)
    }
    
    fun insert(service: Service) = viewModelScope.launch {
        repository.insert(service)
    }
    
    fun update(service: Service) = viewModelScope.launch {
        repository.update(service)
    }
    
    fun delete(service: Service) = viewModelScope.launch {
        repository.delete(service)
    }
}