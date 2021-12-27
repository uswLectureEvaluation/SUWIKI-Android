package com.kunize.uswtimetable.ui.more

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.StartActivity
import com.kunize.uswtimetable.login.LoginActivity

class MoreViewModel : ViewModel() {
    private var _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> get() = _loggedIn

    private var _id = MutableLiveData<String>()
    val id: LiveData<String> get() = _id

    private var _point = MutableLiveData<Int>()
    val point: LiveData<Int> get() = _point

    private var _myEvaluationCount = MutableLiveData<Int>()
    val myEvaluationCount: LiveData<Int> get() = _myEvaluationCount

    private var _myExamInfoCount = MutableLiveData<Int>()
    val myExamInfoCount: LiveData<Int> get() = _myExamInfoCount

    private var _openedExamCount = MutableLiveData<Int>()
    val openedExamCount: LiveData<Int> get() = _openedExamCount

    private var _myEvaluationPoint = MutableLiveData<Int>()
    val myEvaluationPoint: LiveData<Int> get() = _myEvaluationPoint

    private var _myExamInfoPoint = MutableLiveData<Int>()
    val myExamInfoPoint: LiveData<Int> get() = _myExamInfoPoint

    private var _openedExamPoint = MutableLiveData<Int>()
    val openedExamPoint: LiveData<Int> get() = _openedExamPoint

    init {
        _loggedIn.value = false

        _myEvaluationCount.value = 3
        _myExamInfoCount.value = 2
        _openedExamCount.value = 7

        _myEvaluationPoint.value = 70
        _myExamInfoPoint.value = 100
        _openedExamPoint.value = -40

        _point.value = 130
        _id.value = "pmb0836"
    }

    fun login() {
        _loggedIn.value = true
    }

    fun logout() {
        _loggedIn.value = false
    }

    fun loginOrOut() {
        _loggedIn.value = _loggedIn.value?.not()
    }
}
