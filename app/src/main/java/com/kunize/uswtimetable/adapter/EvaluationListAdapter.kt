package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemRecyclerEvaluationBinding
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureItemViewType

class EvaluationListAdapter : RecyclerView.Adapter<EvaluationListAdapter.Holder>() {
    var viewType: Int = 0
    var evaluationListData = mutableListOf<EvaluationData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerEvaluationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = evaluationListData[position]
        holder.setData(data)
    }

    override fun getItemCount(): Int = evaluationListData.size

    inner class Holder(private val binding: ItemRecyclerEvaluationBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun setData(data: EvaluationData) {
            with(binding) {
                //viewType에 따라 뷰 숨김/보임 처리
                when(viewType) {
                    //강의평가 목록
                    LectureItemViewType.SHORT -> {
                        lectureType.visibility = View.VISIBLE
                        yearSemester.visibility = View.GONE
                        content.visibility = View.GONE
                        itemLayout.isFocusable = true
                        itemLayout.isClickable = true
                    }
                    //내가 쓴 강의평가
                    LectureItemViewType.USERLECTURE -> {
                        setVisibilityAdditionalInfo()
                        editBtn.visibility = View.VISIBLE
                        deleteBtn.visibility = View.VISIBLE
                    }
                    //내가 쓴 시험 정보
                    LectureItemViewType.USEREXAM -> {
                        goneForUserType()
                        editBtn.visibility = View.VISIBLE
                        deleteBtn.visibility = View.VISIBLE
                    }
                    //세부 강의평가 정보
                    LectureItemViewType.LECTURE -> {
                        goneLectureNameProf()
                        setVisibilityAdditionalInfo()
                        reportBtn.visibility = View.VISIBLE
                    }
                    //세부 시험 정보
                    LectureItemViewType.EXAM -> {
                        goneForNotUserType()
                        reportBtn.visibility = View.VISIBLE
                    }
                }

                evaluationData = data
                seeDetail.setOnClickListener {
                    if(detailScoreLayout.isVisible) {
                        seeDetail.setText(R.string.see_detail)
                        detailScoreLayout.visibility = View.GONE
                    }
                    else {
                        seeDetail.setText(R.string.see_short)
                        detailScoreLayout.visibility = View.VISIBLE
                    }
                }
                executePendingBindings()
            }
        }

        private fun ItemRecyclerEvaluationBinding.goneForNotUserType() {
            goneLectureNameProf()
            goneForUserType()
        }

        private fun ItemRecyclerEvaluationBinding.goneForUserType() {
            averScore.visibility = View.GONE
            averRatingBar.visibility = View.GONE
            seeDetail.visibility = View.GONE
            textViewNum6.visibility = View.GONE
            textViewAver.visibility = View.GONE
            detailScoreLayout.visibility = View.GONE
        }

        private fun ItemRecyclerEvaluationBinding.goneLectureNameProf() {
            lectureName.visibility = View.GONE
            lectureProfessor.visibility = View.GONE
        }

        private fun ItemRecyclerEvaluationBinding.setVisibilityAdditionalInfo() {
            teamMeeting.visibility = View.VISIBLE
            textViewMeeting.visibility = View.VISIBLE
            task.visibility = View.VISIBLE
            textViewTask.visibility = View.VISIBLE
            grade.visibility = View.VISIBLE
            textViewGrade.visibility = View.VISIBLE
        }
    }
}