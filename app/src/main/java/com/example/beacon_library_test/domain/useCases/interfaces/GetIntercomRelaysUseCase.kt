package com.example.beacon_library_test.domain.useCases.interfaces

import com.example.beacon_library_test.data.models.IntercomRelays
import com.example.beacon_library_test.data.models.Resource
import kotlinx.coroutines.flow.Flow

interface GetIntercomRelaysUseCase {
    suspend operator fun invoke(): Flow<Resource<List<IntercomRelays>>>
}