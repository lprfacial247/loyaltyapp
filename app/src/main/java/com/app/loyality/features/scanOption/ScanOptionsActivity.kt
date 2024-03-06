package com.app.loyality.features.scanOption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityLoginBinding
import com.app.loyality.databinding.ActivityScanOptionsBinding
import com.app.loyality.features.manualInserting.ManualInsertingActivity
import com.app.loyality.features.noMemeber.NoMemberYetActivity
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
            changeActivity(NoMemberYetActivity::class.java)
        }

        binding.scanBarCode.setOnClickListener {
            changeActivity(NoMemberYetActivity::class.java)
        }

          binding.manualInserting.setOnClickListener {
            changeActivity(ManualInsertingActivity::class.java)
        }


    }
}