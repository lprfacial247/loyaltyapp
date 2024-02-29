package com.app.loyality.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivityLoginBinding
import com.app.loyality.features.main.MainActivity
import com.app.loyality.features.scanOption.ScanOptionsActivity
import com.wada811.viewbinding.viewBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by viewBinding(ActivityLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnGetStart.setOnClickListener {
            changeActivity(ScanOptionsActivity::class.java)
        }
    }
}