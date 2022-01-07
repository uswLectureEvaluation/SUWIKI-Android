package com.kunize.uswtimetable.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemNoticeBinding
import com.kunize.uswtimetable.dataclass.NoticeData
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeAdapter(val onItemClicked: (NoticeData) -> Unit): ListAdapter<NoticeData, NoticeAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemNoticeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: NoticeData) {
            binding.root.setOnClickListener {
                onItemClicked(notice)
            }
            binding.titleTextView.text = notice.title
            binding.dateTextView.text = notice.date.toString()
            Log.d(TAG, "ViewHolder - bind(${notice.title}) called")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<NoticeData>() {
            override fun areItemsTheSame(oldItem: NoticeData, newItem: NoticeData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoticeData, newItem: NoticeData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}