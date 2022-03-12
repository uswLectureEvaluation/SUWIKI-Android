package com.kunize.uswtimetable.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.ui.login.User

class MoreViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private var _myEvaluationPoint = MutableLiveData<Int>()
    val myEvaluationPoint: LiveData<Int> get() = _myEvaluationPoint
    val myEvaluationPointText: LiveData<String> = Transformations.map(_myEvaluationPoint) { point ->
        if (point >= 0) {
            "(+$point)"
        } else {
            "($point)"
        }
    }

    private var _myExamInfoPoint = MutableLiveData<Int>()
    val myExamInfoPoint: LiveData<Int> get() = _myExamInfoPoint
    val myExamInfoPointText: LiveData<String> = Transformations.map(_myExamInfoPoint) { point ->
        if (point >= 0) {
            "(+$point)"
        } else {
            "($point)"
        }
    }

    private var _openedExamPoint = MutableLiveData<Int>()
    val openedExamPoint: LiveData<Int> get() = _openedExamPoint
    val openedExamPointText: LiveData<String> = Transformations.map(_openedExamPoint) { point ->
        if (point >= 0) {
            "(+$point)"
        } else {
            "($point)"
        }
    }

    init {
        _myEvaluationPoint.value = 70
        _myExamInfoPoint.value = 100
        _openedExamPoint.value = -40
    }

    fun refresh() {
        _user.value = User
    }
}
