package com.example.beacon_library_test.core.di

import com.example.beacon_library_test.domain.useCases.impl.*
import com.example.beacon_library_test.domain.useCases.interfaces.*
import dagger.Binds
import dagger.Module

@Module
interface BindingModule {

    @Binds
    fun startMonitoringUseCaseBinding(impl: StartBLEMonitorUseCaseImpl): StartBLEMonitorUseCase

    @Binds
    fun stopMonitoringUseCaseBinding(impl: StopBLEMonitorUseCaseImpl): StopBLEMonitorUseCase

    @Binds
    fun checkBLEAvailabilityUseCaseBinding(impl: CheckBLEAvailabilityUseCaseImpl): CheckBLEAvailabilityUseCase

    @Binds
    fun authUseCaseBinding(impl: AuthUseCaseImpl): AuthUseCase

    @Binds
    fun isTokenExpiredUseCaseBinding(impl: IsTokenExpiredUseCaseImpl): IsTokenExpiredUseCase

    @Binds
    fun getIntercomRelaysUseCaseBinding(impl: GetIntercomRelaysUseCaseImpl): GetIntercomRelaysUseCase

    @Binds
    fun openIntercomUseCaseBinding(impl: OpenIntercomUseCaseImpl): OpenIntercomUseCase


}