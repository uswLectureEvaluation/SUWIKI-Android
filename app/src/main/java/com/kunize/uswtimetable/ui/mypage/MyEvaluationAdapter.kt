package com.kunize.uswtimetable.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemMyPostBinding
import com.kunize.uswtimetable.dataclass.MyEvaluationDto

class MyEvaluationAdapter(private val viewModel: MyEvaluationViewModel) :
    PagingDataAdapter<MyEvaluationDto, MyEvaluationAdapter.MyEvaluationViewHolder>(diffUtil) {

    inner class MyEvaluationViewHolder(private val binding: ItemMyPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MyEvaluationDto) {
            binding.data = data
            binding.viewmodel = viewModel
            binding.detailOrShortButton = binding.tvDetailShortButton
            binding.group = binding.layoutDetailScore
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEvaluationViewHolder {
        return MyEvaluationViewHolder(ItemMyPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyEvaluationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<MyEvaluationDto>() {
            override fun areItemsTheSame(oldItem: MyEvaluationDto, newItem: MyEvaluationDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyEvaluationDto, newItem: MyEvaluationDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}