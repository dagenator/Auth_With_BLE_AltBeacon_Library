package com.example.beacon_library_test.domain.useCases.impl

import com.example.beacon_library_test.data.repository.BLERepository
import com.example.beacon_library_test.domain.useCases.interfaces.StartBLEMonitorUseCase
import javax.inject.Inject

class StartBLEMonitorUseCaseImpl @Inject constructor(
    private val bleRepository: BLERepository
) : StartBLEMonitorUseCase {
    override fun invoke(intercom: String?) = bleRepository.startForegroundService(intercom)
}