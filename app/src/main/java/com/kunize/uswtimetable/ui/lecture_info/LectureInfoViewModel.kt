package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.common.PageViewModel
import com.kunize.uswtimetable.util.LAST_PAGE
import com.suwiki.domain.model.EvaluationData
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.LectureInfoRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LectureInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val lectureInfoRepository: LectureInfoRepository,
) : ViewModel() {
    val pageViewModel = PageViewModel()

    //    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<EvaluationData>()
//    val evaluationsState = CommonRecyclerViewState<EvaluationData>()

//    private val _writeBtnText = MutableLiveData<Int>()
//    val writeBtnText: LiveData<Int>
//        get() = _writeBtnText

//    private val _written = MutableLiveData<Boolean>()
//    val written: LiveData<Boolean>
//        get() = _written

//    private val _showNoExamDataLayout = MutableLiveData<Boolean>()
//    val showNoExamDataLayout: LiveData<Boolean>
//        get() = _showNoExamDataLayout

//    private val _showHideExamDataLayout = MutableLiveData<Boolean>()
//    val showHideExamDataLayout: LiveData<Boolean>
//        get() = _showHideExamDataLayout

//    private val _lectureDetailInfoData = MutableLiveData<LectureDetailInfo>()
//    val lectureDetailInfoData: LiveData<LectureDetailInfo>
//        get() = _lectureDetailInfoData

    private lateinit var _lectureInfoState: MutableStateFlow<LectureInfoState>
    val lectureInfoState: StateFlow<LectureInfoState>
        get() = _lectureInfoState.asStateFlow()

    val state: LectureInfoState
        get() = lectureInfoState.value

    init {
//        _writeBtnText.value = R.string.write_evaluation
//        _showNoExamDataLayout.value = false
//        _showHideExamDataLayout.value = false
    }

    init {
        viewModelScope.launch {
            val lectureId: Long = savedStateHandle["lectureId"] ?: error("lectureId is null")
            when (
                val response =
                    lectureInfoRepository.getLectureDetailInfo(lectureId)
            ) {
                is Result.Success -> {
                    reduce {
                        copy(lectureInfo = response.data, error = null)
                    }
                }

                is Result.Failure -> {
                    reduce {
                        copy(error = response.error)
                    }
                }
            }
        }
        fetchLectureList()
    }

    fun reportExam(lectureId: Long) {
        viewModelScope.launch {
            lectureInfoRepository.reportExam(lectureId)
        }
    }

    fun reportLecture(lectureId: Long) {
        viewModelScope.launch {
            lectureInfoRepository.reportLecture(lectureId)
        }
    }

    fun lectureInfoRadioBtnClicked() {
        pageViewModel.resetPage()
//        _showHideExamDataLayout.value = false
//        _showNoExamDataLayout.value = false
//        changeWriteBtnText(R.string.write_evaluation)
//        commonRecyclerViewViewModel.loading()
//        evaluationsState.loading = true
        viewModelScope.launch {
            reduce {
                copy(
                    showHideExamInfo = false,
                    showNoExamInfo = false,
                    writeButtonText = R.string.write_evaluation,
                    written = true,
                    evaluationsState = evaluationsState.apply {
                        loading = true
                    },
                )
            }
        }
        getEvaluationList()
    }

    fun examInfoRadioBtnClicked() {
        pageViewModel.resetPage()
//        changeWriteBtnText(R.string.write_exam)
//        commonRecyclerViewViewModel.loading()
//        evaluationsState.loading = true
        getExamList()
        viewModelScope.launch {
            reduce {
                copy(
                    writeButtonText = R.string.write_exam,
                    written = true,
                    evaluationsState = evaluationsState.apply {
                        loading = true
                    },
                )
            }
        }
    }

    fun usePointBtnClicked() {
        viewModelScope.launch {
            when (lectureInfoRepository.buyExam(pageViewModel.lectureId)) {
                is Result.Success -> {
                    reduce {
                        copy(
                            showHideExamInfo = false,
                            evaluationsState = evaluationsState.apply { loading = true },
                        )
                    }
//                    _showHideExamDataLayout.value = false
                    pageViewModel.resetPage()
//                    commonRecyclerViewViewModel.loading()
//                    evaluationsState.loading = true
                    getExamList()
                }

                is Result.Failure -> {}
            }
            /*if (response.isSuccessful) {
                if (response.body() == "success") {
                    _showHideExamDataLayout.value = false
                    pageViewModel.resetPage()
                    commonRecyclerViewViewModel.loading()
                    getExamList()
                }
            } else {
//                handleError(response.code())
            }*/
        }
    }

    /*private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
        _written.value = true
    }*/

    /*suspend fun fetchLectureIntegratedInfo(onSuccess: () -> Unit) {
        val response = lectureInfoRepository.getLectureDetailInfo(pageViewModel.lectureId)

        when (response) {
            is Result.Success -> {
                _lectureDetailInfoData.value = response.data
                onSuccess()
            }

            is Result.Failure -> {}
        }
        *//*when {
            response.isSuccessful -> {
                response.body()?.data?.let { data ->
                    _lectureDetailInfoData.value = data
                    onSuccess()
                }
            }

            else -> {
//                handleError(response.code())
            }
        }*//*
    }*/

    fun scrollBottomEvent() {
        if (pageViewModel.page.value!! < 2) {
            return
        }
        fetchLectureList()
    }

    fun fetchLectureList() {
        if (pageViewModel.page.value!! == LAST_PAGE) {
            return
        }
        when (state.writeButtonText) {
            R.string.write_evaluation -> getEvaluationList()
            else -> getExamList()
        }
        /*when (_writeBtnText.value) {
            R.string.write_evaluation -> getEvaluationList()
            else -> getExamList()
        }*/
    }

    private fun getEvaluationList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailEvaluation(
                    pageViewModel.lectureId,
                    pageViewModel.page.value!!.toInt(),
                )
            when (response) {
                is Result.Success -> {
//                    _written.value = response.data.written
                    reduce {
                        copy(
                            written = response.data.written,
                            evaluationsState = evaluationsState.apply { loading = false },
                        )
                    }
//                    commonRecyclerViewViewModel.deleteLoading()
//                    evaluationsState.loading = false
                    val tmpEvaluationData = response.data.data
                    if (tmpEvaluationData.isNotEmpty()) {
                        pageViewModel.isLastData(tmpEvaluationData.toMutableList())
//                        commonRecyclerViewViewModel.itemList.value!!.addAll(tmpEvaluationData)
                        /*commonRecyclerViewViewModel.changeRecyclerViewData(
                            commonRecyclerViewViewModel.itemList.value!!,
                        )*/
//                        evaluationsState.addItems(tmpEvaluationData)
                        reduce {
                            copy(
                                evaluationsState = evaluationsState.apply {
                                    addItems(tmpEvaluationData)
                                },
                            )
                        }
                    }
                    pageViewModel.nextPage()
                }

                is Result.Failure -> reduce { copy(error = response.error) }
            }
            /*if (response.isSuccessful) {
                _written.value = response.body()?.written
                commonRecyclerViewViewModel.deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if (!tmpEvaluationData.isNullOrEmpty()) {
                    pageViewModel.isLastData(tmpEvaluationData)
                    commonRecyclerViewViewModel.itemList.value!!.addAll(tmpEvaluationData)
                    commonRecyclerViewViewModel.changeRecyclerViewData(commonRecyclerViewViewModel.itemList.value!!)
                }
                pageViewModel.nextPage()
            } else {
//                handleError(response.code())
            }*/
        }
    }

    private fun getExamList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailExam(
                    pageViewModel.lectureId,
                    pageViewModel.page.value ?: 1,
                )
            when (response) {
                is Result.Success -> {
                    val evaluationDatas: MutableList<EvaluationData?> =
                        response.data.data.toMutableList()
                    if (evaluationDatas.size == 10) {
                        evaluationDatas.add(null)
                    } else {
                        pageViewModel._page.value = LAST_PAGE
                    }
                    if (evaluationDatas.isEmpty() && response.data.examDataExist) {
                        reduce {
                            copy(
                                showHideExamInfo = true,
                            )
                        }
                    } else if (!response.data.examDataExist) {
                        reduce {
                            copy(
                                showNoExamInfo = true,
                            )
                        }
                    } else {
                        reduce {
                            copy(
                                written = response.data.written,
                                evaluationsState = evaluationsState.apply {
                                    loading = false
                                    addItems(evaluationDatas)
                                },
                            )
                        }
                        pageViewModel.nextPage()
                    }
                }

                is Result.Failure -> reduce { copy(error = response.error) }
            }
        }
    }

    private suspend fun reduce(reducer: LectureInfoState.() -> LectureInfoState) {
        withContext(SINGLE_THREAD) {
            _lectureInfoState.value = reducer(state)
        }
    }

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val SINGLE_THREAD = newSingleThreadContext("single thread")
    }
}
