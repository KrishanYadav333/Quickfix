package com.quickfix.services.ui.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.quickfix.services.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {
    private lateinit var editTextAddress: TextInputEditText
    private lateinit var editTextContactInfo: TextInputEditText
    private lateinit var buttonSelectDate: Button
    private lateinit var buttonSelectTime: Button
    private lateinit var textSelectedDateTime: TextView
    private var providerName: String = ""
    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        providerName = intent.getStringExtra("provider_name") ?: "Unknown Provider"

        editTextAddress = findViewById(R.id.editTextAddress)
        editTextContactInfo = findViewById(R.id.editTextContactInfo)
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        buttonSelectTime = findViewById(R.id.buttonSelectTime)
        textSelectedDateTime = findViewById(R.id.textSelectedDateTime)
        val buttonConfirmBooking = findViewById<Button>(R.id.buttonConfirmBooking)

        buttonSelectDate.setOnClickListener { showDatePicker() }
        buttonSelectTime.setOnClickListener { showTimePicker() }
        buttonConfirmBooking.setOnClickListener { confirmBooking() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                selectedDate = dateFormat.format(selectedCalendar.time)
                buttonSelectDate.text = selectedDate
                updateDateTimeDisplay()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val timeCalendar = Calendar.getInstance()
                timeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                timeCalendar.set(Calendar.MINUTE, minute)
                selectedTime = timeFormat.format(timeCalendar.time)
                buttonSelectTime.text = selectedTime
                updateDateTimeDisplay()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun updateDateTimeDisplay() {
        if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
            textSelectedDateTime.text = "$selectedDate at $selectedTime"
        } else if (selectedDate.isNotEmpty()) {
            textSelectedDateTime.text = "Date: $selectedDate"
        } else if (selectedTime.isNotEmpty()) {
            textSelectedDateTime.text = "Time: $selectedTime"
        }
    }

    private fun confirmBooking() {
        val address = editTextAddress.text.toString().trim()
        val contactInfo = editTextContactInfo.text.toString().trim()

        if (address.isEmpty() || contactInfo.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select date/time", Toast.LENGTH_SHORT).show()
            return
        }

        // Save booking to database with customer data
        val serviceName = intent.getStringExtra("service_name") ?: "Unknown Service"
        val customerName = intent.getStringExtra("customer_name") ?: "Guest Customer"
        val customerEmail = intent.getStringExtra("customer_email") ?: "guest@quickfix.com"
        
        val booking = com.quickfix.services.data.model.Booking(
            serviceName = serviceName,
            providerName = providerName,
            customerName = customerName,
            customerEmail = customerEmail,
            customerAddress = address,
            customerContact = contactInfo,
            bookingDate = selectedDate,
            bookingTime = selectedTime
        )
        
        lifecycleScope.launch {
            com.quickfix.services.data.database.AppDatabase.getDatabase(this@BookingActivity)
                .bookingDao().insert(booking)
        }

        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, BookingSuccessActivity::class.java)
        intent.putExtra("provider_name", providerName)
        intent.putExtra("booking_date", selectedDate)
        intent.putExtra("booking_time", selectedTime)
        startActivity(intent)
        finish()
    }
}