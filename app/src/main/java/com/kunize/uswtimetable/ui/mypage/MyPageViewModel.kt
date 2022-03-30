package com.kunize.uswtimetable.ui.mypage

import android.util.Log
import androidx.lifecycle.*
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.dataclass.UserDataDto
import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.ui.repository.mypage.MyPageRepository
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(private val repository: MyPageRepository) : ViewModel() {
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
        viewModelScope.launch {
            val userDataResponse = repository.getUserData()
            if (userDataResponse.isSuccessful) {
                Log.d(TAG, "MoreViewModel - refresh() called / ${userDataResponse.body()}")
                setUserData(userDataResponse.body()!!)
            }
            launch(Dispatchers.Main) {
                _user.value = User
            }
        }
    }

    private fun setUserData(userData: UserDataDto) {
        User.setUser(
            LoggedInUser(
                userId = userData.userId,
                point = userData.point,
                writtenExam = userData.writtenExam,
                writtenLecture = userData.writtenEvaluation,
                viewExam = userData.viewExam,
                email = userData.email
            )
        )
    }
}
