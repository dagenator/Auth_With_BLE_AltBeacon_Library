package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.repository.SomeApiRepository
import com.example.beacon_library_test.domain.useCases.interfaces.OpenIntercomUseCase
import javax.inject.Inject

class OpenIntercomUseCaseImpl @Inject constructor(val someApiRepository: SomeApiRepository) :
    OpenIntercomUseCase {
    override fun invoke(id: String) = someApiRepository.openIntercom(id)
}