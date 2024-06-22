package com.takumi.test.app

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.takumi.employee.biz.R
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.ui.recycleview.EmployeeViewHolder
import com.takumi.employees.MainActivity
import com.takumi.test.app.action.actionOnItemAtPosition
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
 * Integration test for the add Employees screen.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class TestDeleteEmployee {
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
        val deletePosition = 0
        // ready to load data list
        val readyLoadDataList = repository.getAllEmployees().toMutableList()

        onView(ViewMatchers.withId(R.id.list)).perform(
            actionOnItemAtPosition<EmployeeViewHolder>(
                position = deletePosition,
                clickId = com.takumi.employee.ui.R.id.deleteBtn,
                viewAction = click()
            )
        )

        // delete for target Data List
        readyLoadDataList.removeAt(deletePosition)
        //check list isDisplayed
        onView(ViewMatchers.withId(R.id.list)).check(
            ViewAssertions.matches(
                RecycleViewTypeSafeMatcher(readyLoadDataList)
            )
        )

        activityScenario.close()
    }
}