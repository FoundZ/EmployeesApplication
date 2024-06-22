package com.takumi.employee.data

import com.takumi.employee.data.local.database.bean.User
import javax.inject.Inject

class DefaultEmployeesRepository @Inject constructor(
    private val employeeLocalRepository: EmployeesRepository,
    private val employeeRemoteRepository: EmployeesRepository,
) : EmployeesRepository {

    override suspend fun addEmployees(vararg user: User) {
        employeeLocalRepository.addEmployees(*user)
    }

    override suspend fun deleteEmployee(user: User) {
        employeeLocalRepository.deleteEmployee(user)
    }

    override suspend fun updateEmployee(user: User) {
        employeeLocalRepository.updateEmployee(user)
    }

    override suspend fun getAllEmployees(): List<User> {
        var dataList = employeeLocalRepository.getAllEmployees()
        if (dataList.isEmpty()) {
            val remoteDataList = employeeRemoteRepository.getAllEmployees()
            employeeLocalRepository.addEmployees(*remoteDataList.toTypedArray())
            dataList = remoteDataList
        }
        return dataList
    }

    override suspend fun getAverageAge(): Double {
        return employeeLocalRepository.getAverageAge()
    }

    override suspend fun getTotalGenderForType(gender: Int): Double {
        return employeeLocalRepository.getTotalGenderForType(gender)
    }

    override suspend fun getTotalGender(): Double {
        return employeeLocalRepository.getTotalGender()
    }
}