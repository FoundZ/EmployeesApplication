package com.takumi.employees.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.takumi.employee.data.DefaultEmployeesRepository
import com.takumi.employee.data.fake.fakeRandomDataList
import com.takumi.employee.data.fake.forkDataList
import com.takumi.employee.data.local.EmployeeLocalRepository
import com.takumi.employee.data.local.database.AppDatabase
import com.takumi.employee.data.local.database.bean.User
import com.takumi.employee.data.local.di.DATABASE_NAME
import com.takumi.employee.data.remote.EmployeeRemoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EmployeesRepositoryWithDatabaseUnitTest {

    private lateinit var localRepository: EmployeeLocalRepository
    private lateinit var remoteRepository: EmployeeRemoteRepository

    private lateinit var repository: DefaultEmployeesRepository


    @Before
    fun createRepository() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME).build()
        localRepository = EmployeeLocalRepository(database.userDao())

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
        val fakeDataList = mutableListOf<User>()
        // search
        var allEmployees = repository.getAllEmployees()
        fakeDataList.addAll(allEmployees)
        assertEquals(fakeDataList.isNotEmpty(), true)
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