package com.app.loyality.features.rfidScanApproved

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.loyality.R
import com.app.loyality.common.extensions.changeActivity
import com.app.loyality.common.extensions.toJson
import com.app.loyality.common.network.api.RetrofitClient
import com.app.loyality.common.pref.SpManager
import com.app.loyality.common.utils.NfcUtils
import com.app.loyality.databinding.ActivityRfidScanBinding
import com.app.loyality.features.barScanner.UserInfoResponse
import com.app.loyality.features.main.MainActivity
import com.app.loyality.features.noMemeber.NoMemberYetActivity
import com.wada811.viewbinding.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RfidScanActivity : AppCompatActivity(R.layout.activity_rfid_scan) {
    private val binding by viewBinding(ActivityRfidScanBinding::bind)
    private val REQUEST_NFC_PERMISSION = 123
    private var nfcAdapter: NfcAdapter? = null
    private val user by lazy { SpManager.getUser(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if NFC is available
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Request NFC permission for foreground dispatch (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.NFC), REQUEST_NFC_PERMISSION)
            }
        }


    }


    override fun onResume() {
        super.onResume()
        if (NfcUtils.isNfcAvailableAndEnabled(this)) {
            NfcUtils.enableNfcReader(this)
        } else {
            Toast.makeText(this, "NFC is not available or enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        NfcUtils.disableNfcReader(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (NfcUtils.isNfcIntent(intent)) {
                val tagContent: String = NfcUtils.readNfcTag(intent)
                runOnUiThread {
                    checkCustomerInfo(user.userIdx, tagContent,
                        onSuccess = {
                            SpManager.saveString(this@RfidScanActivity, SpManager.KEY_USER_INFO, it.toJson())
                            changeActivity(MainActivity::class.java)
                            finish()
                        },
                        onFailed = {
                            changeActivity(NoMemberYetActivity::class.java)
                            finish()
                        })
                    NfcUtils.disableNfcReader(this)
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NFC_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // NFC permission granted
                // You may handle it here or resume the normal flow
            } else {
                // NFC permission denied
                // Handle the denial appropriately, maybe show a message to the user
            }
        }
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