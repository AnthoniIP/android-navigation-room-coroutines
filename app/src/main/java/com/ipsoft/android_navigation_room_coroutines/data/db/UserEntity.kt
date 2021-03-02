package com.ipsoft.android_navigation_room_coroutines.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ipsoft.android_navigation_room_coroutines.data.model.User
import com.ipsoft.android_navigation_room_coroutines.ui.registration.RegistrationViewParams

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

fun RegistrationViewParams.toUserEntity(): UserEntity {
    return with(this) {
        UserEntity(
            name = this.name,
            bio = this.bio,
            userName = this.username,
            password = this.password
        )
    }
}

fun UserEntity.toUser(): User {
    return with(this) {
        User(
            id = this.id.toString(),
            name = this.name,
            bio = this.bio
        )
    }
}