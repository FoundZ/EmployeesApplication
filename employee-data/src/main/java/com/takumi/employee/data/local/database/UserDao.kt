package com.takumi.employee.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.takumi.employee.data.local.database.bean.User

@Dao
interface UserDao {

    @Insert
    fun installUsers(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUserList(): List<User>

    @Query("SELECT AVG(age) FROM user")
    fun getAverageAge(): Double

    @Query("SELECT COUNT(gender) FROM user WHERE gender = :gender")
    fun getTotalGenderForType(gender: Int): Double

    @Query("SELECT COUNT(gender) FROM user")
    fun getTotalGender(): Double

}