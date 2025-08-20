package com.example.localservicesapp.ui.booking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.localservicesapp.R
import com.google.android.material.textfield.TextInputEditText

class BookingActivity : AppCompatActivity() {
    private lateinit var editTextAddress: TextInputEditText
    private lateinit var editTextContactInfo: TextInputEditText
    private var providerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        providerName = intent.getStringExtra("provider_name") ?: "Unknown Provider"

        editTextAddress = findViewById(R.id.editTextAddress)
        editTextContactInfo = findViewById(R.id.editTextContactInfo)
        val buttonConfirmBooking = findViewById<Button>(R.id.buttonConfirmBooking)

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

        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, BookingSuccessActivity::class.java)
        intent.putExtra("provider_name", providerName)
        startActivity(intent)
        finish()
    }
}