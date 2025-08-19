package com.example.localservicesapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.localservicesapp.R
import com.example.localservicesapp.ui.services.ServicesActivity
import com.example.localservicesapp.ui.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

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

        // In a real app, you would verify the credentials against the database
        // For now, we'll just navigate to the services screen
        val intent = Intent(this, ServicesActivity::class.java)
        intent.putExtra("user_role", "customer") // Default to customer
        startActivity(intent)
        finish()
    }
}