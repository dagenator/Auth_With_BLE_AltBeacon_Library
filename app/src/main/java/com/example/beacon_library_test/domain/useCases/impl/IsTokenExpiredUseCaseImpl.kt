package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.repository.SomeApiRepository
import com.example.beacon_library_test.domain.useCases.interfaces.IsTokenExpiredUseCase
import javax.inject.Inject

class IsTokenExpiredUseCaseImpl @Inject constructor(private val authRepository: SomeApiRepository) :
    IsTokenExpiredUseCase {
    override fun invoke(): Boolean = authRepository.isTokenExpiredCheck()
}