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

    private val _viewType = MutableLiveData<Int>()
    val viewType: LiveData<Int>
        get() = _viewType


    init {
        _evaluationList.value = arrayListOf()
        _viewType.value = 0
    }

    fun changeData(dataList : ArrayList<EvaluationData>) {
        _evaluationList.value = dataList
    }

    fun setViewType(type : Int) {
        _viewType.value = type
    }
}

object EvaluationBindingAdapter {
    @BindingAdapter("evaluationList","viewType")
    @JvmStatic
    fun setList(recyclerView: RecyclerView, items : ArrayList<EvaluationData>, viewType: Int) {
        if(recyclerView.adapter == null)
            recyclerView.adapter = EvaluationListAdapter()
        val evaluationAdapter = recyclerView.adapter as EvaluationListAdapter
        evaluationAdapter.viewType = viewType

        evaluationAdapter.evaluationListData = items
        evaluationAdapter.notifyDataSetChanged()
    }
}