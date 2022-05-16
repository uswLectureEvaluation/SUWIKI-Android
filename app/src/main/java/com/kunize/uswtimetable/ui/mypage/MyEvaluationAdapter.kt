package com.kunize.uswtimetable.ui.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemMyPostBinding
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.util.ItemType

class MyEvaluationAdapter(val onItemClicked: (data: MyEvaluationDto, type: ItemType) -> Unit) :
    ListAdapter<MyEvaluationDto, MyEvaluationAdapter.MyEvaluationViewHolder>(diffUtil) {

    inner class MyEvaluationViewHolder(private val binding: ItemMyPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val gray = ContextCompat.getColor(binding.detailScoreLayout.context, R.color.custom_gray)
        private val darkGray = ContextCompat.getColor(binding.detailScoreLayout.context, R.color.custom_dark_gray)
        private val red = ContextCompat.getColor(binding.detailScoreLayout.context, R.color.custom_red)

        fun bind(data: MyEvaluationDto) {
            with(binding) {
                lectureName.text = data.lectureName
                yearSemester.text = data.selectedSemester
                lectureProfessor.text = data.professor
                averRatingBar.rating = data.totalAvg
                averScore.text = data.totalAvg.toString()
                satisfactionScore.text = data.satisfaction.toString()
                learningScore.text = data.learning.toString()
                honeyScore.text = data.honey.toString()
                if (data.team == 1) {
                    teamMeeting.text = "있음"
                    teamMeeting.setTextColor(darkGray)
                } else {
                    teamMeeting.text = "없음"
                    teamMeeting.setTextColor(gray)
                }
                when (data.homework) {
                    0 -> {
                        task.text = "없음"
                        task.setTextColor(gray)
                    }
                    1 -> {
                        task.text = "보통"
                        task.setTextColor(darkGray)
                    }
                    2 -> {
                        task.text = "많음"
                        task.setTextColor(red)
                    }
                }

                when (data.difficulty) {
                    0 -> {
                        grade.text = "잘 줌"
                        grade.setTextColor(gray)
                    }
                    1 -> {
                        grade.text = "보통"
                        grade.setTextColor(darkGray)
                    }
                    2 -> {
                        grade.text = "까다로움"
                        grade.setTextColor(red)
                    }
                }
                content.text = data.content
            }
            binding.root.setOnClickListener {
                onItemClicked(data, ItemType.ROOT_VIEW)
                binding.detailScoreLayout.visibility = when (binding.detailScoreLayout.visibility) {
                    View.VISIBLE -> View.GONE
                    View.GONE -> View.VISIBLE
                    else -> View.VISIBLE
                }
                binding.content.maxLines = if (binding.content.maxLines > 2) 2 else 100
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