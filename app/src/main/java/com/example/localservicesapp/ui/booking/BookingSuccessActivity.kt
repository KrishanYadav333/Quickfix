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

        val buttonBackToHome = findViewById<Button>(R.id.buttonBackToHome)
        
        buttonBackToHome.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}