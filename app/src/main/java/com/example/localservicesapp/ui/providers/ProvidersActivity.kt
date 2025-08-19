package com.example.localservicesapp.ui.providers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.Provider
import com.example.localservicesapp.ui.booking.BookingActivity
import com.example.localservicesapp.ui.viewmodel.ProviderViewModel

class ProvidersActivity : AppCompatActivity() {
    private lateinit var providerViewModel: ProviderViewModel
    private lateinit var providerAdapter: ProviderAdapter
    private var serviceId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)

        // Get service ID from intent
        serviceId = intent.getLongExtra("service_id", 0)
        val serviceName = intent.getStringExtra("service_name") ?: "Providers"

        // Set toolbar title
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = serviceName

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProviders)
        providerAdapter = ProviderAdapter(
            providers = emptyList(),
            onBookClick = { provider ->
                // Navigate to booking screen
                val intent = Intent(this, BookingActivity::class.java)
                intent.putExtra("provider_id", provider.id)
                intent.putExtra("provider_name", provider.name)
                startActivity(intent)
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = providerAdapter

        // Setup ViewModel
        providerViewModel = ViewModelProvider(this)[ProviderViewModel::class.java]
        
        // Observe providers for the selected service
        providerViewModel.getProvidersByServiceId(serviceId).observe(this) { providers ->
            if (providers.isEmpty()) {
                // Add sample providers if none exist
                addSampleProviders(serviceName)
            } else {
                providerAdapter.updateProviders(providers)
            }
        }
    }

    private fun addSampleProviders(serviceName: String) {
        val sampleProviders = listOf(
            Provider(serviceId = serviceId, name = "John's $serviceName", contact = "123-456-7890"),
            Provider(serviceId = serviceId, name = "Professional $serviceName Co.", contact = "098-765-4321"),
            Provider(serviceId = serviceId, name = "Quick $serviceName Service", contact = "555-123-4567")
        )
        
        sampleProviders.forEach { provider ->
            providerViewModel.insert(provider)
        }
    }
}