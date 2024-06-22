package com.takumi.employee.ui.bean

data class EmployeeInfo(
    var id: Int? = null, val name: String, val firstName: String, val age: Int, val gender: Gender
)

sealed class Gender(val value: Int) {
    data object Male : Gender(value = 0)
    data object Female : Gender(value = 1)
}