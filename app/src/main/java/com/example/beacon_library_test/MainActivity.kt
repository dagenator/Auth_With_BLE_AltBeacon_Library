package com.example.beacon_library_test

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beacon_library_test.core.app.App
import com.example.beacon_library_test.data.models.IntercomRelays
import com.example.beacon_library_test.data.models.IntercomViewInfo
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.data.models.Status
import com.example.beacon_library_test.databinding.ActivityMainBinding
import com.example.beacon_library_test.presenter.adapters.IntercomInfoAdapter
import com.example.beacon_library_test.presenter.viewModel.ApiViewModel
import com.example.beacon_library_test.presenter.viewModel.BleViewModel
import com.example.beacon_library_test.utils.PermissionsUtils
import javax.inject.Inject


fun Activity.showAlert(
    title: String? = null,
    message: String? = null,
    positiveAction: Pair<String?, () -> Unit?> = Pair("OK", {}),
    negativeAction: Pair<String?, () -> Unit?>? = null,
    neutralAction: Pair<String, () -> Unit>? = null,
) {
    AlertDialog.Builder(this).apply {
        positiveAction.run { setPositiveButton(first) { _, _ -> second() } }
        negativeAction?.run { setNegativeButton(first) { _, _ -> second() } }
        neutralAction?.run { setNeutralButton(first) { _, _ -> second() } }
        setTitle(title)
        setMessage(message)
        show()
    }
}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionsUtils: PermissionsUtils

    @Inject
    lateinit var bleViewModel: BleViewModel

    @Inject
    lateinit var apiViewModel: ApiViewModel

    private lateinit var binding: ActivityMainBinding

    private val component by lazy { (application as App).appComponent }

    private val adapter = IntercomInfoAdapter()

    private val intercomsApiObserver = Observer<Resource<List<IntercomRelays>>> {
        if (it == null) return@Observer
        when (it.status) {
            Status.LOADING -> {
                setRecyclerDownload(true)
            }
            Status.SUCCESS -> {
                val intercoms = it.data
                if (intercoms == null || intercoms.isEmpty()) {
                    Toast.makeText(
                        this, "Error intercoms list is empty. No scanning", Toast.LENGTH_LONG
                    ).show()
                } else {
                    val main = intercoms.filter { x -> x.main == 1 }.first()
                    setRecyclerUI(main)
                    bleViewModel.startMonitor(this, main.relayId)
                }
            }
            Status.ERROR -> {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val checkBLEObserver = Observer<Resource<Boolean>?> {
        if (it == null) return@Observer
        when (it.status) {
            Status.SUCCESS -> {
                if (it.data == false) {
                    showAlert(
                        getString(R.string.bluetooth_off), getString(R.string.turn_on_bluetooth)
                    )
                }
            }
            Status.ERROR -> {
                showAlert(
                    getString(R.string.ble_not_support_title),
                    getString(R.string.ble_not_support_message)
                )
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        binding.intercomInfoList.adapter = adapter
        binding.intercomInfoList.layoutManager = LinearLayoutManager(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_FINE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) permissionsUtils.requestBackgroundLocation(
                this
            ) { startMonitoring() }
            PERMISSION_REQUEST_BACKGROUND_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) permissionsUtils.requestBlePermission(
                this
            ) { startMonitoring() }
            PERMISSION_REQUEST_BLUETOOTH_CONNECT -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) permissionsUtils.requestScanPermission(
                this
            ) { startMonitoring() }
            PERMISSION_REQUEST_BLUETOOTH_SCAN -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMonitoring()
            }
        }
    }

    private fun setRecyclerDownload(isDownload: Boolean) {
        binding.intercomInfoLoader.isVisible = isDownload
        binding.intercomInfoList.isVisible = isDownload.not()
    }

    private fun setRecyclerUI(relay: IntercomRelays) {
        val list = mutableListOf<IntercomViewInfo>(
            IntercomViewInfo(getString(R.string.address), relay.address),
            IntercomViewInfo(getString(R.string.relay_num), relay.entranceNum.toString()),
            IntercomViewInfo(getString(R.string.mac), relay.mac.toString())
        )
        relay.relayDesc?.let {
            list.add(IntercomViewInfo(getString(R.string.description), it))
        }

        adapter.submitList(list)
        setRecyclerDownload(false)
    }

    private fun setUI() {
        apiViewModel.intercomLiveData.observe(this, intercomsApiObserver)
        bleViewModel.checkBLELiveData.observe(this, checkBLEObserver)
        bleViewModel.checkBLE()

        binding.start.setOnClickListener {
            permissionsUtils.checkOrAskPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_FINE_LOCATION
            ) {
                permissionsUtils.requestBackgroundLocation(
                    this
                ) { startMonitoring() }
            }
        }
        binding.end.setOnClickListener { bleViewModel.stopMonitor() }
    }

    private fun startMonitoring() {
        apiViewModel.getIntercoms()
    }

    companion object {
        const val PERMISSION_REQUEST_FINE_LOCATION = 0
        const val PERMISSION_REQUEST_BACKGROUND_LOCATION = 1
        const val PERMISSION_REQUEST_BLUETOOTH_SCAN = 2
        const val PERMISSION_REQUEST_BLUETOOTH_CONNECT = 3
    }
}

