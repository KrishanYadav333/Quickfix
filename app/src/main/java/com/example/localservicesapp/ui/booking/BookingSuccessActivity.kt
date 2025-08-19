package com.example.localservicesapp.ui.booking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.localservicesapp.R
import com.example.localservicesapp.ui.services.ServicesActivity

class BookingSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_success)

        val buttonBackToServices = findViewById<Button>(R.id.buttonBackToServices)
        buttonBackToServices.setOnClickListener {
            // Navigate back to services screen
            val intent = Intent(this, ServicesActivity::class.java)
            // In a real app, you would pass the user role here
            intent.putExtra("user_role", "customer")
            startActivity(intent)
            finish()
        }
    }
}