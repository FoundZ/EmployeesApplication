package com.takumi.test.app

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.takumi.employee.data.EmployeesRepository
import com.takumi.employee.data.fake.fakeRandomData
import com.takumi.employees.MainActivity
import com.takumi.employees.R
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
class TestAddEmployee {
    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: EmployeesRepository

    @Before
    fun init() {
        rule.inject()
    }

    @Test
    fun testAddUser() = runTest {
        // start up main screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
        // ready to add user
        val user = fakeRandomData()
        // click menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        // click install user btn
        onView(withText(R.string.action_install)).perform(click())
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

        onView(withId(com.takumi.employee.biz.R.id.list)).check(
            ViewAssertions.matches(
                RecycleViewTypeSafeMatcher(arrayListOf(user), true)
            )
        )

        activityScenario.close()

    }

    @Test
    fun testAddRandomUser() = runTest {
        // start up main screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
        // ready to add user
        val user = fakeRandomData()
        // click menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        // click install user btn
        onView(withText(R.string.action_install)).perform(click())
        // click random new user info
        onView(withId(com.takumi.employee.ui.R.id.random)).perform(click())

        activityScenario.close()
    }


}