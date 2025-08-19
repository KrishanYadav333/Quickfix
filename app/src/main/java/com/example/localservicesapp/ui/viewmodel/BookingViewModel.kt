package com.example.localservicesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localservicesapp.data.db.AppDatabase
import com.example.localservicesapp.data.model.Booking
import com.example.localservicesapp.data.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookingRepository
    
    init {
        val bookingDao = AppDatabase.getDatabase(application).bookingDao()
        repository = BookingRepository(bookingDao)
    }
    
    fun getAllBookings() = repository.getAllBookings()
    
    fun getBookingsByUserId(userId: Long) = repository.getBookingsByUserId(userId)
    
    fun getBookingById(id: Long) = viewModelScope.launch {
        repository.getBookingById(id)
    }
    
    fun insert(booking: Booking) = viewModelScope.launch {
        repository.insert(booking)
    }
    
    fun update(booking: Booking) = viewModelScope.launch {
        repository.update(booking)
    }
    
    fun delete(booking: Booking) = viewModelScope.launch {
        repository.delete(booking)
    }
}