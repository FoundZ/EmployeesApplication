package com.takumi.employee.biz.bean

import com.takumi.employee.biz.R
import com.takumi.employee.ui.bean.EmployeeInfo

data class EmployeeNotifyAction(
    val dataList: MutableList<EmployeeInfo>, val action: NotifyAction
)

sealed class NotifyAction(val messageStringResID: Int) {
    data object Install : NotifyAction(messageStringResID = R.string.notify_action_install)
    data object Delete : NotifyAction(messageStringResID = R.string.notify_action_delete)
    data object Update : NotifyAction(messageStringResID = R.string.notify_action_update)
    data object Show : NotifyAction(messageStringResID = R.string.notify_action_show)
}

data class EmployeeStatisticsData(
    val averageAge: String, val femalePercentage: String, val malePercentage: String
)