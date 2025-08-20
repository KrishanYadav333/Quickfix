package com.quickfix.services.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.quickfix.services.data.dao.ServiceDao
import com.quickfix.services.data.dao.ProviderDao
import com.quickfix.services.data.dao.UserDao
import com.quickfix.services.data.dao.BookingDao
import com.quickfix.services.data.model.Service
import com.quickfix.services.data.model.Provider
import com.quickfix.services.data.model.User
import com.quickfix.services.data.model.Booking

@Database(
    entities = [Service::class, Provider::class, User::class, Booking::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao
    abstract fun providerDao(): ProviderDao
    abstract fun userDao(): UserDao
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quickfix_pro_db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}