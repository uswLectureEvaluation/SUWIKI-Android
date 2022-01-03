package com.kunize.uswtimetable.ui.evaluation

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.dataclass.EvaluationData
import okhttp3.internal.notify

class EvaluationViewModel : ViewModel() {
    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationList: LiveData<ArrayList<EvaluationData?>>
        get() = _evaluationList

    private val _viewType = MutableLiveData<Int>()
    val viewType: LiveData<Int>
        get() = _viewType


    init {
        _evaluationList.value = arrayListOf()
        _viewType.value = 0
    }

    fun changeData(dataList : ArrayList<EvaluationData?>) {
        _evaluationList.value = dataList
    }

    fun addData(dataList: ArrayList<EvaluationData?>) {
        if(dataList.isEmpty())
            return
        dataList.add(null)
        _evaluationList.value!!.addAll(dataList)
        _evaluationList.value = _evaluationList.value //대입을 해줘야지만 옵저버가 변화를 감지함.
        Log.d("Scroll","데이터 추가 ${_evaluationList.value?.size}")
    }

    fun deleteLoading() {
        if(_evaluationList.value?.get(evaluationList.value?.size!! - 1) == null) {
            _evaluationList.value?.removeAt(evaluationList.value?.size!! - 1)
            _evaluationList.value = _evaluationList.value //대입을 해줘야지만 옵저버가 변화를 감지함.
        }
        //대입을 해줘야지만 옵저버가 변화를 감지함.
    }

    fun setViewType(type : Int) {
        _viewType.value = type
    }
}

object EvaluationBindingAdapter {
    @BindingAdapter("evaluationList","viewType")
    @JvmStatic
    fun setList(recyclerView: RecyclerView, items : ArrayList<EvaluationData?>, viewType: Int) {
        if(recyclerView.adapter == null)
            recyclerView.adapter = EvaluationListAdapter()
        val evaluationAdapter = recyclerView.adapter as EvaluationListAdapter
        evaluationAdapter.viewType = viewType

        val prevItemSize = evaluationAdapter.evaluationListData.size
        val newItemSize = items.size

        Log.d("Scroll","변화감지 $newItemSize")
        evaluationAdapter.evaluationListData = items
        //api를 호출했을 때 불러올 수 있는 데이터 개수의 최대값이 11이므로 만약 newItemSize의 크기가 11보다 크다면 데이터가 추가되었음을 의미
        if(newItemSize > 11)
            evaluationAdapter.notifyItemRangeInserted(prevItemSize + 1, newItemSize - prevItemSize + 1)
        else
            evaluationAdapter.notifyDataSetChanged()
    }
}