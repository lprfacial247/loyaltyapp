package com.app.loyality.features.manualInsert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.databinding.ActivityManualInsertSuccessBinding
import com.app.loyality.features.scanOption.ScanOptionsActivity
import com.wada811.viewbinding.viewBinding

class ManualInsertSuccessActivity : AppCompatActivity(R.layout.activity_manual_insert_success) {
    private val binding by viewBinding(ActivityManualInsertSuccessBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()
    }

    private fun initClicks() {
        binding.includeDialog.btnBack.setOnClickListener {
            startActivity(Intent(this, ScanOptionsActivity::class.java))
            finish()
        }
    }
}