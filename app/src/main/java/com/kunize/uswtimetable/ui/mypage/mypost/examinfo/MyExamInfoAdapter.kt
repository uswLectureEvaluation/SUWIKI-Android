package com.kunize.uswtimetable.ui.mypage.mypost.examinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.databinding.ItemMyExamInfoBinding

class MyExamInfoAdapter(private val viewModel: MyExamInfoViewModel) :
    ListAdapter<LectureExamDto, MyExamInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemMyExamInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LectureExamDto) {
            binding.viewmodel = viewModel
            binding.data = data
            binding.contentTextView = binding.tvContent
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyExamInfoBinding.inflate(
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
        private val diffUtil = object : DiffUtil.ItemCallback<LectureExamDto>() {
            override fun areItemsTheSame(oldItem: LectureExamDto, newItem: LectureExamDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LectureExamDto,
                newItem: LectureExamDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
