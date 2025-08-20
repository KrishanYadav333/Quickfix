package com.quickfix.services.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class CustomerManagementActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var customerAdapter: CustomerAdapter
    private val customers = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_management)

        database = AppDatabase.getDatabase(this)
        setupRecyclerView()
        initializeDemoCustomers()
        loadCustomers()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCustomers)
        customerAdapter = CustomerAdapter(
            customers = customers,
            onBlockCustomer = { customer -> blockCustomer(customer) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customerAdapter
    }

    private fun loadCustomers() {
        lifecycleScope.launch {
            database.userDao().getAllUsers().collect { userList ->
                customers.clear()
                customers.addAll(userList)
                customerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun blockCustomer(customer: User) {
        lifecycleScope.launch {
            database.userDao().updateBlockStatus(customer.id, !customer.isBlocked)
        }
    }
    
    private fun initializeDemoCustomers() {
        lifecycleScope.launch {
            try {
                val existingUsers = database.userDao().getAllUsers().first()
                if (existingUsers.isEmpty()) {
                    val demoCustomers = listOf(
                        User(email = "john.doe@gmail.com", name = "John Doe", password = "password123", isBlocked = false),
                        User(email = "sarah.smith@yahoo.com", name = "Sarah Smith", password = "password123", isBlocked = false),
                        User(email = "mike.johnson@hotmail.com", name = "Mike Johnson", password = "password123", isBlocked = true),
                        User(email = "emma.wilson@gmail.com", name = "Emma Wilson", password = "password123", isBlocked = false),
                        User(email = "david.brown@outlook.com", name = "David Brown", password = "password123", isBlocked = false),
                        User(email = "lisa.garcia@gmail.com", name = "Lisa Garcia", password = "password123", isBlocked = false),
                        User(email = "tom.anderson@yahoo.com", name = "Tom Anderson", password = "password123", isBlocked = true),
                        User(email = "anna.martinez@gmail.com", name = "Anna Martinez", password = "password123", isBlocked = false)
                    )
                    demoCustomers.forEach { database.userDao().insert(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}