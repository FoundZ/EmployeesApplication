package com.takumi.employee.data.local.di

import android.content.Context
import androidx.room.Room
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.local.EmployeeLocalRepository
import com.takumi.employee.data.local.database.AppDatabase
import com.takumi.employee.data.local.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideEmployeeLocalRepository(userDao: UserDao): EmployeeLocalRepository = EmployeeLocalRepository(userDao)

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME).build()

}

const val DATABASE_NAME = "EmployeesDataBase"
