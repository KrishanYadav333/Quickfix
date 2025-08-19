package com.example.localservicesapp.data.repository

import com.example.localservicesapp.data.db.BookingDao
import com.example.localservicesapp.data.model.Booking

class BookingRepository(private val bookingDao: BookingDao) {
    fun getAllBookings() = bookingDao.getAllBookings()
    
    fun getBookingsByUserId(userId: Long) = bookingDao.getBookingsByUserId(userId)
    
    suspend fun getBookingById(id: Long) = bookingDao.getBookingById(id)
    
    suspend fun insert(booking: Booking) = bookingDao.insert(booking)
    
    suspend fun update(booking: Booking) = bookingDao.update(booking)
    
    suspend fun delete(booking: Booking) = bookingDao.delete(booking)
}