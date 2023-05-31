package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.models.ApiAccess
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.data.repository.SomeApiRepository
import com.example.beacon_library_test.domain.useCases.interfaces.AuthUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(private val authRepository: SomeApiRepository) :
    AuthUseCase {
    override suspend fun invoke(username: String, password: String): Flow<Resource<ApiAccess>> =
        authRepository.auth(username, password)

}