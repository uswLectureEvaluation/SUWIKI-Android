package com.kunize.uswtimetable.ui.common

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.EvaluationData

object BindingAdapter {
    @SuppressLint("NotifyDataSetChanged")
    @BindingAdapter("evaluationList")
    @JvmStatic
    fun setList(
        recyclerView: RecyclerView,
        items: MutableList<EvaluationData?>
    ) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = EvaluationListAdapter { }
        val evaluationAdapter = recyclerView.adapter as EvaluationListAdapter

        val prevItemSize = evaluationAdapter.evaluationListData.size
        val newItemSize = items.size

        Log.d("Scroll", "$prevItemSize $newItemSize")
        evaluationAdapter.evaluationListData = items
        //api를 호출했을 때 불러올 수 있는 데이터 개수의 최대값이 11이므로 만약 newItemSize의 크기가 11보다 크다면 데이터가 추가되었음을 의미
        when {
            newItemSize == prevItemSize -> evaluationAdapter.notifyDataSetChanged()
            newItemSize > 11 -> evaluationAdapter.notifyItemRangeInserted(
                prevItemSize + 1,
                newItemSize - prevItemSize + 1
            )
            else -> evaluationAdapter.notifyDataSetChanged()
        }
    }

    @BindingAdapter("difficultColor")
    @JvmStatic
    fun setColor(textView: TextView, difficult: String) {
        when (difficult) {
            "쉬움", "매우 쉬움" -> textView.setTextColor(
                ContextCompat.getColor(
                    textView.context,
                    R.color.custom_yellow
                )
            )
            "보통" -> textView.setTextColor(
                ContextCompat.getColor(
                    textView.context,
                    R.color.custom_blue
                )
            )
            else -> textView.setTextColor(
                ContextCompat.getColor(
                    textView.context,
                    R.color.custom_red
                )
            )
        }
    }
}