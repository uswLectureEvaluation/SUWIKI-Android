package com.kunize.uswtimetable.ui.mypage.puchase_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemRecyclerPurchaseBinding
import com.kunize.uswtimetable.dataclass.PurchaseHistory

class PurchaseHistoryAdapter : ListAdapter<PurchaseHistory, PurchaseHistoryAdapter.ViewHolder>(
    diffUtil
) {
    inner class ViewHolder(private val binding: ItemRecyclerPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: PurchaseHistory) {
            binding.data = history
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerPurchaseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PurchaseHistory>() {
            override fun areItemsTheSame(
                oldItem: PurchaseHistory,
                newItem: PurchaseHistory
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PurchaseHistory,
                newItem: PurchaseHistory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}