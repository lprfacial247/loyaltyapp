package com.app.loyality.features.scanOption

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityLoginBinding
import com.app.loyality.databinding.ActivityScanOptionsBinding
import com.app.loyality.features.barScanner.BarcodeReaderActivity
import com.app.loyality.features.manualInserting.ManualInsertingActivity
import com.app.loyality.features.noMemeber.NoMemberYetActivity
import com.app.loyality.features.qrScanner.QrScannerActivity
import com.app.loyality.features.rfidScanApproved.RFIDScanApprovedActivity
import com.wada811.viewbinding.viewBinding

class ScanOptionsActivity : AppCompatActivity(R.layout.activity_scan_options) {
    private val binding by viewBinding(ActivityScanOptionsBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()
    }

    private fun initClicks() {
        binding.scanRfid.setOnClickListener {
            changeActivity(RFIDScanApprovedActivity::class.java)
        }

        binding.scanQrCode.setOnClickListener {
            if (checkCameraPermission()) {
                changeActivity(QrScannerActivity::class.java)
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

        }

        binding.scanBarCode.setOnClickListener {
            if (checkCameraPermission()) {
                changeActivity(BarcodeReaderActivity::class.java)
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
//            changeActivity(NoMemberYetActivity::class.java)
        }

          binding.manualInserting.setOnClickListener {
            changeActivity(ManualInsertingActivity::class.java)
        }


    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                changeActivity(QrScannerActivity::class.java)
            } else {
                // Permission denied, handle accordingly
            }
        }
}