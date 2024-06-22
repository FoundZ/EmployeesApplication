package com.takumi.employee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.takumi.employee.data.local.database.bean.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}