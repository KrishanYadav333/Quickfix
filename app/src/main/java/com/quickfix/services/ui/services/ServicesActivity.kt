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
    private val services = mutableListOf<com.quickfix.services.data.model.Service>()
    private lateinit var database: AppDatabase
    private var customerName = ""
    private var customerEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val userRole = intent.getStringExtra("user_role") ?: "customer"
        isAdmin = userRole == "admin"
        customerName = intent.getStringExtra("customer_name") ?: "Customer"
        customerEmail = intent.getStringExtra("customer_email") ?: "guest@quickfix.com"
        
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
        setupLogout()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewServices)
        serviceAdapter = ServiceAdapter(
            services = services,
            isAdmin = isAdmin,
            onServiceClick = { service ->
                if (!isAdmin) {
                    val intent = Intent(this, ProvidersActivity::class.java)
                    intent.putExtra("service_name", service.serviceName)
                    intent.putExtra("customer_name", customerName)
                    intent.putExtra("customer_email", customerEmail)
                    startActivity(intent)
                }
            },
            onEditClick = { _ ->
                // Edit functionality
            },
            onDeleteClick = { service ->
                lifecycleScope.launch {
                    database.serviceDao().delete(service)
                    Toast.makeText(this@ServicesActivity, "Deleted ${service.serviceName}", Toast.LENGTH_SHORT).show()
                }
            },
            onToggleVisibility = { service ->
                lifecycleScope.launch {
                    database.serviceDao().updateVisibility(service.id, !service.isVisible)
                    val action = if (service.isVisible) "hidden" else "shown"
                    Toast.makeText(this@ServicesActivity, "${service.serviceName} $action", Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        recyclerView.adapter = serviceAdapter
    }

    private fun insertSampleServices() {
        val serviceDao = database.serviceDao()
        lifecycleScope.launch {
            try {
                val sampleServices = listOf(
                    com.quickfix.services.data.model.Service(serviceName = "Plumber", description = "Fix pipes, leaks & water issues", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Electrician", description = "Electrical repairs & installations", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Mechanic", description = "Car & bike repair services", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Cleaning", description = "Home & office cleaning", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Carpenter", description = "Furniture & wood work", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Painter", description = "Interior & exterior painting", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "AC Repair", description = "AC installation & maintenance", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Appliance Repair", description = "Fix washing machine, fridge etc", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Pest Control", description = "Remove insects & pests", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Gardening", description = "Lawn care & plant maintenance", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Locksmith", description = "Lock repair & key services", imageUrl = "", isVisible = true),
                    com.quickfix.services.data.model.Service(serviceName = "Beauty & Spa", description = "Home salon & spa services", imageUrl = "", isVisible = true)
                )
                sampleServices.forEach { serviceDao.insert(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadSampleServices() {
        // Load services from database based on user role
        lifecycleScope.launch {
            val servicesFlow = if (isAdmin) {
                database.serviceDao().getAllServices()
            } else {
                database.serviceDao().getVisibleServices()
            }
            
            servicesFlow.collect { dbServices ->
                services.clear()
                services.addAll(dbServices)
                serviceAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupFab() {
        val fabAddService = findViewById<FloatingActionButton>(R.id.fabAddService)
        if (isAdmin) {
            fabAddService.visibility = android.view.View.VISIBLE
            fabAddService.setOnClickListener {
                lifecycleScope.launch {
                    val newService = com.quickfix.services.data.model.Service(
                        serviceName = "New Service",
                        description = "Sample description",
                        isVisible = true
                    )
                    database.serviceDao().insert(newService)
                    Toast.makeText(this@ServicesActivity, "Added new service", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            fabAddService.visibility = android.view.View.GONE
        }
    }
    
    private fun setupLogout() {
        val buttonLogout = findViewById<android.widget.Button>(R.id.buttonLogout)
        if (!isAdmin) {
            buttonLogout.visibility = android.view.View.VISIBLE
            buttonLogout.setOnClickListener {
                val intent = android.content.Intent(this, com.quickfix.services.ui.login.LoginActivity::class.java)
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        } else {
            buttonLogout.visibility = android.view.View.GONE
        }
    }
}