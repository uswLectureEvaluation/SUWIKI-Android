package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemMyExamInfoBinding
import com.kunize.uswtimetable.dataclass.MyExamInfo
import com.kunize.uswtimetable.util.ExamDifficulty
import com.kunize.uswtimetable.util.ItemType

class MyExamInfoAdapter(val onItemClicked: (id: String, type: ItemType) -> Unit) :
    ListAdapter<MyExamInfo, MyExamInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemMyExamInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyExamInfo) {

            val difficultyType = convertExamDifficulty(data.examDifficulty)
            with(binding) {
                lectureName.text = data.subject
                yearSemester.text = data.semester

                when (difficultyType) {
                    ExamDifficulty.EASY -> {
                        tvExamDifficulty.text = "쉬움"
                        tvExamDifficulty.setTextColor(
                            ContextCompat.getColor(
                                tvEvaluationType.context,
                                R.color.custom_yellow
                            )
                        )
                    }
                    ExamDifficulty.NORMAL -> {
                        tvExamDifficulty.text = "보통"
                        tvExamDifficulty.setTextColor(
                            ContextCompat.getColor(
                                tvEvaluationType.context,
                                R.color.custom_blue
                            )
                        )
                    }
                    ExamDifficulty.DIFFICULT -> {
                        tvExamDifficulty.text = "어려움"
                        tvExamDifficulty.setTextColor(
                            ContextCompat.getColor(
                                tvEvaluationType.context,
                                R.color.custom_red
                            )
                        )
                    }
                }
                tvExamType.text = data.examType
                tvEvaluationType.text = data.evaluationType
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

    private fun convertExamDifficulty(difficultyString: String) =
        when (difficultyString) {
            "쉬움" -> ExamDifficulty.EASY
            "보통" -> ExamDifficulty.NORMAL
            "어려움" -> ExamDifficulty.DIFFICULT
            else -> ExamDifficulty.NORMAL
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
        private val diffUtil = object : DiffUtil.ItemCallback<MyExamInfo>() {
            override fun areItemsTheSame(oldItem: MyExamInfo, newItem: MyExamInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyExamInfo, newItem: MyExamInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}