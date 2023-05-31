package com.example.beacon_library_test.data.repository

import com.example.beacon_library_test.MonitorAndRangeNotifier
import org.altbeacon.beacon.BeaconManager
import javax.inject.Inject

class BLERepository @Inject constructor(
    private val monitorAndRangeNotifier: MonitorAndRangeNotifier,
    private val beaconManager: BeaconManager
) {

    fun startForegroundService(intercom: String?) {
        monitorAndRangeNotifier.startForegroundService(intercom)
    }

    fun stopForegroundService() {
        monitorAndRangeNotifier.stopMonitoring()
    }

    fun checkBeaconManagerAvailable(): Boolean {
        return beaconManager.checkAvailability()
    }

}