package com.kunize.uswtimetable.ui.evaluation

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.dataclass.EvaluationData

class EvaluationViewModel : ViewModel() {
    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData>>()
    val evaluationList: LiveData<ArrayList<EvaluationData>>
        get() = _evaluationList

    init {
        _evaluationList.value = arrayListOf()
    }

    fun changeData(dataList : ArrayList<EvaluationData>) {
        _evaluationList.value = dataList
    }
}

object EvaluationBindingAdapter {
    @BindingAdapter("evaluationList")
    @JvmStatic
    fun setList(recyclerView: RecyclerView, items : ArrayList<EvaluationData>) {
        if(recyclerView.adapter == null)
            recyclerView.adapter = EvaluationListAdapter()
        val evaluationAdapter = recyclerView.adapter as EvaluationListAdapter

        evaluationAdapter.evaluationListData = items
        evaluationAdapter.notifyDataSetChanged()
    }
}