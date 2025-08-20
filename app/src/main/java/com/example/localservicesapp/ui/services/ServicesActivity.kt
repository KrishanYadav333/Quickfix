package com.example.localservicesapp.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localservicesapp.R
import com.example.localservicesapp.ui.providers.ProvidersActivity
import com.example.localservicesapp.data.database.AppDatabase
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
                Toast.makeText(this, "Edit ${service.name}", Toast.LENGTH_SHORT).show()
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

    private fun loadSampleServices() {
        services.addAll(listOf(
            SimpleService("Plumber", "Fix pipes, leaks & water issues"),
            SimpleService("Electrician", "Electrical repairs & installations"),
            SimpleService("Mechanic", "Car & bike repair services"),
            SimpleService("Cleaning", "Home & office cleaning"),
            SimpleService("Carpenter", "Furniture & wood work"),
            SimpleService("Painter", "Interior & exterior painting"),
            SimpleService("AC Repair", "AC installation & maintenance"),
            SimpleService("Appliance Repair", "Fix washing machine, fridge etc"),
            SimpleService("Pest Control", "Remove insects & pests"),
            SimpleService("Gardening", "Lawn care & plant maintenance"),
            SimpleService("Locksmith", "Lock repair & key services"),
            SimpleService("Beauty & Spa", "Home salon & spa services")
        ))
        serviceAdapter.notifyDataSetChanged()
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