package com.takumi.employees.data

import com.takumi.employee.data.DefaultEmployeesRepository
import com.takumi.employee.data.fake.fakeRandomDataList
import com.takumi.employee.data.fake.forkDataList
import com.takumi.employee.data.local.EmployeeLocalRepository
import com.takumi.employee.data.local.database.UserDao
import com.takumi.employee.data.local.database.bean.User
import com.takumi.employee.data.remote.EmployeeRemoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DefaultEmployeesRepositoryUnitTest {

    private lateinit var localRepository: EmployeeLocalRepository
    private lateinit var remoteRepository: EmployeeRemoteRepository

    private lateinit var repository: DefaultEmployeesRepository

    @Before
    fun repository_newInstance() {
        localRepository = EmployeeLocalRepository(FakeUserDao())
        remoteRepository = EmployeeRemoteRepository()
        repository = DefaultEmployeesRepository(localRepository, remoteRepository)
    }

    @Test
    fun repository_local_newItemSaved_itemReturn() = runTest {
        // add and search
        val fakeDataList = fakeRandomDataList()
        localRepository.addEmployees(*fakeDataList.toTypedArray())
        var allEmployees = localRepository.getAllEmployees()
        assertEquals(allEmployees.size, fakeDataList.size)

        // delete
        localRepository.deleteEmployee(allEmployees.get(Random.nextInt(0, allEmployees.size - 1)))
        allEmployees = localRepository.getAllEmployees()
        val changeCount = fakeDataList.size - allEmployees.size
        assertEquals(changeCount, 1)

        // update
        val updateData = allEmployees[Random.nextInt(0, allEmployees.size - 1)]
        val newData = User(
            uid = updateData.uid,
            firstName = updateData.firstName + "newData",
            name = updateData.name + "newData",
            age = updateData.age + 100,
            gender = updateData.gender
        )
        localRepository.updateEmployee(newData)
        allEmployees = localRepository.getAllEmployees()
        val changedData = allEmployees.find { it.uid == updateData.uid }

        val result =
            changedData?.uid == newData.uid && changedData.age == newData.age && changedData.gender == newData.gender && changedData.name == newData.name && changedData.firstName == newData.firstName
        assertEquals(result, true)
    }

    @Test
    fun repository_remote_newItemSaved_itemReturn() = runTest {
        assertEquals(remoteRepository.getAllEmployees().size, forkDataList.size)
    }

    @Test
    fun repository_newItemSaved_itemReturn() = runTest {
        val fakeDataList = forkDataList.toMutableList()
        // search
        var allEmployees = repository.getAllEmployees()
        assertEquals(allEmployees.size, fakeDataList.size)
        // add
        val newFakeDataList = fakeRandomDataList()
        fakeDataList.addAll(newFakeDataList)
        repository.addEmployees(*newFakeDataList.toTypedArray())
        allEmployees = repository.getAllEmployees()
        assertEquals(allEmployees.size, fakeDataList.size)

        // delete
        repository.deleteEmployee(allEmployees.get(Random.nextInt(0, allEmployees.size - 1)))
        allEmployees = repository.getAllEmployees()
        val changeCount = fakeDataList.size - allEmployees.size
        assertEquals(changeCount, 1)

        // update
        val updateData = allEmployees[Random.nextInt(0, allEmployees.size - 1)]
        val newData = User(
            uid = updateData.uid,
            firstName = updateData.firstName + "newData",
            name = updateData.name + "newData",
            age = updateData.age + 100,
            gender = updateData.gender
        )
        repository.updateEmployee(newData)
        allEmployees = repository.getAllEmployees()
        val changedData = allEmployees.find { it.uid == updateData.uid }

        val result =
            changedData?.uid == newData.uid && changedData.age == newData.age && changedData.gender == newData.gender && changedData.name == newData.name && changedData.firstName == newData.firstName
        assertEquals(result, true)
    }
}

class FakeUserDao : UserDao {

    private val userList = mutableListOf<User>()

    override fun installUsers(vararg users: User) {
        userList.addAll(users)
    }

    override fun deleteUser(user: User) {
        userList.removeIf {
            it.uid == user.uid
        }
    }

    override fun updateUser(user: User) {
        val index = userList.indexOfFirst {
            it.uid == user.uid
        }

        if (index > -1) {
            userList[index] = user
        }
    }

    override fun getAllUserList(): List<User> {
        return userList
    }

    override fun getAverageAge(): Double {
        val size = userList.size
        if (size == 0) {
            return 0.0
        }
        val sumAge = userList.sumOf { it.age }
        return sumAge.toDouble() / size.toDouble()
    }

    override fun getTotalGenderForType(gender: Int): Double {
        return userList.filter { it.gender == gender }.size.toDouble()
    }

    override fun getTotalGender(): Double {
        return userList.size.toDouble()
    }

}