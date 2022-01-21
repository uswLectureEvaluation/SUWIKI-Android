package com.kunize.uswtimetable.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MoreViewModel : ViewModel() {
    private var _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> get() = _loggedIn

    private var _id = ""
    val id get() = _id

    private var _point = MutableLiveData<Int>()

    val point: LiveData<String> = Transformations.map(_point) { point ->
        "$point P"
    }

    private var _myEvaluationCount = MutableLiveData<Int>()
    val myEvaluationCount: LiveData<Int> get() = _myEvaluationCount

    private var _myExamInfoCount = MutableLiveData<Int>()
    val myExamInfoCount: LiveData<Int> get() = _myExamInfoCount

    private var _openedExamCount = MutableLiveData<Int>()
    val openedExamCount: LiveData<Int> get() = _openedExamCount

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
        _loggedIn.value = false

        _myEvaluationCount.value = 3
        _myExamInfoCount.value = 2
        _openedExamCount.value = 7

        _myEvaluationPoint.value = 70
        _myExamInfoPoint.value = 100
        _openedExamPoint.value = -40

        _point.value = 130

        _id = "pmb0836"
    }

    fun login() {
        _loggedIn.value = true
        _id = "pmb0836"
    }

    fun logout() {
        _loggedIn.value = false
        _id = ""
    }

    fun toggleLogin() {
        _loggedIn.value = _loggedIn.value?.not()
    }
}
