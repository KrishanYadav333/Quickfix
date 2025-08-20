package com.quickfix.services.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.ui.providers.ProvidersActivity
import com.quickfix.services.data.database.AppDatabase
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ServicesActivity : AppCompatActivity() {
    private lateinit var serviceAdapter: ServiceAdapter
    private var isAdmin = false
    private val services = mutableListOf<SimpleService>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val userRole = intent.getStringExtra("user_role") ?: "customer"
        isAdmin = userRole == "admin"
        
        database = AppDatabase.getDatabase(this)
        
        // Force database creation and populate with data
        lifecycleScope.launch {
            val serviceDao = database.serviceDao()
            val existingServices = serviceDao.getAllServices().first()
            if (existingServices.isEmpty()) {
                insertSampleServices()
            }
        }

        setupRecyclerView()
        loadSampleServices()
        setupFab()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewServices)
        serviceAdapter = ServiceAdapter(
            services = services,
            isAdmin = isAdmin,
            onServiceClick = { service ->
                if (!isAdmin) {
                    val intent = Intent(this, ProvidersActivity::class.java)
                    intent.putExtra("service_name", service.name)
                    startActivity(intent)
                }
            },
            onEditClick = { service ->
                // Edit functionality
            },
            onDeleteClick = { service ->
                services.remove(service)
                serviceAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Deleted ${service.name}", Toast.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        recyclerView.adapter = serviceAdapter
    }

    private fun insertSampleServices() {
        val serviceDao = database.serviceDao()
        lifecycleScope.launch {
            val sampleServices = listOf(
                com.quickfix.services.data.model.Service(serviceName = "Plumber", description = "Fix pipes, leaks & water issues"),
                com.quickfix.services.data.model.Service(serviceName = "Electrician", description = "Electrical repairs & installations"),
                com.quickfix.services.data.model.Service(serviceName = "Mechanic", description = "Car & bike repair services"),
                com.quickfix.services.data.model.Service(serviceName = "Cleaning", description = "Home & office cleaning"),
                com.quickfix.services.data.model.Service(serviceName = "Carpenter", description = "Furniture & wood work"),
                com.quickfix.services.data.model.Service(serviceName = "Painter", description = "Interior & exterior painting"),
                com.quickfix.services.data.model.Service(serviceName = "AC Repair", description = "AC installation & maintenance"),
                com.quickfix.services.data.model.Service(serviceName = "Appliance Repair", description = "Fix washing machine, fridge etc"),
                com.quickfix.services.data.model.Service(serviceName = "Pest Control", description = "Remove insects & pests"),
                com.quickfix.services.data.model.Service(serviceName = "Gardening", description = "Lawn care & plant maintenance"),
                com.quickfix.services.data.model.Service(serviceName = "Locksmith", description = "Lock repair & key services"),
                com.quickfix.services.data.model.Service(serviceName = "Beauty & Spa", description = "Home salon & spa services")
            )
            sampleServices.forEach { serviceDao.insert(it) }
        }
    }

    private fun loadSampleServices() {
        // Load services from database
        lifecycleScope.launch {
            database.serviceDao().getAllServices().collect { dbServices ->
                services.clear()
                services.addAll(dbServices.map { SimpleService(it.serviceName, it.description) })
                serviceAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupFab() {
        val fabAddService = findViewById<FloatingActionButton>(R.id.fabAddService)
        if (isAdmin) {
            fabAddService.visibility = android.view.View.VISIBLE
            fabAddService.setOnClickListener {
                services.add(SimpleService("New Service", "Sample description"))
                serviceAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Added new service", Toast.LENGTH_SHORT).show()
            }
        } else {
            fabAddService.visibility = android.view.View.GONE
        }
    }
}

data class SimpleService(val name: String, val description: String)