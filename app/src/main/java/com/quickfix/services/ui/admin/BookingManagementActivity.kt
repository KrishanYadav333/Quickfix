package com.quickfix.services.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.data.model.Booking
import kotlinx.coroutines.launch

class BookingManagementActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var bookingAdapter: BookingAdapter
    private val bookings = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_management)

        database = AppDatabase.getDatabase(this)
        setupRecyclerView()
        loadBookings()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBookings)
        bookingAdapter = BookingAdapter(bookings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookingAdapter
    }

    private fun loadBookings() {
        lifecycleScope.launch {
            database.bookingDao().getAllBookings().collect { bookingList ->
                bookings.clear()
                bookings.addAll(bookingList)
                bookingAdapter.notifyDataSetChanged()
            }
        }
    }
}