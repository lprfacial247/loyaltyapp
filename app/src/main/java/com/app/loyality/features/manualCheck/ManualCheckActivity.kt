package com.app.loyality.features.manualCheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.toast
import com.app.loyality.common.network.api.RetrofitClient
import com.app.loyality.common.pref.SpManager
import com.app.loyality.databinding.ActivityManualInsertingBinding
import com.app.loyality.features.barScanner.UserInfoResponse
import com.app.loyality.features.main.MainActivity
import com.app.loyality.features.manualInsert.ManualInsertSuccessActivity
import com.app.loyality.features.noMemeber.NoMemberYetActivity
import com.wada811.viewbinding.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualCheckActivity : AppCompatActivity(R.layout.activity_manual_inserting) {
    private val binding by viewBinding(ActivityManualInsertingBinding::bind)
    private val user by lazy { SpManager.getUser(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClicks()
    }

    private fun initClicks() {
        binding.btnOk.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()

            if (name.isEmpty()) {
                "Name can't be empty!!".toast()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                "Email can't be empty!!".toast()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                "Phone can't be empty!!".toast()
                return@setOnClickListener
            }

            checkCustomerInfo(user.userIdx, name, email, phone,
                onSuccess = {
                    changeActivity(MainActivity::class.java)
                    finish()
                },
                onFailed = {
                    changeActivity(NoMemberYetActivity::class.java)
                    finish()
                })
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun checkCustomerInfo(
        userId: Int,
        name: String,
        email: String,
        phone: String,
        onSuccess: (UserInfoResponse) -> Unit,
        onFailed: (String) -> Unit
    ) {
        RetrofitClient.getApiClient()
            .checkCustomerInfo(userId, 3, "", "", name, email, phone)
            .enqueue(object : Callback<UserInfoResponse?> {
                override fun onResponse(
                    call: Call<UserInfoResponse?>,
                    response: Response<UserInfoResponse?>
                ) {
                    if (response.body() != null) {
                        if (response.body()?.isLoyalty == true) {
                            onSuccess.invoke(response.body()!!)
                        } else {
                            onFailed.invoke("Not Found!!")
                        }
                    } else {
                        onFailed.invoke("No response found from server")
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse?>, t: Throwable) {
                    onFailed.invoke(t.localizedMessage)
                }
            })
    }
}