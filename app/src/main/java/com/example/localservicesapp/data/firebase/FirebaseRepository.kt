package com.example.localservicesapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Auth
    suspend fun signUp(email: String, password: String, name: String, role: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User creation failed")
            
            val user = FirebaseUser(userId, name, email, role)
            firestore.collection("users").document(userId).set(user).await()
            
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Services
    suspend fun getServices(): List<FirebaseService> {
        return try {
            firestore.collection("services").get().await()
                .toObjects(FirebaseService::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addService(service: FirebaseService): Result<String> {
        return try {
            val docRef = firestore.collection("services").add(service).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Providers
    suspend fun getProvidersByService(serviceId: String): List<FirebaseProvider> {
        return try {
            firestore.collection("providers")
                .whereEqualTo("serviceId", serviceId)
                .get().await()
                .toObjects(FirebaseProvider::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Bookings
    suspend fun addBooking(booking: FirebaseBooking): Result<String> {
        return try {
            val docRef = firestore.collection("bookings").add(booking).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}