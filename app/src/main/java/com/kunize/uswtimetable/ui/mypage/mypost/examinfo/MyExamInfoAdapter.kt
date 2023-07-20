package com.kunize.uswtimetable.ui.mypage.mypost.examinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemMyExamInfoBinding
import com.suwiki.domain.model.LectureExam

class MyExamInfoAdapter(private val viewModel: MyExamInfoViewModel) :
    ListAdapter<LectureExam, MyExamInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemMyExamInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LectureExam) {
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
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<LectureExam>() {
            override fun areItemsTheSame(oldItem: LectureExam, newItem: LectureExam): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LectureExam,
                newItem: LectureExam,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
