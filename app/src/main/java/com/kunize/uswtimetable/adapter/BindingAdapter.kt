package com.kunize.uswtimetable.adapter

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragmentDirections

object BindingAdapter {
    @BindingAdapter("evaluationList","viewType")
    @JvmStatic
    fun setList(recyclerView: RecyclerView, items : java.util.ArrayList<EvaluationData?>, viewType: Int) {
        if(recyclerView.adapter == null)
            recyclerView.adapter = EvaluationListAdapter()
        val evaluationAdapter = recyclerView.adapter as EvaluationListAdapter

        //Callback
        evaluationAdapter.setOnItemClickListener(object : EvaluationListAdapter.OnItemClickListener{
            override fun onItemClick(v: View, lectureName: String, professor: String) {
                Log.d("callback!","$lectureName, $professor")
                val action = NavGraphDirections.actionGlobalLectureInfoFragment(lectureName, professor)
                v.findNavController().navigate(action)
            }
        })
        evaluationAdapter.viewType = viewType

        val prevItemSize = evaluationAdapter.evaluationListData.size
        val newItemSize = items.size

        Log.d("Scroll","$recyclerView $newItemSize")
        evaluationAdapter.evaluationListData = items
        //api를 호출했을 때 불러올 수 있는 데이터 개수의 최대값이 11이므로 만약 newItemSize의 크기가 11보다 크다면 데이터가 추가되었음을 의미
        if(newItemSize > 11)
            evaluationAdapter.notifyItemRangeInserted(prevItemSize + 1, newItemSize - prevItemSize + 1)
        else
            evaluationAdapter.notifyDataSetChanged()
    }
}