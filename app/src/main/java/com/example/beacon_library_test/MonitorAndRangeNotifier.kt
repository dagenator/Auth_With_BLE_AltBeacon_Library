package com.example.beacon_library_test

import android.util.Log
import com.example.beacon_library_test.domain.useCases.interfaces.OpenIntercomUseCase
import com.example.beacon_library_test.utils.NotificationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.altbeacon.beacon.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonitorAndRangeNotifier @Inject constructor(
    private val beaconManager: BeaconManager,
    private val notification: NotificationController,
    private val openIntercomUseCase: OpenIntercomUseCase,
) : MonitorNotifier, RangeNotifier {

    // hardcode because it is MVP and backend did not have this info
    private val region = Region("wildCardRegion", null, null, null)
    private var isOpened: Boolean = false
    private val borderOfOpen = 4
    private val borderOfClose = 6
    private val filteredMac = "**********"

    private var intercom: String? = null

    override fun didEnterRegion(region: Region?) {
        beaconManager.addRangeNotifier(this)
        beaconManager.startRangingBeacons(this.region)

        sendMessageToUser("start ranging monitor")
    }

    override fun didExitRegion(region: Region?) {
        sendMessageToUser("didExitRegion called")
        isOpened = false
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
        sendMessageToUser("didDetermineStateForRegion called with state: " + if (state == 1) "INSIDE ($state)" else "OUTSIDE ($state)")
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        beacons?.forEach {
            if (it.bluetoothAddress != filteredMac) return@forEach

            if (it.distance < borderOfOpen && !isOpened) {
                sendMessageToUser(" < $borderOfOpen do api request")
                sendMessageToUser("Door is opened", true)
                openIntercom()
                isOpened = true
            } else if (it.distance > borderOfClose) {
                sendMessageToUser("user on distance ${it.distance} -> reset opened door ")
                isOpened = false
            } else sendMessageToUser("user on distance ${it.distance} do nothing")
        }
    }

    fun startForegroundService(intercom: String?) {
        stopMonitoring()
        this.intercom = intercom

        // hardcode because it is MVP and backend did not have this info
        val parser = BeaconParser().setBeaconLayout("*********")
        beaconManager.enableForegroundServiceScanning(
            notification.createNotificationForService().build(), 456
        )
        beaconManager.setEnableScheduledScanJobs(false)
        beaconManager.backgroundBetweenScanPeriod = 0
        beaconManager.backgroundScanPeriod = 10000

        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(parser)
        beaconManager.addMonitorNotifier(this)
        beaconManager.addRangeNotifier(this)
        beaconManager.startMonitoring(region)
    }

    fun stopMonitoring() {
        with(this.beaconManager) {
            stopMonitoring(region)
            stopRangingBeacons(region)
            removeAllRangeNotifiers()
            removeAllMonitorNotifiers()
            disableForegroundServiceScanning()
        }
    }

    private fun sendMessageToUser(message: String, isImportant: Boolean = false) {
        Log.i("MessageToUser", "sendMessageToUser: $message")
        notification.notify(message, isImportant)
    }

    private fun openIntercom() {
        CoroutineScope(Dispatchers.IO).launch {
            intercom?.let {
                val result = openIntercomUseCase(it)
                result.collect {
                    Log.i(
                        "TEST_TAG",
                        "doOnIntercomDetect: ${it.status}, ${it.data}, ${it.message}, ${it.data?.statusCode} "
                    )
                }
            }
        }
    }

}