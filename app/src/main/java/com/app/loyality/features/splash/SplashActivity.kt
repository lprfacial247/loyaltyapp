package com.app.loyality.features.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.databinding.ActivitySplashBinding
import com.app.loyality.features.main.MainActivity
import com.wada811.viewbinding.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding by viewBinding(ActivitySplashBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(3000)
            changeActivity(MainActivity::class.java)
        }


    }
}