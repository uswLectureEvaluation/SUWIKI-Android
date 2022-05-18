package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.EvaluationData
import com.kunize.uswtimetable.data.remote.LectureDetailInfoDto
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.PageViewModel
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.repository.lecture_info.LectureInfoRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LectureInfoViewModel(private val lectureInfoRepository: LectureInfoRepository) : ViewModel(), HandlingErrorInterface {
    val pageViewModel = PageViewModel()
    val toastViewModel = ToastViewModel()
    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<EvaluationData>()
    private val _writeBtnText = MutableLiveData<Int>()
    val writeBtnText: LiveData<Int>
        get() = _writeBtnText

    private val _showNoExamDataLayout = MutableLiveData<Boolean>()
    val showNoExamDataLayout: LiveData<Boolean>
        get() = _showNoExamDataLayout

    private val _showHideExamDataLayout = MutableLiveData<Boolean>()
    val showHideExamDataLayout: LiveData<Boolean>
        get() = _showHideExamDataLayout

    private val _lectureDetailInfoData = MutableLiveData<LectureDetailInfoDto>()
    val lectureDetailInfoData: LiveData<LectureDetailInfoDto>
        get() = _lectureDetailInfoData

    init {
        _writeBtnText.value = R.string.write_evaluation
        _showNoExamDataLayout.value = false
        _showHideExamDataLayout.value = false
    }

    fun lectureInfoRadioBtnClicked() {
        pageViewModel.resetPage()
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        changeWriteBtnText(R.string.write_evaluation)
        commonRecyclerViewViewModel.loading()
        getEvaluationList()
    }

    fun examInfoRadioBtnClicked() {
        pageViewModel.resetPage()
        changeWriteBtnText(R.string.write_exam)
        commonRecyclerViewViewModel.loading()
        getExamList()
    }

    fun usePointBtnClicked() {
        viewModelScope.launch {
            val response = lectureInfoRepository.buyExam(pageViewModel.lectureId)
            if(response.isSuccessful) {
                if(response.body() == "success") {
                    _showHideExamDataLayout.value = false
                    pageViewModel.resetPage()
                    commonRecyclerViewViewModel.loading()
                    getExamList()
                }
            } else handleError(response.code())
        }
    }

    private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    suspend fun setInfoValue(): Boolean {
        val response: Response<LectureDetailInfoDto>
        withContext(viewModelScope.coroutineContext) {
            response = lectureInfoRepository.getLectureDetailInfo(pageViewModel.lectureId)
            if (response.isSuccessful) {
                _lectureDetailInfoData.value = response.body()
            } else {
                handleError(response.code())
            }
        }
        return response.isSuccessful
    }

    fun scrollBottomEvent() {
        if (pageViewModel.page.value!! < 2)
            return
        getLectureList()
    }

    fun getLectureList() {
        if(pageViewModel.page.value!! == LAST_PAGE)
            return
        when (_writeBtnText.value) {
            R.string.write_evaluation -> getEvaluationList()
            else -> getExamList()
        }
    }

    private fun getEvaluationList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailEvaluation(pageViewModel.lectureId, pageViewModel.page.value!!.toInt())
            if (response.isSuccessful) {
                commonRecyclerViewViewModel.deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if (!tmpEvaluationData.isNullOrEmpty()) {
                    pageViewModel.isLastData(tmpEvaluationData)
                    commonRecyclerViewViewModel.itemList.value!!.addAll(tmpEvaluationData)
                    commonRecyclerViewViewModel.changeRecyclerViewData(commonRecyclerViewViewModel.itemList.value!!)
                }
                pageViewModel.nextPage()
            } else {
                handleError(response.code())
            }
        }
    }

    private fun getExamList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailExam(pageViewModel.lectureId, pageViewModel.page.value!!.toInt())
            if (response.isSuccessful) {
                val tmpResponse = response.body()
                val tmpExamData = tmpResponse?.convertToEvaluationData()
                commonRecyclerViewViewModel.deleteLoading()
                if (tmpExamData != null) {
                    pageViewModel.isLastData(tmpExamData)
                    if (tmpExamData.isEmpty() && tmpResponse.examDataExist)
                        _showHideExamDataLayout.value = true
                    else if (!tmpResponse.examDataExist)
                        _showNoExamDataLayout.value = true
                    else {
                        commonRecyclerViewViewModel.changeRecyclerViewData(tmpExamData!!)
                        pageViewModel.nextPage()
                    }
                }
            } else {
                handleError(response.code())
            }
        }
    }

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = when (errorCode) {
            403 -> "권한이 없어요! 이용 제한 내역을 확인하거나 문의해주세요!"
            400 -> "포인트가 부족해요!"
            else -> "$errorCode 에러 발생!"
        }
        toastViewModel.showToastMsg()
        commonRecyclerViewViewModel.deleteLoading()
    }
}