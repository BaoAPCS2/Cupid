package com.example.cupid.data.repository

import com.example.cupid.data.model.UserDto
import com.example.cupid.data.remote.UserApi

class UserRepository(private val api: UserApi) {
    suspend fun getUsers(): List<UserDto> = api.getUsers()
}