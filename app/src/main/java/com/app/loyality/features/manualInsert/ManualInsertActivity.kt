package com.app.loyality.features.manualInsert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.isEmailValid
import com.app.loyality.common.extensions.toast
import com.app.loyality.common.network.api.RetrofitClient
import com.app.loyality.databinding.ActivityManualInsertBinding
import com.wada811.viewbinding.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualInsertActivity : AppCompatActivity(R.layout.activity_manual_insert) {
    private val binding by viewBinding(ActivityManualInsertBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            if (!email.isEmailValid()) {
                "Invalid email address".toast()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                "Phone can't be empty!!".toast()
                return@setOnClickListener
            }

            manualInsert(name, email, phone,
                onSuccess = {
                    changeActivity(ManualInsertSuccessActivity::class.java)
                    finish()
                },
                onFailed = {
                    it.toast()
                }
                )
        }

    }


    private fun manualInsert(
        name: String,
        email: String,
        phone: String,
        onSuccess: (String) -> Unit,
        onFailed: (String) -> Unit
    ) {
        RetrofitClient.getApiClient()
            .submitNewCustomer(name, email, phone)
            .enqueue(object : Callback<ManualInsertResponse?> {
                override fun onResponse(
                    call: Call<ManualInsertResponse?>,
                    response: Response<ManualInsertResponse?>
                ) {
                    if (response.body() != null) {
                        if (response.body()?.status == "success") {
                            onSuccess.invoke("Success")
                        } else {
                            onFailed.invoke("Failed!!")
                        }
                    } else {
                        onFailed.invoke("No response found from server")
                    }
                }

                override fun onFailure(call: Call<ManualInsertResponse?>, t: Throwable) {
                    onFailed.invoke(t.localizedMessage)
                }
            })
    }
}