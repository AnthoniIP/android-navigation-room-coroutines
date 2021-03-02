package com.ipsoft.android_navigation_room_coroutines.data.repository

import com.ipsoft.android_navigation_room_coroutines.data.db.dao.UserDao
import com.ipsoft.android_navigation_room_coroutines.data.db.toUser
import com.ipsoft.android_navigation_room_coroutines.data.db.toUserEntity
import com.ipsoft.android_navigation_room_coroutines.data.model.User
import com.ipsoft.android_navigation_room_coroutines.ui.registration.RegistrationViewParams

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       01/03/2021
 */

class UserDbDataSource(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun createUser(registrationViewParams: RegistrationViewParams) {
        val userEntity = registrationViewParams.toUserEntity()
        userDao.save(userEntity)
    }

    override suspend fun getUser(id: Long): User {
        return userDao.getUser(id).toUser()
    }

    override suspend fun login(username: String, password: String): Long {
        return userDao.login(username,password)
    }
}