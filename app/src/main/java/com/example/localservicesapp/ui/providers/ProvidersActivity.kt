package com.example.localservicesapp.ui.providers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.ui.booking.BookingActivity

class ProvidersActivity : AppCompatActivity() {
    private lateinit var providerAdapter: ProviderAdapter
    private val providers = mutableListOf<SimpleProvider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)

        val serviceName = intent.getStringExtra("service_name") ?: "Service"
        title = "$serviceName Providers"

        setupRecyclerView()
        loadSampleProviders(serviceName)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProviders)
        providerAdapter = ProviderAdapter(providers) { provider ->
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("provider_name", provider.name)
            startActivity(intent)
        }
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = providerAdapter
    }

    private fun loadSampleProviders(serviceName: String) {
        @Suppress("UNUSED_PARAMETER")
        providers.addAll(listOf(
            SimpleProvider("John Smith", "123-456-7890"),
            SimpleProvider("Sarah Johnson", "987-654-3210"),
            SimpleProvider("Mike Wilson", "555-123-4567")
        ))
        providerAdapter.notifyDataSetChanged()
    }
}

data class SimpleProvider(val name: String, val contact: String)