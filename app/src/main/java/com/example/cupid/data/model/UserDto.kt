package com.example.cupid.data.model

data class UserDto(
    val id: Int,
    val username: String,
    val email: String?,
    val bio: String?,
    val location_lat: Double?,
    val location_lng: Double?,
    val photos: List<PhotoDto>
)