package com.kunize.uswtimetable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                evaluationData = data
                seeDetail.setOnClickListener {
                    if(seeDetailLayout.isVisible) {
                        seeDetail.setText(R.string.see_detail)
                        seeDetailLayout.visibility = View.GONE
                    }
                    else {
                        seeDetail.setText(R.string.see_short)
                        seeDetailLayout.visibility = View.VISIBLE
                    }
                }
                executePendingBindings()
            }
        }
    }
}