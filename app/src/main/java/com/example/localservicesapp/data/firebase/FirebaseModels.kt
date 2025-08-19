package com.example.localservicesapp.data.firebase

data class FirebaseUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "customer"
)

data class FirebaseService(
    val id: String = "",
    val serviceName: String = "",
    val description: String = ""
)

data class FirebaseProvider(
    val id: String = "",
    val serviceId: String = "",
    val name: String = "",
    val contact: String = ""
)

data class FirebaseBooking(
    val id: String = "",
    val userId: String = "",
    val providerId: String = "",
    val address: String = "",
    val contactInfo: String = "",
    val timestamp: Long = System.currentTimeMillis()
)