package com.app.loyality.features.login

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.toast
import com.app.loyality.common.utils.DeviceUtils
import com.app.loyality.databinding.ActivityLoginBinding
import com.app.loyality.features.scanOption.ScanOptionsActivity
import com.wada811.viewbinding.viewBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val binding by viewBinding(ActivityLoginBinding::bind)
    private val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize ActivityResultLauncher for requesting permissions
        requestPermissionLauncher = registerForActivityResult<String, Boolean>(
            RequestPermission()
        ) { isGranted: Boolean? ->  }

        if (!isNotificationPermissionGranted()) {
            askNotificationPermission()
        }

        binding.btnGetStart.setOnClickListener {
            val deviceToken = DeviceUtils.getDeviceIMEI(this@LoginActivity)
            val pinCode = binding.passwordInput.text.toString()
            if (pinCode.length != 5) {
                "Pin code should be 5 digits".toast()
                return@setOnClickListener
            }
            viewModel.login(deviceToken, pinCode,
                onSuccess = {
                    "Login Successful".toast()
                    navigateToNextActivity()
                },
                onFailed = {
                    it.toast()
                })



        }
    }

    private fun navigateToNextActivity() {
        changeActivity(ScanOptionsActivity::class.java)
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) {
            // For Android 13 (or later), check the notification permission
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
        } else {
            // For versions older than Android 13, assume permission is granted
            true
        }
    }
}