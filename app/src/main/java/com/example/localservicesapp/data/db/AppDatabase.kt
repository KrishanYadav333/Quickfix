package com.example.localservicesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.localservicesapp.data.model.Booking
import com.example.localservicesapp.data.model.Provider
import com.example.localservicesapp.data.model.Service
import com.example.localservicesapp.data.model.User

@Database(
    entities = [User::class, Service::class, Provider::class, Booking::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun serviceDao(): ServiceDao
    abstract fun providerDao(): ProviderDao
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "local_services_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}