package com.example.beacon_library_test.domain.useCases.interfaces

import com.example.beacon_library_test.data.models.ApiAccess
import com.example.beacon_library_test.data.models.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    suspend operator fun invoke(username: String, password: String): Flow<Resource<ApiAccess>>
}