package com.takumi.employee.data

import com.takumi.employee.data.local.database.bean.User

interface EmployeesRepository {

    suspend fun addEmployees(vararg user: User)

    suspend fun deleteEmployee(user: User)

    suspend fun updateEmployee(user: User)

    suspend fun getAllEmployees(): List<User>

    suspend fun getAverageAge(): Double

    suspend fun getTotalGenderForType(gender: Int): Double

    suspend fun getTotalGender(): Double
}