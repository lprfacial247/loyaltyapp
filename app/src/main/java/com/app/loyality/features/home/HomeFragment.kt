package com.app.loyality.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.loyality.common.dialog.ProgressDialog
import com.app.loyality.R
import com.app.loyality.common.extensions.fromJSONToList
import com.app.loyality.common.extensions.fromJson
import com.app.loyality.common.extensions.hide
import com.app.loyality.common.extensions.load
import com.app.loyality.common.extensions.show
import com.app.loyality.common.extensions.toast
import com.app.loyality.common.network.model.ResponseState
import com.app.loyality.common.pref.SpManager
import com.app.loyality.databinding.FragmentHomeBinding
import com.app.loyality.features.barScanner.UserInfoResponse
import com.wada811.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val progressDialog by lazy { ProgressDialog.Builder(requireContext()).build() }
    private val adapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        val userInfoStr = SpManager.getString(requireContext(), SpManager.KEY_USER_INFO, "")
        if (!userInfoStr.isNullOrEmpty()) {
            val userInfo = userInfoStr.fromJson<UserInfoResponse>()
            adapter.updateData(userInfo.histories.toMutableList())

            binding.tvCount.text = "view all ${userInfo.histories.size} purchases"

            binding.ivProfile.load(userInfo.imagePath)
            binding.tvName.text = userInfo.name
            binding.tvCardNo.text = userInfo.cardId
            binding.tvBarCode.text = userInfo.barcode
            binding.tvTotalPoints.text = "${userInfo.totalPts} pt"
            binding.tvTotalPaidAmount.text = "$ ${userInfo.totalPaid}"
            binding.tvissueDate.text = userInfo.createdAt

            if (userInfo.isLoyalty) {
                binding.ivLoayality.show()
            }
            else binding.ivLoayality.hide()
        }
    }
}