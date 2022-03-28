package com.kunize.uswtimetable.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.collection.LLRBNode
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.*
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.util.LectureItemViewType

class EvaluationListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var evaluationListData = mutableListOf<EvaluationData?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LectureItemViewType.SHORT -> {
                val binding = ItemRecyclerLectureSearchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LectureSearchHolder(binding)
            }
            LectureItemViewType.LECTURE -> {
                val binding = ItemRecyclerLectureInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LectureInfoHolder(binding)
            }
            LectureItemViewType.EXAM -> {
                val binding = ItemRecyclerExamInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ExamInfoHolder(binding)
            }
            else -> {
                val binding = ItemRecyclerProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
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
            if (holder is LectureInfoHolder)
                holder.setData(data)
            if (holder is LectureSearchHolder)
                holder.setData(data)
            if (holder is ExamInfoHolder)
                holder.setData(data)
        }
    }

    override fun getItemCount(): Int = evaluationListData.size

    inner class LoadingHolder(private val binding: ItemRecyclerProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    //Callback
    interface OnItemClickListener{
        fun onItemClick(v : View, lectureId : Long)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

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
                if(pos!=RecyclerView.NO_POSITION)
                    itemView.setOnClickListener{
                        listener?.onItemClick(itemView, data.lectureId)
                    }
                executePendingBindings()
            }
        }
    }

    inner class LectureInfoHolder(private val binding: ItemRecyclerLectureInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                evaluationData = data
                averageSeeDetail.seeDetail.setOnClickListener {
                    if (scoresEtc.layout.isVisible) {
                        averageSeeDetail.seeDetail.setText(R.string.see_detail)
                        scoresEtc.layout.visibility = View.GONE
                    } else {
                        averageSeeDetail.seeDetail.setText(R.string.see_short)
                        scoresEtc.layout.visibility = View.VISIBLE
                    }
                }
                executePendingBindings()
            }
        }
    }

    inner class ExamInfoHolder(private val binding: ItemRecyclerExamInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                evaluationData = data
                executePendingBindings()
            }
        }
    }
}