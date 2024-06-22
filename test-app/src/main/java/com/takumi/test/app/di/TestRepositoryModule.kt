package com.takumi.test.app.di

import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.di.DataModule
import com.takumi.employee.data.fake.forkDataList
import com.takumi.employee.data.local.database.bean.User
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [DataModule::class]
)
class TestRepositoryModule {

    @Provides
    fun provideDefaultEmployeesRepository(): EmployeesRepository = FakeEmployeesRepository()
}

class FakeEmployeesRepository : EmployeesRepository {

    private val userList = mutableListOf<User>().apply {
        this.addAll(forkDataList)
    }

    override suspend fun addEmployees(vararg user: User) {
        userList.addAll(user)
    }

    override suspend fun deleteEmployee(user: User) {
        userList.removeIf {
            it.uid == user.uid
        }
    }

    override suspend fun updateEmployee(user: User) {
        val index = userList.indexOfFirst {
            it.uid == user.uid
        }
        if (index != -1) {
            userList[index] = user
        }
    }

    override suspend fun getAllEmployees(): List<User> {
        return userList
    }

    override suspend fun getAverageAge(): Double {
        val size = userList.size
        if (size == 0) {
            return 0.0
        }
        val sumAge = userList.sumOf { it.age }
        return sumAge.toDouble() / size.toDouble()
    }

    override suspend fun getTotalGenderForType(gender: Int): Double {
        return userList.filter { it.gender == gender }.size.toDouble()
    }

    override suspend fun getTotalGender(): Double {
        return userList.size.toDouble()
    }

}