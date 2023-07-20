package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.domain.usecase.GetUserInfoUsecase
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.ui.common.PageViewModel
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.util.LectureApiOption
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import com.suwiki.domain.model.LectureMain
import com.suwiki.domain.repository.SearchResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchResultRepository: SearchResultRepository,
    userInfoUsecase: GetUserInfoUsecase,
) : ViewModel() {
    val toastViewModel = ToastViewModel()
    private val pageViewModel = PageViewModel()

    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<LectureMain>()
    private var selectedType: String = MODIFIED
    var searchValue: String = ""

    var majorType = "전체"

    private val _sortText = MutableLiveData<String>()
    val sortText: LiveData<String>
        get() = _sortText

    private val _dialogItemClickEvent = MutableLiveData<Event<Boolean>>()
    val dialogItemClickEvent: LiveData<Event<Boolean>>
        get() = _dialogItemClickEvent

    private val sortTypeList = listOf(
        MODIFIED,
        LectureApiOption.HONEY,
        LectureApiOption.SATISFACTION,
        LectureApiOption.LEARNING,
        LectureApiOption.BEST,
    )
    val spinnerTextList = listOf("날짜", "꿀강", "만족도", "배움", "종합")

    val isLoggedIn: StateFlow<Boolean> = userInfoUsecase.isLoggedIn()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun scrollBottomEvent() {
        if (pageViewModel.page.value!! < 2) {
            return
        }
        viewModelScope.launch {
            getData()
        }
    }

    private suspend fun SearchResultViewModel.getData() {
        /*val response = getResponse() // TODO 구현!!
        if (response.isSuccessful) {
            val tmpEvaluationData = response.body()?.data
            commonRecyclerViewViewModel.deleteLoading()
            if (!tmpEvaluationData.isNullOrEmpty()) {
                pageViewModel.isLastData(tmpEvaluationData)
                commonRecyclerViewViewModel.itemList.value!!.addAll(tmpEvaluationData)
                commonRecyclerViewViewModel.changeRecyclerViewData(
//                    commonRecyclerViewViewModel.itemList.value!!,
                    commonRecyclerViewViewModel.itemList.value?: emptyList<LectureMain>() + tmpEvaluationData
                )
                pageViewModel.nextPage()
            }
        } else {
//            handleError(response.code()) // TODO Error 처리
        }*/
    }

    private suspend fun getResponse() = if (searchValue.isBlank()) {
        searchResultRepository.getLectureMainList(
            selectedType,
            pageViewModel.page.value ?: 1,
            if (majorType == "전체") "" else majorType,
        )
    } else {
        searchResultRepository.getSearchResultList(
            searchValue,
            selectedType,
            pageViewModel.page.value ?: 1,
            if (majorType == "전체") "" else majorType,
        )
    }

    fun search(searchValue: String) {
        this.searchValue = searchValue
        pageViewModel.resetPage()
        commonRecyclerViewViewModel.loading()
        viewModelScope.launch {
            getData()
        }
    }

    fun initType() {
        selectedType = sortTypeList[0]
        _sortText.value = spinnerTextList[0]
    }

    fun dialogItemClick(text: String) {
        changeType(spinnerTextList.indexOf(text))
    }

    fun changeType(position: Int) {
        selectedType = sortTypeList[position]
        _sortText.value = spinnerTextList[position]
        _dialogItemClickEvent.value = Event(true)
        pageViewModel.resetPage()
        commonRecyclerViewViewModel.loading()
        viewModelScope.launch {
            getData()
        }
    }
}
