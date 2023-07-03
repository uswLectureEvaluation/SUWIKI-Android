package com.kunize.uswtimetable.ui.mypage.mypost.evaluation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemMyPostBinding
import com.suwiki.domain.model.MyEvaluation

class MyEvaluationAdapter(private val viewModel: MyEvaluationViewModel) :
    ListAdapter<MyEvaluation, MyEvaluationAdapter.MyEvaluationViewHolder>(diffUtil) {

    inner class MyEvaluationViewHolder(private val binding: ItemMyPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MyEvaluation) {
            binding.data = data
            binding.viewmodel = viewModel
            binding.detailOrShortButton = binding.tvDetailShortButton
            binding.group = binding.layoutDetailScore
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEvaluationViewHolder {
        return MyEvaluationViewHolder(
            ItemMyPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: MyEvaluationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MyEvaluation>() {
            override fun areItemsTheSame(oldItem: MyEvaluation, newItem: MyEvaluation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyEvaluation, newItem: MyEvaluation): Boolean {
                return oldItem == newItem
            }
        }
    }
}
