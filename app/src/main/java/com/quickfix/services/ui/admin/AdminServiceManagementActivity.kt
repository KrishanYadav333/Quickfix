package com.quickfix.services.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.data.model.Service
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class AdminServiceManagementActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var serviceAdapter: AdminServiceAdapter
    private val services = mutableListOf<Service>()
    private var selectedImageUri: Uri? = null
    private lateinit var currentImageUrlEditText: com.google.android.material.textfield.TextInputEditText
    
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                currentImageUrlEditText.setText(uri.toString())
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_service_management)

        database = AppDatabase.getDatabase(this)
        setupRecyclerView()
        initializeServices()
        loadServices()

        val fabAddService = findViewById<FloatingActionButton>(R.id.fabAddService)
        fabAddService.setOnClickListener { showAddServiceDialog() }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAdminServices)
        serviceAdapter = AdminServiceAdapter(
            services = services,
            onToggleVisibility = { service -> toggleServiceVisibility(service) },
            onDeleteService = { service -> deleteService(service) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = serviceAdapter
    }

    private fun loadServices() {
        lifecycleScope.launch {
            database.serviceDao().getAllServices().collect { serviceList ->
                services.clear()
                services.addAll(serviceList)
                serviceAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun toggleServiceVisibility(service: Service) {
        lifecycleScope.launch {
            val newVisibility = !service.isVisible
            database.serviceDao().updateVisibility(service.id, newVisibility)
            val message = if (newVisibility) "${service.serviceName} is now visible" else "${service.serviceName} is now hidden"
            Toast.makeText(this@AdminServiceManagementActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteService(service: Service) {
        AlertDialog.Builder(this)
            .setTitle("Delete Service")
            .setMessage("Are you sure you want to delete ${service.serviceName}?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    database.serviceDao().delete(service)
                    Toast.makeText(this@AdminServiceManagementActivity, "Service deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddServiceDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_service, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()

        val editTextServiceName = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextServiceName)
        val editTextServiceDescription = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextServiceDescription)
        val editTextImageUrl = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextImageUrl)
        val buttonSelectImage = view.findViewById<android.widget.Button>(R.id.buttonSelectImage)
        val buttonSaveService = view.findViewById<android.widget.Button>(R.id.buttonSaveService)

        currentImageUrlEditText = editTextImageUrl
        
        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        buttonSaveService.setOnClickListener {
            val serviceName = editTextServiceName.text.toString().trim()
            val description = editTextServiceDescription.text.toString().trim()
            val imageUrl = editTextImageUrl.text.toString().trim()

            if (serviceName.isNotEmpty() && description.isNotEmpty()) {
                lifecycleScope.launch {
                    database.serviceDao().insert(
                        Service(
                            serviceName = serviceName,
                            description = description,
                            imageUrl = imageUrl,
                            isVisible = true
                        )
                    )
                    Toast.makeText(this@AdminServiceManagementActivity, "Service added successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this@AdminServiceManagementActivity, "Please fill service name and description", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun initializeServices() {
        lifecycleScope.launch {
            try {
                val existingServices = database.serviceDao().getAllServices().first()
                if (existingServices.isEmpty()) {
                    val sampleServices = listOf(
                        Service(serviceName = "Plumber", description = "Fix pipes, leaks & water issues", imageUrl = "", isVisible = true),
                        Service(serviceName = "Electrician", description = "Electrical repairs & installations", imageUrl = "", isVisible = true),
                        Service(serviceName = "Mechanic", description = "Car & bike repair services", imageUrl = "", isVisible = true),
                        Service(serviceName = "Cleaning", description = "Home & office cleaning", imageUrl = "", isVisible = true),
                        Service(serviceName = "Carpenter", description = "Furniture & wood work", imageUrl = "", isVisible = true),
                        Service(serviceName = "Painter", description = "Interior & exterior painting", imageUrl = "", isVisible = true),
                        Service(serviceName = "AC Repair", description = "AC installation & maintenance", imageUrl = "", isVisible = true),
                        Service(serviceName = "Appliance Repair", description = "Fix washing machine, fridge etc", imageUrl = "", isVisible = true),
                        Service(serviceName = "Pest Control", description = "Remove insects & pests", imageUrl = "", isVisible = true),
                        Service(serviceName = "Gardening", description = "Lawn care & plant maintenance", imageUrl = "", isVisible = true),
                        Service(serviceName = "Locksmith", description = "Lock repair & key services", imageUrl = "", isVisible = true),
                        Service(serviceName = "Beauty & Spa", description = "Home salon & spa services", imageUrl = "", isVisible = true)
                    )
                    sampleServices.forEach { database.serviceDao().insert(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}