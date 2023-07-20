package com.kunize.uswtimetable.ui.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemNoticeBinding
import com.kunize.uswtimetable.util.extensions.onThrottleClick
import com.suwiki.domain.model.SimpleNotice

class NoticeAdapter(private val onClick: (SimpleNotice) -> Unit) : // TODO ViewModel 제거
    ListAdapter<SimpleNotice, NoticeAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: SimpleNotice) {
            binding.notice = notice
//            binding.viewmodel = viewModel
            binding.root.onThrottleClick { onClick(notice) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimpleNotice>() {
            override fun areItemsTheSame(oldItem: SimpleNotice, newItem: SimpleNotice): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SimpleNotice, newItem: SimpleNotice): Boolean {
                return oldItem == newItem
            }
        }
    }
}
