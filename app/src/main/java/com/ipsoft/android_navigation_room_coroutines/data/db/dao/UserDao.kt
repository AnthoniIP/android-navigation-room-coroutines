package com.ipsoft.android_navigation_room_coroutines.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ipsoft.android_navigation_room_coroutines.data.db.UserEntity

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       01/03/2021
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserEntity)

    @Query("SELECT * FROM user WHERE id =:id")
    fun getUser(id: Long): UserEntity

    @Query("SELECT id FROM user WHERE userName = :userName and password = :password")
    fun login(userName: String, password: String): Long
}