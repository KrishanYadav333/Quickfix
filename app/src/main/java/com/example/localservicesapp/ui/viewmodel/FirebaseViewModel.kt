package com.example.localservicesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localservicesapp.data.firebase.FirebaseBooking
import com.example.localservicesapp.data.firebase.FirebaseProvider
import com.example.localservicesapp.data.firebase.FirebaseRepository
import com.example.localservicesapp.data.firebase.FirebaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    
    private val _services = MutableStateFlow<List<FirebaseService>>(emptyList())
    val services: StateFlow<List<FirebaseService>> = _services
    
    private val _providers = MutableStateFlow<List<FirebaseProvider>>(emptyList())
    val providers: StateFlow<List<FirebaseProvider>> = _providers

    fun signUp(email: String, password: String, name: String, role: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.signUp(email, password, name, role).fold(
                onSuccess = { callback(true, it) },
                onFailure = { callback(false, it.message ?: "Sign up failed") }
            )
        }
    }

    fun signIn(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.signIn(email, password).fold(
                onSuccess = { callback(true, it) },
                onFailure = { callback(false, it.message ?: "Sign in failed") }
            )
        }
    }

    fun loadServices() {
        viewModelScope.launch {
            _services.value = repository.getServices()
        }
    }

    fun addService(serviceName: String, description: String) {
        viewModelScope.launch {
            val service = FirebaseService(serviceName = serviceName, description = description)
            repository.addService(service)
            loadServices()
        }
    }

    fun loadProviders(serviceId: String) {
        viewModelScope.launch {
            _providers.value = repository.getProvidersByService(serviceId)
        }
    }

    fun addBooking(userId: String, providerId: String, address: String, contactInfo: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val booking = FirebaseBooking(userId = userId, providerId = providerId, address = address, contactInfo = contactInfo)
            repository.addBooking(booking).fold(
                onSuccess = { callback(true) },
                onFailure = { callback(false) }
            )
        }
    }
}