package com.takumi.employee.data.remote.di

import com.takumi.employee.data.remote.EmployeeRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    fun provideEmployeeRemoteRepository(): EmployeeRemoteRepository = EmployeeRemoteRepository()
}