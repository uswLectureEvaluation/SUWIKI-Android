package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemRecyclerPurchaseBinding
import com.kunize.uswtimetable.dataclass.PurchaseHistoryDto

class PurchaseHistoryAdapter: ListAdapter<PurchaseHistoryDto, PurchaseHistoryAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemRecyclerPurchaseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history: PurchaseHistoryDto) {
            binding.tvItemTitle.text = history.lectureName
            binding.tvItemMajor.text = history.majorType
            binding.tvItemProfessor.text = history.professor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecyclerPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<PurchaseHistoryDto>() {
            override fun areItemsTheSame(oldItem: PurchaseHistoryDto, newItem: PurchaseHistoryDto): Boolean {
                return oldItem == newItem // TODO 아이디를 내려주면 아이디 비교로 변경
            }

            override fun areContentsTheSame(oldItem: PurchaseHistoryDto, newItem: PurchaseHistoryDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}