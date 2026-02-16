package com.example.hiltexample1

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as MyApplication).container
        val factory = UserViewModelFactory(appContainer.userRepository)

        setContent {
            val viewModel: UserViewModel = viewModel(factory = factory)
            Text(text = viewModel.userName)
        }
    }
}

class MyApplication : Application() {
    val container = AppContainer()
}

class AppContainer {
    val userRepository = UserRepository()
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class UserRepository {
    fun getUser() = "William"
}

class UserViewModel(repository: UserRepository) : ViewModel() {
    val userName = repository.getUser()
}