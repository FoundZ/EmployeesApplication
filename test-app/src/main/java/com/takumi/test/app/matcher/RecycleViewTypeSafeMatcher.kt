package com.takumi.test.app.matcher

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.takumi.employee.biz.utils.isSameToUser
import com.takumi.employee.data.local.database.bean.User
import com.takumi.employee.ui.recycleview.EmployeeAdapter
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RecycleViewTypeSafeMatcher(private val targetList: List<User>,private val skipUID: Boolean = false) :
    TypeSafeMatcher<View>() {


    override fun matchesSafely(item: View?): Boolean {
        val recyclerView = item as? RecyclerView ?: return false
        val adapter = recyclerView.adapter as? EmployeeAdapter ?: return false
        val showList = adapter.currentList
        targetList.forEach { user ->
            val showInfo = showList.find { it.isSameToUser(user, skipUID) }

            if (showInfo == null) {
                return false
            }
        }
        return true
    }

    override fun describeTo(description: Description?) {
        description?.appendText("has items not in RecyclerView: $targetList");
    }

}