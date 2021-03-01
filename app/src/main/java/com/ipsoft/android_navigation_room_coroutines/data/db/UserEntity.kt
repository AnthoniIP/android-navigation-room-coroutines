package com.ipsoft.android_navigation_room_coroutines.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       01/03/2021
 */

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val bio: String,
    val userName: String,
    val password: String
)