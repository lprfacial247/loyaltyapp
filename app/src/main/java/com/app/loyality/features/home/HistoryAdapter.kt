package com.app.loyality.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.loyality.databinding.LayoutHistoryBinding
import com.app.loyality.features.barScanner.UserInfoResponse

class HistoryAdapter() : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    // ViewHolder class

    private val items: MutableList<UserInfoResponse.History> = ArrayList()
    class MyViewHolder(val binding: LayoutHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvStoreName.text = item.storeName
        holder.binding.tvVisitDate.text = item.visitDate
        holder.binding.tvBoughtProducts.text = item.boughtProds.toString()
        holder.binding.tvTotalPaidAmount.text = "$ ${item.totalPaid}"
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Update data and refresh RecyclerView
    fun updateData(newItems: MutableList<UserInfoResponse.History>) {
        val diffCallback = MyDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil Callback class
    class MyDiffCallback(private val oldList: List<UserInfoResponse.History>, private val newList: List<UserInfoResponse.History>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
