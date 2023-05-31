package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.repository.BLERepository
import com.example.beacon_library_test.domain.useCases.interfaces.StopBLEMonitorUseCase
import javax.inject.Inject

class StopBLEMonitorUseCaseImpl @Inject constructor(private val bleRepository: BLERepository) :
    StopBLEMonitorUseCase {
    override fun invoke() = bleRepository.stopForegroundService()

}