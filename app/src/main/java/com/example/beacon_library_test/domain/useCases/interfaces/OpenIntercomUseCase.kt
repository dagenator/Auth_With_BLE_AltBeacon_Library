package com.example.beacon_library_test.domain.useCases.interfaces

import com.example.beacon_library_test.data.models.IntercomOpeningResult
import com.example.beacon_library_test.data.models.Resource
import kotlinx.coroutines.flow.Flow

interface OpenIntercomUseCase {
    operator fun invoke(id: String): Flow<Resource<IntercomOpeningResult>>
}