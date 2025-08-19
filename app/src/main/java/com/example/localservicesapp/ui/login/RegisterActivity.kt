package com.example.localservicesapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.localservicesapp.R
import com.example.localservicesapp.data.model.User
import com.example.localservicesapp.ui.services.ServicesActivity
import com.example.localservicesapp.ui.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextConfirmPassword: TextInputEditText
    private lateinit var radioGroupRole: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        radioGroupRole = findViewById(R.id.radioGroupRole)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonRegister.setOnClickListener {
            register()
        }

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun register() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()
        val selectedRoleId = radioGroupRole.checkedRadioButtonId
        val role = if (selectedRoleId == R.id.radioButtonAdmin) "admin" else "customer"

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Create user object
        val user = User(
            name = name,
            email = email,
            password = password,
            role = role
        )

        // Save user to database
        userViewModel.insert(user)

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

        // Navigate to services screen
        val intent = Intent(this, ServicesActivity::class.java)
        intent.putExtra("user_role", role)
        startActivity(intent)
        finish()
    }
}