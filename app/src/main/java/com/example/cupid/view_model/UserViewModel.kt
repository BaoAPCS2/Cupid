package com.example.cupid.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cupid.data.repository.UserRepository
import com.example.cupid.data.model.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repo: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<UserDto>>(emptyList())
    val users: StateFlow<List<UserDto>> = _users

    fun load() = viewModelScope.launch {
        try { _users.value = repo.getUsers() } catch (_: Exception) { }
    }
}