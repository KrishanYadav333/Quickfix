package com.example.localservicesapp.data.repository

import com.example.localservicesapp.data.db.ServiceDao
import com.example.localservicesapp.data.model.Service

class ServiceRepository(private val serviceDao: ServiceDao) {
    fun getAllServices() = serviceDao.getAllServices()
    
    suspend fun getServiceById(id: Long) = serviceDao.getServiceById(id)
    
    suspend fun insert(service: Service) = serviceDao.insert(service)
    
    suspend fun update(service: Service) = serviceDao.update(service)
    
    suspend fun delete(service: Service) = serviceDao.delete(service)
}