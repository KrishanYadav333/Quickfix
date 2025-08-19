package com.example.localservicesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localservicesapp.data.db.AppDatabase
import com.example.localservicesapp.data.model.User
import com.example.localservicesapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    
    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    
    fun getAllUsers() = repository.getAllUsers()
    
    fun getUserByEmail(email: String) = viewModelScope.launch {
        repository.getUserByEmail(email)
    }
    
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
    
    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }
    
    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }
}