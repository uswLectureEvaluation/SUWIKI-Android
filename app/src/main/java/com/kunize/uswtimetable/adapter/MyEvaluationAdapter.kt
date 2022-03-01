package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemMyPostBinding
import com.kunize.uswtimetable.dataclass.MyEvaluation

class MyEvaluationAdapter(val onItemClicked: (id: String, type: ItemType) -> Unit): ListAdapter<MyEvaluation, MyEvaluationAdapter.MyEvaluationViewHolder>(diffUtil) {

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
                teamMeeting.text = if (data.team) "많음" else "적음"
                task.text = data.homework
                grade.text = data.grade
                content.text = data.content
            }
            binding.root.setOnClickListener {
                onItemClicked(data.id, ItemType.ROOT_VIEW)
            }
            binding.editBtn.setOnClickListener {
                onItemClicked(data.id, ItemType.EDIT_BUTTON)
            }
            binding.deleteBtn.setOnClickListener {
                onItemClicked(data.id, ItemType.DELETE_BUTTON)
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

        enum class ItemType {
            ROOT_VIEW,
            EDIT_BUTTON,
            DELETE_BUTTON
        }
    }
}