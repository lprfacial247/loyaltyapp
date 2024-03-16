package com.app.loyality.features.scanOption

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.toast
import com.app.loyality.databinding.ActivityScanOptionsBinding
import com.app.loyality.features.barScanner.BarcodeReaderActivity
import com.app.loyality.features.manualCheck.ManualCheckActivity
import com.app.loyality.features.qrScanner.QrScannerActivity
import com.app.loyality.features.rfidScanApproved.RFIDScanApprovedActivity
import com.wada811.viewbinding.viewBinding

class ScanOptionsActivity : AppCompatActivity(R.layout.activity_scan_options) {
    private val binding by viewBinding(ActivityScanOptionsBinding::bind)

    private val scannerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val scanResult: String? =
                    result.data?.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult")
                Log.d("LOGTAG", "Have scan result in your app activity :$scanResult")
                scanResult?.toast()
            }
            else {
                showScanError(result)
            }

        }

    private fun showScanError(result: ActivityResult) {
        val error: String? =
            result.data?.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image")
        if (error != null) {
            val alertDialog = AlertDialog.Builder(this@ScanOptionsActivity).create()
            alertDialog.setTitle("Scan Error")
            alertDialog.setMessage("QR Code could not be scanned")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        }
    }

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
        }

          binding.manualInserting.setOnClickListener {
            changeActivity(ManualCheckActivity::class.java)
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