package com.takumi.test.app

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.fake.fakeRandomData
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
class TestUpdateEmployee {
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
        val updatePosition = 0
        // ready to add user
        val user = fakeRandomData()
        onView(withId(com.takumi.employee.biz.R.id.list)).perform(
            actionOnItemAtPosition<EmployeeViewHolder>(
                position = updatePosition,
                clickId = com.takumi.employee.ui.R.id.updateBtn,
                viewAction = click()
            )
        )
        // input user info
        onView(withId(com.takumi.employee.ui.R.id.nameEditText)).perform(replaceText(user.name))
        onView(withId(com.takumi.employee.ui.R.id.firstNameEditText)).perform(replaceText(user.firstName))
        onView(withId(com.takumi.employee.ui.R.id.ageEditText)).perform(replaceText(user.age.toString()))
        if (user.gender == 0) {
            onView(withId(com.takumi.employee.ui.R.id.maleRadioBtn)).perform(click())
        } else {
            onView(withId(com.takumi.employee.ui.R.id.femaleRadioBtn)).perform((click()))
        }

        // commit
        onView(withText(com.takumi.employee.ui.R.string.dialog_btn_ok)).perform(click())

        // check changed user info on the RecycleView
        onView(withId(com.takumi.employee.biz.R.id.list)).check(
            matches(
                RecycleViewTypeSafeMatcher(arrayListOf(user), true)
            )
        )

        activityScenario.close()

    }
}