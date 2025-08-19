package com.example.localservicesapp.data.db

import androidx.room.*
import com.example.localservicesapp.data.model.Booking
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    fun getBookingsByUserId(userId: Long): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE id = :id LIMIT 1")
    suspend fun getBookingById(id: Long): Booking?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(booking: Booking): Long

    @Update
    suspend fun update(booking: Booking)

    @Delete
    suspend fun delete(booking: Booking)
}