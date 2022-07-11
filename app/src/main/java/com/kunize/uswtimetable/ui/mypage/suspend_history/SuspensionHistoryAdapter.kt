package com.kunize.uswtimetable.ui.mypage.suspend_history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.Suspension
import com.kunize.uswtimetable.databinding.ItemSuspensionHistoryBinding
import com.kunize.uswtimetable.ui.common.dateTimeFormat
import java.time.LocalDateTime

class SuspensionHistoryAdapter :
    ListAdapter<Suspension, SuspensionHistoryAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemSuspensionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Suspension) {
            val context = binding.root.context
            when (data) {
                is Suspension.Ban -> {
                    binding.tvReason.text = data.reason
                    binding.tvSuspensionStartDate.text = parseCreatedAt(context, data.createdAt)
                    binding.tvSuspensionEndDate.text =
                        parseExpiredAt(context, data.createdAt, data.expiredAt)
                    binding.tvRestrictions.text = data.judgement
                }
                is Suspension.Block -> {
                    binding.tvReason.text = data.reason
                    binding.tvSuspensionStartDate.text = parseCreatedAt(context, data.createdAt)
                    binding.tvSuspensionEndDate.text =
                        parseExpiredAt(context, data.createdAt, data.expiredAt)
                    binding.tvRestrictions.text = data.judgement
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSuspensionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun parseCreatedAt(context: Context, createdAt: LocalDateTime) =
        context.resources.getString(R.string.suspension_created_at, dateTimeFormat(createdAt))

    private fun parseExpiredAt(
        context: Context,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime
    ) =
        context.resources.getString(
            R.string.suspension_expired_at,
            dateTimeFormat(expiredAt),
            expiredAt.compareTo(createdAt)
        )

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Suspension>() {
            override fun areItemsTheSame(
                oldItem: Suspension,
                newItem: Suspension
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Suspension,
                newItem: Suspension
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
