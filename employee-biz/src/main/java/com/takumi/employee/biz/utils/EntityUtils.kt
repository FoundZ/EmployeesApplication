package com.takumi.employee.biz.utils

import com.takumi.employee.data.local.database.bean.User
import com.takumi.employee.ui.bean.EmployeeInfo
import com.takumi.employee.ui.bean.Gender

fun User.toEmployeeInfo(): EmployeeInfo {
    return EmployeeInfo(
        id = this.uid,
        name = this.name,
        firstName = this.firstName,
        age = this.age,
        gender = this.gender.toGender()
    )
}

fun EmployeeInfo.toUser(): User {
    return User(
        uid = this.id ?: 0,
        name = this.name,
        firstName = this.firstName,
        age = this.age,
        gender = this.gender.value
    )
}

fun Int.toGender(): Gender {
    return when (this) {
        0 -> Gender.Male
        1 -> Gender.Female
        else -> Gender.Male
    }
}


fun EmployeeInfo.isSameToUser(user: User,skipUID:Boolean = false): Boolean {
    if (this.id != user.uid && skipUID.not()) {
        return false
    }
    if (this.name != user.name) {
        return false
    }
    if (this.firstName != user.firstName) {
        return false
    }
    if (this.age != user.age) {
        return false
    }
    if (this.gender.value != user.gender) {
        return false
    }
    return true
}