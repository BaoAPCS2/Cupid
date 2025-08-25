package com.example.cupid.data.remote

import com.example.cupid.data.model.UserDto
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface UserApi {
    @GET("api/users/")
    suspend fun getUsers(): List<UserDto>

    companion object {
        fun create(): UserApi {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi::class.java)
        }
    }
}