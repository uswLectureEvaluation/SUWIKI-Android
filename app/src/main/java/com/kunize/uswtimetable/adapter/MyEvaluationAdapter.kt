package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemMyPostBinding
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.util.ItemType

class MyEvaluationAdapter(val onItemClicked: (data: MyEvaluation, type: ItemType) -> Unit): ListAdapter<MyEvaluation, MyEvaluationAdapter.MyEvaluationViewHolder>(diffUtil) {

    inner class MyEvaluationViewHolder(private val binding: ItemMyPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyEvaluation) {
            with(binding) {
                lectureName.text = data.subject
                yearSemester.text = data.semester
                lectureProfessor.text = data.professor
                averRatingBar.rating = data.totalValue
                averScore.text = data.totalValue.toString()
                satisfactionScore.text = data.satisfaction.toString()
                learningScore.text = data.learning.toString()
                honeyScore.text = data.honey.toString()
                if (data.team) {
                    teamMeeting.text = "있음"
                    teamMeeting.setTextColor(ContextCompat.getColor(teamMeeting.context, R.color.custom_blue))
                } else {
                    teamMeeting.text = "없음"
                    teamMeeting.setTextColor(ContextCompat.getColor(teamMeeting.context, R.color.custom_gray))
                }
                task.text = data.homework
                grade.text = data.grade
                content.text = data.content
            }
            binding.root.setOnClickListener {
                onItemClicked(data, ItemType.ROOT_VIEW)
                binding.detailScoreLayout.visibility = when(binding.detailScoreLayout.visibility) {
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.VISIBLE
                }
                binding.content.maxLines = if(binding.content.maxLines > 2) 2 else 100
            }
            binding.editBtn.setOnClickListener {
                onItemClicked(data, ItemType.EDIT_BUTTON)
            }
            binding.deleteBtn.setOnClickListener {
                onItemClicked(data, ItemType.DELETE_BUTTON)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEvaluationViewHolder {
        return MyEvaluationViewHolder(ItemMyPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyEvaluationViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<MyEvaluation>() {
            override fun areItemsTheSame(oldItem: MyEvaluation, newItem: MyEvaluation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyEvaluation, newItem: MyEvaluation): Boolean {
                return oldItem == newItem
            }
        }
    }
}