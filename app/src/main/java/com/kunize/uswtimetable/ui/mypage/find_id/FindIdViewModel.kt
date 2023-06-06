package com.kunize.uswtimetable.ui.mypage.find_id

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.FindIdRepository
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
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
            val response = repository.findId(emailWithDomain)

            response.onSuccess {
                if (data.success) {
                    successMessage.postValue("$emailWithDomain 로 아이디가 전송되었습니다.")
                }
            }.onError {
                when (statusCode) {
                    StatusCode.BadRequest -> errorMessage.postValue("존재하지 않는 이메일 주소입니다.")
                    StatusCode.InternalServerError -> errorMessage.postValue("서버가 원활하지 않습니다.")
                    else -> errorMessage.postValue("알 수 없는 오류입니다.")
                }
            }

            loading.postValue(false)
        }
    }
}
