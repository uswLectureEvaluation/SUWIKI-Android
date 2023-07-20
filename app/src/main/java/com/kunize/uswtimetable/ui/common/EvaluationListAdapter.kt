package com.kunize.uswtimetable.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemRecyclerExamDetailBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerLectureDetailBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerLectureSearchBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerProgressBinding
import com.kunize.uswtimetable.util.LectureItemViewType
import com.suwiki.domain.model.EvaluationData

class EvaluationListAdapter(val onItemClicked: (id: Long) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var evaluationListData = listOf<EvaluationData?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LectureItemViewType.LECTURE -> {
                val binding = ItemRecyclerLectureDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LectureInfoHolder(binding)
            }

            LectureItemViewType.EXAM -> {
                val binding = ItemRecyclerExamDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                ExamInfoHolder(binding)
            }

            else -> {
                val binding = ItemRecyclerProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        evaluationListData[position]?.let {
            return it.recyclerViewType
        }
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = evaluationListData[position]
        data?.let {
            if (holder is LectureInfoHolder) {
                holder.setData(data)
            }
            if (holder is LectureSearchHolder) {
                holder.setData(data)
            }
            if (holder is ExamInfoHolder) {
                holder.setData(data)
            }
        }
    }

    override fun getItemCount(): Int = evaluationListData.size

    inner class LoadingHolder(private val binding: ItemRecyclerProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LectureSearchHolder(private val binding: ItemRecyclerLectureSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                evaluationData = data
                averageSeeDetail.seeDetail.setOnClickListener {
                    if (scores.layout.isVisible) {
                        averageSeeDetail.seeDetail.setText(R.string.see_detail)
                        scores.layout.visibility = View.GONE
                    } else {
                        averageSeeDetail.seeDetail.setText(R.string.see_short)
                        scores.layout.visibility = View.VISIBLE
                    }
                }
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    itemView.setOnClickListener {
                        onItemClicked(data.lectureId)
                    }
                }
                executePendingBindings()
            }
        }
    }

    inner class LectureInfoHolder(private val binding: ItemRecyclerLectureDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                this.data = data
                tvDetail.setOnClickListener {
                    if (clDetail.isVisible) {
                        tvDetail.setText(R.string.see_detail)
                        clDetail.visibility = View.GONE
                    } else {
                        tvDetail.setText(R.string.see_short)
                        clDetail.visibility = View.VISIBLE
                    }
                }
                tvReport.setOnClickListener {
                    onItemClicked(data.lectureId)
                }
                executePendingBindings()
            }
        }
    }

    inner class ExamInfoHolder(private val binding: ItemRecyclerExamDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                this.data = data
                tvReport.setOnClickListener {
                    onItemClicked(data.lectureId)
                }
                executePendingBindings()
            }
        }
    }
}
