package com.takumi.employee.data.remote

import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.fake.forkDataList
import com.takumi.employee.data.local.database.bean.User


class EmployeeRemoteRepository : EmployeesRepository {


    override suspend fun addEmployees(vararg user: User) {
        // do nothing
    }

    override suspend fun deleteEmployee(user: User) {
        // do nothing
    }

    override suspend fun updateEmployee(user: User) {
        // do nothing
    }

    override suspend fun getAllEmployees(): List<User> {
        return forkDataList
    }

    override suspend fun getAverageAge(): Double {
        // do nothing
        return 0.0
    }

    override suspend fun getTotalGenderForType(gender: Int): Double {
        // do nothing
        return 0.0
    }

    override suspend fun getTotalGender(): Double {
        // do nothing
        return 0.0
    }
}