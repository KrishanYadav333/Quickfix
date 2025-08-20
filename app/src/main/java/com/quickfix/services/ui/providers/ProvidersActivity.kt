package com.quickfix.services.ui.providers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.ui.booking.BookingActivity

class ProvidersActivity : AppCompatActivity() {
    private lateinit var providerAdapter: ProviderAdapter
    private val providers = mutableListOf<SimpleProvider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)

        val serviceName = intent.getStringExtra("service_name") ?: "Service"
        val customerName = intent.getStringExtra("customer_name") ?: "Customer"
        val customerEmail = intent.getStringExtra("customer_email") ?: "guest@quickfix.com"
        title = "$serviceName Providers"

        setupRecyclerView()
        loadSampleProviders(serviceName)
        setupLogout()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProviders)
        providerAdapter = ProviderAdapter(providers) { provider ->
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("provider_name", provider.name)
            intent.putExtra("service_name", intent.getStringExtra("service_name"))
            intent.putExtra("customer_name", intent.getStringExtra("customer_name"))
            intent.putExtra("customer_email", intent.getStringExtra("customer_email"))
            startActivity(intent)
        }
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = providerAdapter
    }

    private fun loadSampleProviders(serviceName: String) {
        val serviceProviders = when(serviceName) {
            "Plumber" -> listOf(
                SimpleProvider("John Smith - Expert Plumber", "123-456-7890"),
                SimpleProvider("Mike Johnson - 24/7 Plumbing", "987-654-3210"),
                SimpleProvider("David Wilson - Quick Fix Plumbing", "555-123-4567")
            )
            "Electrician" -> listOf(
                SimpleProvider("Sarah Electric Services", "111-222-3333"),
                SimpleProvider("Tom's Electrical Works", "444-555-6666"),
                SimpleProvider("Lightning Fast Electric", "777-888-9999")
            )
            "Mechanic" -> listOf(
                SimpleProvider("Auto Repair Pro - Raj", "222-333-4444"),
                SimpleProvider("Car Doctor - Ahmed", "555-666-7777"),
                SimpleProvider("Bike & Car Fix - Kumar", "888-999-0000")
            )
            "Cleaning" -> listOf(
                SimpleProvider("Sparkle Clean Services", "333-444-5555"),
                SimpleProvider("Deep Clean Experts", "666-777-8888"),
                SimpleProvider("Home Shine Cleaning", "999-000-1111")
            )
            "Carpenter" -> listOf(
                SimpleProvider("Wood Master - Ravi", "111-333-5555"),
                SimpleProvider("Furniture Fix Pro", "222-444-6666"),
                SimpleProvider("Custom Wood Works", "333-555-7777")
            )
            "Painter" -> listOf(
                SimpleProvider("Color Perfect Painting", "444-666-8888"),
                SimpleProvider("Wall Art Painters", "555-777-9999"),
                SimpleProvider("Home Paint Experts", "666-888-0000")
            )
            "AC Repair" -> listOf(
                SimpleProvider("Cool Air Services", "777-999-1111"),
                SimpleProvider("AC Fix Masters", "888-000-2222"),
                SimpleProvider("Climate Control Pro", "999-111-3333")
            )
            "Appliance Repair" -> listOf(
                SimpleProvider("Fix It All Services", "000-222-4444"),
                SimpleProvider("Home Appliance Doctor", "111-333-5555"),
                SimpleProvider("Quick Appliance Fix", "222-444-6666")
            )
            "Pest Control" -> listOf(
                SimpleProvider("Bug Busters Services", "333-555-7777"),
                SimpleProvider("Pest Free Solutions", "444-666-8888"),
                SimpleProvider("Safe Pest Control", "555-777-9999")
            )
            "Gardening" -> listOf(
                SimpleProvider("Green Thumb Gardens", "666-888-0000"),
                SimpleProvider("Plant Care Experts", "777-999-1111"),
                SimpleProvider("Garden Maintenance Pro", "888-000-2222")
            )
            "Locksmith" -> listOf(
                SimpleProvider("24/7 Lock Services", "999-111-3333"),
                SimpleProvider("Key Master Solutions", "000-222-4444"),
                SimpleProvider("Secure Lock Experts", "111-333-5555")
            )
            "Beauty & Spa" -> listOf(
                SimpleProvider("Home Beauty Services", "222-444-6666"),
                SimpleProvider("Spa at Your Door", "333-555-7777"),
                SimpleProvider("Beauty Care Experts", "444-666-8888")
            )
            else -> listOf(
                SimpleProvider("Service Provider 1", "123-456-7890"),
                SimpleProvider("Service Provider 2", "987-654-3210"),
                SimpleProvider("Service Provider 3", "555-123-4567")
            )
        }
        providers.addAll(serviceProviders)
        providerAdapter.notifyDataSetChanged()
    }
    
    private fun setupLogout() {
        val buttonLogout = findViewById<android.widget.Button>(R.id.buttonLogoutProviders)
        buttonLogout.setOnClickListener {
            val intent = android.content.Intent(this, com.quickfix.services.ui.login.LoginActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}

data class SimpleProvider(val name: String, val contact: String)