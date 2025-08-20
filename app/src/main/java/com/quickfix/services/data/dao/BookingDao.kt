package com.quickfix.services.data.dao

import androidx.room.*
import com.quickfix.services.data.model.Booking
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    fun getAllBookings(): Flow<List<Booking>>

    @Insert
    suspend fun insert(booking: Booking)

    @Update
    suspend fun update(booking: Booking)

    @Delete
    suspend fun delete(booking: Booking)
}