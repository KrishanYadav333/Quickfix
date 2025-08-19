package com.example.localservicesapp.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.Service
import com.example.localservicesapp.ui.login.LoginActivity
import com.example.localservicesapp.ui.providers.ProvidersActivity
import com.example.localservicesapp.ui.viewmodel.ServiceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServicesActivity : AppCompatActivity() {
    private lateinit var serviceViewModel: ServiceViewModel
    private lateinit var serviceAdapter: ServiceAdapter
    private var isAdmin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        // Get user role from intent
        val userRole = intent.getStringExtra("user_role") ?: "customer"
        isAdmin = userRole == "admin"

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewServices)
        serviceAdapter = ServiceAdapter(
            services = emptyList(),
            isAdmin = isAdmin,
            onServiceClick = { service ->
                if (!isAdmin) {
                    // Navigate to providers screen for customers
                    val intent = Intent(this, ProvidersActivity::class.java)
                    intent.putExtra("service_id", service.id)
                    intent.putExtra("service_name", service.serviceName)
                    startActivity(intent)
                }
            },
            onEditClick = { service ->
                // In a real app, you would open an edit dialog or activity
                Toast.makeText(this, "Edit ${service.serviceName}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { service ->
                // In a real app, you would show a confirmation dialog
                serviceViewModel.delete(service)
                Toast.makeText(this, "Deleted ${service.serviceName}", Toast.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = serviceAdapter

        // Setup ViewModel
        serviceViewModel = ViewModelProvider(this)[ServiceViewModel::class.java]
        
        // Observe services
        serviceViewModel.getAllServices().observe(this) { services ->
            if (services.isEmpty()) {
                // Add sample services if none exist
                addSampleServices()
            } else {
                serviceAdapter.updateServices(services)
            }
        }

        // Setup FAB for admin
        val fabAddService = findViewById<FloatingActionButton>(R.id.fabAddService)
        if (isAdmin) {
            fabAddService.visibility = android.view.View.VISIBLE
            fabAddService.setOnClickListener {
                // In a real app, you would open an add service dialog or activity
                addSampleService()
            }
        } else {
            fabAddService.visibility = android.view.View.GONE
        }
    }

    private fun addSampleService() {
        val service = Service(
            serviceName = "New Service",
            description = "Sample service description"
        )
        serviceViewModel.insert(service)
        Toast.makeText(this, "Added new service", Toast.LENGTH_SHORT).show()
    }

    private fun addSampleServices() {
        val sampleServices = listOf(
            Service(serviceName = "Plumbing", description = "Professional plumbing services for your home"),
            Service(serviceName = "Electrical", description = "Licensed electrical work and repairs"),
            Service(serviceName = "Cleaning", description = "House cleaning and maintenance services"),
            Service(serviceName = "Gardening", description = "Lawn care and garden maintenance"),
            Service(serviceName = "Painting", description = "Interior and exterior painting services")
        )
        
        sampleServices.forEach { service ->
            serviceViewModel.insert(service)
        }
    }
}