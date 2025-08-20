package com.quickfix.services.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.quickfix.services.R
import com.quickfix.services.ui.admin.AdminDashboardActivity

class AdminAccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_access)

        val buttonDirectAdmin = findViewById<Button>(R.id.buttonDirectAdmin)
        buttonDirectAdmin.setOnClickListener {
            val intent = Intent(this, AdminDashboardActivity::class.java)
            startActivity(intent)
        }
    }
}