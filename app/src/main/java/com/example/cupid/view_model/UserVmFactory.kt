package com.example.cupid.view_model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cupid.data.repository.UserRepository
import com.example.cupid.data.remote.UserApi

class UserVmFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = UserApi.create()
        val repo = UserRepository(api)
        return UserViewModel(repo) as T
    }
}