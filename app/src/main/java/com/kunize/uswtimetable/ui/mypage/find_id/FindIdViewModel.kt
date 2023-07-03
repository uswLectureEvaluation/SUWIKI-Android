package com.kunize.uswtimetable.ui.mypage.find_id

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.SuwikiError
import com.suwiki.domain.repository.FindIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val repository: FindIdRepository,
) : ViewModel() {
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>(false)

    fun findId() {
        if (email.value?.isEmpty() == true) return
        val emailWithDomain = email.value + SCHOOL_DOMAIN_AT

        viewModelScope.launch {
            loading.postValue(true)

            when (val response = repository.findId(emailWithDomain)) {
                is Result.Success -> {
                    if (response.data) successMessage.postValue("$emailWithDomain 로 아이디가 전송되었습니다.")
                }

                is Result.Failure -> when (response.error) {
                    SuwikiError.RequestFailure -> errorMessage.postValue("존재하지 않는 이메일 주소입니다.")
                    else -> errorMessage.postValue("알 수 없는 오류입니다.")
                }
            }

            loading.postValue(false)
        }
    }
}
