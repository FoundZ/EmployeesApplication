package com.takumi.employee.data.di

import com.takumi.employee.data.DefaultEmployeesRepository
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.local.EmployeeLocalRepository
import com.takumi.employee.data.remote.EmployeeRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideDefaultEmployeesRepository(
        employeeLocalRepository: EmployeeLocalRepository,
        employeeRemoteRepository: EmployeeRemoteRepository
    ): EmployeesRepository = DefaultEmployeesRepository(
        employeeLocalRepository = employeeLocalRepository,
        employeeRemoteRepository = employeeRemoteRepository,
    )
}