package com.quickfix.services.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.ui.services.ServicesActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = AppDatabase.getDatabase(this)
        
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            login()
        }

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Check for admin credentials
        if (email == "admin" && password == "admin123") {
            Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, com.quickfix.services.ui.admin.AdminDashboardActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Check database for customer credentials
        lifecycleScope.launch {
            try {
                val user = database.userDao().getUserByCredentials(email, password)
                if (user != null) {
                    if (user.isBlocked) {
                        Toast.makeText(this@LoginActivity, "Your account has been blocked. Contact admin.", Toast.LENGTH_LONG).show()
                        return@launch
                    }
                    
                    Toast.makeText(this@LoginActivity, "Welcome back, ${user.name}!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, ServicesActivity::class.java)
                    intent.putExtra("user_role", "customer")
                    intent.putExtra("customer_name", user.name)
                    intent.putExtra("customer_email", user.email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}