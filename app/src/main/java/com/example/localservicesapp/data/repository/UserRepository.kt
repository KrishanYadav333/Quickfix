package com.example.localservicesapp.data.repository

import com.example.localservicesapp.data.db.UserDao
import com.example.localservicesapp.data.model.User

class UserRepository(private val userDao: UserDao) {
    fun getAllUsers() = userDao.getAllUsers()
    
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    
    suspend fun insert(user: User) = userDao.insert(user)
    
    suspend fun update(user: User) = userDao.update(user)
    
    suspend fun delete(user: User) = userDao.delete(user)
}