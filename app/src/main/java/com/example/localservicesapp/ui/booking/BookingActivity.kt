package com.example.localservicesapp.ui.booking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.Booking
import com.example.localservicesapp.ui.viewmodel.BookingViewModel
import com.google.android.material.textfield.TextInputEditText

class BookingActivity : AppCompatActivity() {
    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var editTextProviderName: TextInputEditText
    private lateinit var editTextAddress: TextInputEditText
    private lateinit var editTextContactInfo: TextInputEditText
    private var providerId: Long = 0
    private var userId: Long = 1 // In a real app, you would get this from the logged-in user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Setup ViewModel
        bookingViewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        // Get provider ID and name from intent
        providerId = intent.getLongExtra("provider_id", 0)
        val providerName = intent.getStringExtra("provider_name") ?: "Unknown Provider"

        // Initialize views
        editTextProviderName = findViewById(R.id.editTextProviderName)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextContactInfo = findViewById(R.id.editTextContactInfo)
        val buttonConfirmBooking = findViewById<Button>(R.id.buttonConfirmBooking)

        // Set provider name
        editTextProviderName.setText(providerName)

        // Setup button click listener
        buttonConfirmBooking.setOnClickListener {
            confirmBooking()
        }
    }

    private fun confirmBooking() {
        val address = editTextAddress.text.toString().trim()
        val contactInfo = editTextContactInfo.text.toString().trim()

        if (address.isEmpty() || contactInfo.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create booking object
        val booking = Booking(
            userId = userId,
            providerId = providerId,
            address = address,
            contactInfo = contactInfo
        )

        // Save booking to database
        bookingViewModel.insert(booking)

        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show()

        // Navigate to success screen
        val intent = Intent(this, BookingSuccessActivity::class.java)
        startActivity(intent)
        finish()
    }
}