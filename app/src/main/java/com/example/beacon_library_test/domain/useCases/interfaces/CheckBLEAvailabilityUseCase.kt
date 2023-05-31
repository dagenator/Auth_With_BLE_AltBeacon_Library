package com.example.beacon_library_test.domain.useCases.interfaces

interface CheckBLEAvailabilityUseCase {
    operator fun invoke(): Boolean
}