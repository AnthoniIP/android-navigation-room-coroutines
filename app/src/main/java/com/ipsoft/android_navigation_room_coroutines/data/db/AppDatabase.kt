package com.ipsoft.android_navigation_room_coroutines.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ipsoft.android_navigation_room_coroutines.data.db.dao.UserDao

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       01/03/2021
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }

    fun getDatabase(context: Context): AppDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}