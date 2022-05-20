package com.kunize.uswtimetable.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemMyExamInfoBinding
import com.kunize.uswtimetable.dataclass.MyExamInfoDto

class MyExamInfoAdapter(private val viewModel: MyExamInfoViewModel) :
    ListAdapter<MyExamInfoDto, MyExamInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemMyExamInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyExamInfoDto) {
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
        private val diffUtil = object : DiffUtil.ItemCallback<MyExamInfoDto>() {
            override fun areItemsTheSame(oldItem: MyExamInfoDto, newItem: MyExamInfoDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MyExamInfoDto,
                newItem: MyExamInfoDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}