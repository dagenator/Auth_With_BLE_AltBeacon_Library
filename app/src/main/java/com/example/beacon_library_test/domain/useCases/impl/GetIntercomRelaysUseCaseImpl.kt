package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.models.IntercomRelays
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.data.repository.SomeApiRepository
import com.example.beacon_library_test.domain.useCases.interfaces.GetIntercomRelaysUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIntercomRelaysUseCaseImpl @Inject constructor(val someApiRepository: SomeApiRepository) :
    GetIntercomRelaysUseCase {
    override suspend fun invoke(): Flow<Resource<List<IntercomRelays>>> =
        someApiRepository.getIntercoms()
}