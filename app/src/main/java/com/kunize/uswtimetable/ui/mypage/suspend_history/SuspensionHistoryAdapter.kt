package com.kunize.uswtimetable.ui.mypage.suspend_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemSuspensionHistoryBinding
import com.kunize.uswtimetable.dataclass.SuspensionHistory

class SuspensionHistoryAdapter: ListAdapter<SuspensionHistory, SuspensionHistoryAdapter.ViewHolder>(
    diffUtil
) {
    inner class ViewHolder(val binding: ItemSuspensionHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SuspensionHistory) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSuspensionHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<SuspensionHistory>() {
            override fun areItemsTheSame(
                oldItem: SuspensionHistory,
                newItem: SuspensionHistory
            ): Boolean {
                return "${oldItem.createdAt}-${oldItem.expiredAt}" == "${newItem.createdAt}-${newItem.expiredAt}"
            }

            override fun areContentsTheSame(
                oldItem: SuspensionHistory,
                newItem: SuspensionHistory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}