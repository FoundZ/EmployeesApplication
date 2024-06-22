package com.takumi.employee.data.fake

import com.takumi.employee.data.local.database.bean.User
import kotlin.random.Random

val firstNames = listOf("John", "Jane", "Emily", "Michael", "David", "Sophia", "James")
val lastNames = listOf("Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia")

fun fakeRandomData(): User {
    val id = Random.nextInt()
    val firstNames = firstNames.random()
    val lastNames = lastNames.random()
    val age = Random.nextInt(22, 50)
    val gender = Random.nextInt(0, 1)

    return User(
        uid = id, name = lastNames, firstName = firstNames, age = age, gender = gender
    )
}


fun fakeRandomDataList(): List<User> {
    return listOf(fakeRandomData(), fakeRandomData(), fakeRandomData(), fakeRandomData())
}


val forkDataList: List<User> = arrayListOf(
    User(
        uid = 1,
        firstName = "Johnson",
        name = "Emily",
        age = 30,
        gender = 0,
    ), User(
        uid = 2,
        firstName = "Brown",
        name = "Olivia",
        age = 35,
        gender = 0,
    ), User(
        uid = 3,
        firstName = "Davis",
        name = "Liam",
        age = 34,
        gender = 0,
    ), User(
        uid = 4,
        firstName = "Wilson",
        name = "Sophia",
        age = 22,
        gender = 1,
    ), User(
        uid = 5,
        firstName = "Anderson",
        name = "Ethan",
        age = 30,
        gender = 0,
    ), User(
        uid = 6,
        firstName = "Johnson",
        name = "Emily",
        age = 30,
        gender = 1,
    ), User(
        uid = 7,
        firstName = "Thomas",
        name = "Ava ",
        age = 30,
        gender = 1,
    ), User(
        uid = 8,
        firstName = "Scott",
        name = "Benjamin ",
        age = 30,
        gender = 0,
    ), User(
        uid = 9,
        firstName = "Taylor",
        name = "Mia ",
        age = 30,
        gender = 0,
    ), User(
        uid = 10,
        firstName = "Lewis",
        name = "James",
        age = 30,
        gender = 1,
    )
)