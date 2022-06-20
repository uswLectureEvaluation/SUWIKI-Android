package com.kunize.uswtimetable.ui.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemNoticeBinding
import com.kunize.uswtimetable.dataclass.NoticeDto

class NoticeAdapter : PagingDataAdapter<NoticeDto, NoticeAdapter.ViewHolder>(
    diffUtil
) {
    inner class ViewHolder(private val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: NoticeDto) {
            binding.notice = notice
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<NoticeDto>() {
            override fun areItemsTheSame(oldItem: NoticeDto, newItem: NoticeDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoticeDto, newItem: NoticeDto): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}