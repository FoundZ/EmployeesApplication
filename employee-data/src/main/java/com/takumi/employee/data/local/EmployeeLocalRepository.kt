package com.takumi.employee.data.local

import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.local.database.bean.User
import com.takumi.employee.data.local.database.UserDao
import javax.inject.Inject

class EmployeeLocalRepository @Inject constructor(
    private val userDao: UserDao
) : EmployeesRepository {

    override suspend fun addEmployees(vararg user: User) {
        userDao.installUsers(*user)
    }

    override suspend fun deleteEmployee(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun updateEmployee(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun getAllEmployees(): List<User> {
        return userDao.getAllUserList()
    }

    override suspend fun getAverageAge(): Double {
        return userDao.getAverageAge()
    }

    override suspend fun getTotalGenderForType(gender: Int): Double {
        return userDao.getTotalGenderForType(gender = gender)
    }

    override suspend fun getTotalGender(): Double {
        return userDao.getTotalGender()
    }
}