package com.example.beacon_library_test.domain.useCases.interfaces

interface StartBLEMonitorUseCase {
    operator fun invoke(intercom: String?)
}