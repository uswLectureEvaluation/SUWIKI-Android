package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import kotlinx.coroutines.Job

class SignUpViewModel : ViewModel() {
    private var job: Job? = null
    var id: String? = null
    var pw: String? = null
    private var _email: String? = null
    val email get() = _email + SCHOOL_DOMAIN_AT

    private var _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> get() = _currentPage

    init {
        _currentPage.value = 0
    }

    fun moveToNextPage() {
        _currentPage.value?.let { page ->
            if (page < 2) {
                _currentPage.value = page + 1
            }
        }
    }

    fun moveToPreviousPage() {
        _currentPage.value?.let { page ->
            if (page > 0) {
                _currentPage.value = page - 1
            }
        }
    }

    fun movePage(page: Int) {
        if (page in 0..2) _currentPage.value = page
    }

    fun saveIdPw(id: String, pw: String) {
        this.id = id
        this.pw = pw
    }

    fun saveEmail(email: String) {
        _email = email
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}