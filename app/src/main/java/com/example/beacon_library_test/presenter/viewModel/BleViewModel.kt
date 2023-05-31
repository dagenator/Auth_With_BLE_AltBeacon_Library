package com.example.beacon_library_test.presenter.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.domain.useCases.interfaces.CheckBLEAvailabilityUseCase
import com.example.beacon_library_test.domain.useCases.interfaces.StartBLEMonitorUseCase
import com.example.beacon_library_test.domain.useCases.interfaces.StopBLEMonitorUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleViewModel @Inject constructor(
    private val startBLEMonitorUseCase: StartBLEMonitorUseCase,
    private val stopBLEMonitorUseCase: StopBLEMonitorUseCase,
    private val checkBLEAvailability: CheckBLEAvailabilityUseCase
) : ViewModel() {

    val checkBLELiveData: MutableLiveData<Resource<Boolean>> = MutableLiveData(null)

    fun startMonitor(activity: Activity, intercom: String?) {
        if (checkBLELiveData.value == null || checkBLELiveData.value?.data == false) {
            checkBLE()
        } else {
            startBLEMonitorUseCase(intercom)
        }
    }

    fun stopMonitor() {
        stopBLEMonitorUseCase()
    }

    fun checkBLE() {
        try {
            checkBLELiveData.value = Resource.success(checkBLEAvailability())
        } catch (e: RuntimeException) {
            checkBLELiveData.value = Resource.error(null, e.message ?: "")
        }
    }
}