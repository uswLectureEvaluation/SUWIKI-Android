package com.kunize.uswtimetable.ui.evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.EvaluationData
import com.kunize.uswtimetable.data.remote.LectureMain
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.LectureApiOption
import com.kunize.uswtimetable.util.LectureApiOption.BEST
import com.kunize.uswtimetable.util.LectureApiOption.HONEY
import com.kunize.uswtimetable.util.LectureApiOption.LEARNING
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import com.kunize.uswtimetable.util.LectureApiOption.SATISFACTION
import com.kunize.uswtimetable.util.PreferenceManager.Companion.getString
import kotlinx.coroutines.launch

class EvaluationViewModel(private val evaluationRepository: EvaluationRepository) : ViewModel(),
    HandlingErrorInterface {

    private var spinnerTypeList = listOf(MODIFIED, HONEY, SATISFACTION, LEARNING, BEST)
    private val spinnerTextList = listOf("최근 올라온 강의", "꿀 강의", "만족도가 높은 강의", "배울게 많은 강의", "Best 강의")
    private val spinnerImageList = listOf(
        R.drawable.ic_fire, R.drawable.ic_honey, R.drawable.ic_thumbs,
        R.drawable.ic_book, R.drawable.ic_1st
    )

    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<LectureMain>()
    val toastViewModel = ToastViewModel()
    private val _selectedType = MutableLiveData<String>()

    private val _sortImgId = MutableLiveData<Int>()
    val sortImgId: LiveData<Int>
        get() = _sortImgId

    private val _sortText = MutableLiveData<String>()
    val sortText: LiveData<String>
        get() = _sortText

    private val _dialogItemClickEvent = MutableLiveData<Event<Boolean>>()
    val dialogItemClickEvent: LiveData<Event<Boolean>>
        get() = _dialogItemClickEvent

    init {
        changeType(0)
    }

    private fun loadEvaluationData() {
        viewModelScope.launch {
            val response = evaluationRepository.getLectureMainList(_selectedType.value.toString())
            if (response.isSuccessful) {
                commonRecyclerViewViewModel.deleteLoading()
                commonRecyclerViewViewModel.changeRecyclerViewData(response.body()!!.data)
            } else {
                handleError(response.code())
            }
        }
    }

    fun dialogItemClick(position: Int) {
        changeType(position)
    }

    private fun changeType(position: Int) {
        _selectedType.value = spinnerTypeList[position]
        _sortImgId.value = spinnerImageList[position]
        _sortText.value = spinnerTextList[position]
        _dialogItemClickEvent.value = Event(true)
        loadEvaluationData()
    }

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = "$errorCode 에러 발생!"
        toastViewModel.showToastMsg()
        commonRecyclerViewViewModel.changeRecyclerViewData(mutableListOf())
    }
}