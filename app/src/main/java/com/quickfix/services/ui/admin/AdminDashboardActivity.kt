package com.quickfix.services.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.ui.services.ServicesActivity
import kotlinx.coroutines.launch

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        database = AppDatabase.getDatabase(this)
        
        val textWelcome = findViewById<TextView>(R.id.textWelcomeAdmin)
        val textStats = findViewById<TextView>(R.id.textStats)
        val buttonManageServices = findViewById<Button>(R.id.buttonManageServices)
        val buttonViewBookings = findViewById<Button>(R.id.buttonViewBookings)
        val buttonManageCustomers = findViewById<Button>(R.id.buttonManageCustomers)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        textWelcome.text = "Welcome, Admin"
        loadStats(textStats)

        buttonManageServices.setOnClickListener {
            val intent = Intent(this, AdminServiceManagementActivity::class.java)
            startActivity(intent)
        }

        buttonViewBookings.setOnClickListener {
            val intent = Intent(this, BookingManagementActivity::class.java)
            startActivity(intent)
        }
        
        buttonManageCustomers.setOnClickListener {
            val intent = Intent(this, CustomerManagementActivity::class.java)
            startActivity(intent)
        }

        buttonLogout.setOnClickListener {
            finish()
        }
    }

    private fun loadStats(textStats: TextView) {
        lifecycleScope.launch {
            database.serviceDao().getAllServices().collect { services ->
                database.providerDao().getAllProviders().collect { providers ->
                    database.userDao().getAllUsers().collect { users ->
                        textStats.text = "Services: ${services.size} | Providers: ${providers.size} | Customers: ${users.size}"
                    }
                }
            }
        }
    }
}