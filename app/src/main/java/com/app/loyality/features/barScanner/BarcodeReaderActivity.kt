package com.app.loyality.features.barScanner

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.gone
import com.app.loyality.common.network.api.RetrofitClient
import com.app.loyality.common.pref.SpManager
import com.app.loyality.databinding.ActivityBarcodeReaderBinding
import com.app.loyality.features.login.LoginResponse
import com.app.loyality.features.main.MainActivity
import com.app.loyality.features.noMemeber.NoMemberYetActivity
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.Result
import com.wada811.viewbinding.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarcodeReaderActivity : AppCompatActivity(R.layout.activity_barcode_reader) {
    private val binding by viewBinding(ActivityBarcodeReaderBinding::bind)
    private val user by lazy { SpManager.getUser(this) }
    private var mCodeScanner: CodeScanner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCodeScanner = CodeScanner(this, binding.scannerView)
        showQrScanner()
    }

    private fun showQrScanner() {
        mCodeScanner?.decodeCallback = DecodeCallback { result: Result ->
            runOnUiThread {
                checkCustomerInfo(user.userIdx, result.text,
                    onSuccess = {
                        changeActivity(MainActivity::class.java)
                        finish()
                    },
                    onFailed = {
                        changeActivity(NoMemberYetActivity::class.java)
                        finish()
                    })
                mCodeScanner!!.releaseResources()
//                binding.rlBarScanner.gone()
            }
        }
        mCodeScanner?.startPreview()


        binding.closeTv.setOnClickListener(View.OnClickListener { v: View? ->
            finish()
            mCodeScanner?.releaseResources()
        })
    }

    override fun onResume() {
        super.onResume()
        mCodeScanner?.startPreview()
    }

    override fun onPause() {
        mCodeScanner?.releaseResources()
        super.onPause()
    }

    private fun checkCustomerInfo(
        userId: Int,
        barCode: String,
        onSuccess: (UserInfoResponse) -> Unit,
        onFailed: (String) -> Unit
    ) {
        RetrofitClient.getApiClient()
            .checkCustomerInfo(userId, 2, barCode, "", "", "", "")
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
