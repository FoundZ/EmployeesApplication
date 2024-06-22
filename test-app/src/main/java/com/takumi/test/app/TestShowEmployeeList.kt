package com.takumi.test.app

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.local.database.bean.User
import com.takumi.employees.MainActivity
import com.takumi.test.app.matcher.RecycleViewTypeSafeMatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Integration test for the Show Employees screen.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class TestShowEmployeeList {


    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: EmployeesRepository

    @Before
    fun init() {
        rule.inject()
    }

    @Test
    fun testShowList() = runTest {
        // start up main screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        // ready to load data list
        val readyLoadDataList = repository.getAllEmployees()
        //check list isDisplayed
        onView(withId(com.takumi.employee.biz.R.id.list)).check(matches(RecycleViewTypeSafeMatcher(readyLoadDataList)))

        activityScenario.close()
    }
}