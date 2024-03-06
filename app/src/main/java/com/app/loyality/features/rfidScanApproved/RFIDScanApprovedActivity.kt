package com.app.loyality.features.rfidScanApproved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityRfidscanApprovedBinding
import com.app.loyality.features.main.MainActivity
import com.wada811.viewbinding.viewBinding

class RFIDScanApprovedActivity : AppCompatActivity(R.layout.activity_rfidscan_approved) {
    private val binding by viewBinding(ActivityRfidscanApprovedBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()

    }

    private fun initClicks() {
        binding.btnOk.setOnClickListener {
            changeActivity(MainActivity::class.java)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}