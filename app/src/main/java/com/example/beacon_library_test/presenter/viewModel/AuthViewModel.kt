package com.example.beacon_library_test.presenter.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beacon_library_test.data.models.ApiAccess
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.domain.useCases.interfaces.AuthUseCase
import com.example.beacon_library_test.domain.useCases.interfaces.IsTokenExpiredUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val isTokenExpiredUseCase: IsTokenExpiredUseCase,
) : ViewModel() {

    val authResultLiveData = MutableLiveData<Resource<ApiAccess>>(null)
    val isTokenExpiredLiveData = MutableLiveData<Boolean>(null)

    fun authorize(login: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authUseCase(login, password).collect {
                    authResultLiveData.postValue(it)
                }
            }
        }
    }

    fun isTokenExpired() {
        viewModelScope.launch {
            isTokenExpiredLiveData.value = isTokenExpiredUseCase.invoke()
        }
    }
}