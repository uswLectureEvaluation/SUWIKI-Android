package com.kunize.uswtimetable.ui.evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.domain.usecase.GetUserInfoUsecase
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.EvaluationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EvaluationViewModel @Inject constructor(
    private val evaluationRepository: EvaluationRepository,
    userInfoUsecase: GetUserInfoUsecase,
) : ViewModel() {

//    private var spinnerTypeList = listOf(MODIFIED, HONEY, SATISFACTION, LEARNING, BEST)

    //    val spinnerTextList = listOf("최근 올라온 강의", "꿀 강의", "만족도가 높은 강의", "배울게 많은 강의", "Best 강의")
    /*private val spinnerImageList = listOf(
        R.drawable.ic_fire,
        R.drawable.ic_honey,
        R.drawable.ic_thumbs,
        R.drawable.ic_book,
        R.drawable.ic_1st,
    )*/
    var majorType = "전체"
//    var spinnerSel = 0

//    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<LectureMain>()
//    val lectureMainState = CommonRecyclerViewState<LectureMain>()

    val toastViewModel = ToastViewModel()
    private val _selectedType = MutableLiveData<String>()

    private val _sortImgId = MutableLiveData<Int>()
    val sortImgId: LiveData<Int>
        get() = _sortImgId

    private val _sortText = MutableLiveData<String>()
    val sortText: LiveData<String>
        get() = _sortText

    private val _stateFlow = MutableStateFlow(EvaluationState())
    val stateFlow = _stateFlow.asStateFlow()
    val state get() = stateFlow.value

    private val _dialogItemClickEvent = MutableLiveData<Event<Boolean>>()
    val dialogItemClickEvent: LiveData<Event<Boolean>>
        get() = _dialogItemClickEvent

    val loggedIn: StateFlow<Boolean> = userInfoUsecase.isLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false,
        )

    private fun loadEvaluationData() {
        viewModelScope.launch {
            val response = evaluationRepository.getLectureMainList(
//                _selectedType.value.toString(),
                state.evaluationSortBy.toString(),
                if (majorType == "전체") "" else majorType,
            )
            when (response) {
                is Result.Success -> {
                    state.lectureMainsState.loading = false
                    state.lectureMainsState.setItems(response.data)
//                    commonRecyclerViewViewModel.deleteLoading()
//                    commonRecyclerViewViewModel.changeRecyclerViewData(response.data)
                }

                is Result.Failure -> {
                    state.lectureMainsState.setItems(emptyList())
                } // commonRecyclerViewViewModel.changeRecyclerViewData(emptyList())
            }
        }
    }

    fun dialogItemClick(position: Int) {
        changeType(position)
    }

    fun changeType(position: Int) {
//        _selectedType.value = spinnerTypeList[position]
        _dialogItemClickEvent.value = Event(true)

        viewModelScope.launch {
            val sortBy = EvaluationSortBy.getCategoryForPosition(position)
            reduce {
                state.copy(
                    selectedIndex = position,
                    evaluationSortBy = sortBy,
                )
            }
        }

        loadEvaluationData()
    }

    private suspend fun reduce(reducer: EvaluationState.() -> EvaluationState) {
        withContext(SINGLE_THREAD) {
            _stateFlow.value = state.reducer()
        }
    }

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val SINGLE_THREAD = newSingleThreadContext("single thread")
    }
}
