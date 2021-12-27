package com.kunize.uswtimetable.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemRecyclerEvaluationBinding
import com.kunize.uswtimetable.dataclass.EvaluationData

class EvaluationListAdapter : RecyclerView.Adapter<EvaluationListAdapter.Holder>() {
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
                lectureName.text = data.name
                averRatingBar.rating = data.aver
                lectureProfessor.text = data.professor
                lectureType.text = data.type
                averScore.text = data.aver.toString()
                satisfactionScore.text = data.satisfaction.toString()
                learningScore.text = data.learning.toString()
                honeyScore.text = data.honey.toString()

                seeDetail.setOnClickListener {
                    if(seeDetailLayout.isVisible) {
                        seeDetail.setText(R.string.see_detail)
                        seeDetailLayout.visibility = View.GONE
                    }
                    else {
                        seeDetail.setText(R.string.see_shrot)
                        seeDetailLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

    }
}