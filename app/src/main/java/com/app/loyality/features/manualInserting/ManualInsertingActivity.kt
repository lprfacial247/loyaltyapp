package com.app.loyality.features.manualInserting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityManualInsertingBinding
import com.wada811.viewbinding.viewBinding

class ManualInsertingActivity : AppCompatActivity(R.layout.activity_manual_inserting) {
    private val binding by viewBinding(ActivityManualInsertingBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()
    }

    private fun initClicks() {
        binding.btnOk.setOnClickListener {
            changeActivity(ManualInsertSuccessActivity::class.java)
            finish()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}