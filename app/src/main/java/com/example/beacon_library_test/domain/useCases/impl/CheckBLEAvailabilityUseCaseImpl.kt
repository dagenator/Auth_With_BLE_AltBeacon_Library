package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.repository.BLERepository
import com.example.beacon_library_test.domain.useCases.interfaces.CheckBLEAvailabilityUseCase
import javax.inject.Inject

class CheckBLEAvailabilityUseCaseImpl @Inject constructor(private val bleRepository: BLERepository) :
    CheckBLEAvailabilityUseCase {
    override operator fun invoke(): Boolean = bleRepository.checkBeaconManagerAvailable()
}