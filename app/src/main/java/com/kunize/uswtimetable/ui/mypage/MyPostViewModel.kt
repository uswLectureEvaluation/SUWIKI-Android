package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.ui.repository.my_post.MyPostRepository

class MyPostViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myEvaluationData = MutableLiveData<List<MyEvaluation>>()
    val myEvaluationData: LiveData<List<MyEvaluation>> get() = _myEvaluationData

    init {
        getMyEvaluations().let { data ->
            _myEvaluationData.value = data
        }
    }

    private fun getMyEvaluations(): List<MyEvaluation> {
        return repository.getEvaluations()
    }

    fun getMyEvaluationDetail(id: String): MyEvaluation? = myEvaluationData.value?.find { evaluation ->
        evaluation.id == id
    }
}