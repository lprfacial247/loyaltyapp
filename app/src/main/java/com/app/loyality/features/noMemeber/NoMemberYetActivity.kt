package com.app.loyality.features.noMemeber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityNoMemberYetBinding
import com.app.loyality.features.manualInserting.ManualInsertingActivity
import com.wada811.viewbinding.viewBinding

class NoMemberYetActivity : AppCompatActivity(R.layout.activity_no_member_yet) {
    private val binding by viewBinding(ActivityNoMemberYetBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()

    }

    private fun initClicks() {
        binding.includeDialog.btnAddNew.setOnClickListener {
            changeActivity(ManualInsertingActivity::class.java)
        }

        binding.includeDialog.btnBack.setOnClickListener {
            finish()
        }
    }
}