package com.quickfix.services.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.quickfix.services.R
import com.quickfix.services.data.database.AppDatabase
import com.quickfix.services.data.model.User
import com.quickfix.services.ui.services.ServicesActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var radioGroupRole: RadioGroup
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = AppDatabase.getDatabase(this)
        
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        radioGroupRole = findViewById(R.id.radioGroupRole)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonRegister.setOnClickListener {
            register()
        }
        
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun register() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val role = "customer" // Default role
        
        // Check if user already exists
        lifecycleScope.launch {
            try {
                val existingUser = database.userDao().getUserByEmail(email)
                if (existingUser != null) {
                    Toast.makeText(this@RegisterActivity, "Email already registered!", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                
                val user = User(
                    email = email,
                    name = name,
                    password = password,
                    isBlocked = false
                )
                database.userDao().insert(user)
                
                Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                
                val intent = Intent(this@RegisterActivity, ServicesActivity::class.java)
                intent.putExtra("user_role", role)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}