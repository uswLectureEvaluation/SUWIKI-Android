package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.local.EvaluationData
import com.kunize.uswtimetable.data.remote.LectureMain
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.PageViewModel
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.util.LectureApiOption
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import kotlinx.coroutines.launch

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) :
    ViewModel(), HandlingErrorInterface {
    val toastViewModel = ToastViewModel()
    private val pageViewModel = PageViewModel()
    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel<LectureMain>()
    private val _selectedType = MutableLiveData<String>()
    private val _searchValue = MutableLiveData<String>()

    private val _radioButtons = List<MutableLiveData<Boolean>>(5) { MutableLiveData(false) }
    val radioButtons: List<LiveData<Boolean>>
        get() = _radioButtons

    private val sortTypeList = listOf(MODIFIED,
        LectureApiOption.HONEY,
        LectureApiOption.SATISFACTION,
        LectureApiOption.LEARNING,
        LectureApiOption.BEST
    )


    init {
        _radioButtons[0].value = true
        _searchValue.value = ""
        _selectedType.value = MODIFIED
    }

    fun scrollBottomEvent() {
        if(pageViewModel.page.value!! < 2)
            return
        viewModelScope.launch {
            getData()
        }
    }

    private suspend fun SearchResultViewModel.getData() {
        val response = getResponse()
        if (response.isSuccessful) {
            val tmpEvaluationData = response.body()?.data
            commonRecyclerViewViewModel.deleteLoading()
            if (!tmpEvaluationData.isNullOrEmpty()) {
                pageViewModel.isLastData(tmpEvaluationData)
                commonRecyclerViewViewModel.itemList.value!!.addAll(tmpEvaluationData)
                commonRecyclerViewViewModel.changeRecyclerViewData(commonRecyclerViewViewModel.itemList.value!!)
                pageViewModel.nextPage()
            }
        } else {
            handleError(response.code())
        }
    }

    private suspend fun getResponse() = if (_searchValue.value.toString().isBlank()) {
        searchResultRepository.getLectureMainList(
            _selectedType.value.toString(),
            pageViewModel.page.value ?: 1
        )
    } else {
        searchResultRepository.getSearchResultList(
            _searchValue.value.toString(),
            _selectedType.value.toString(),
            pageViewModel.page.value ?: 1
        )
    }

    fun search(searchValue: String) {
        _searchValue.value = searchValue
        pageViewModel.resetPage()
        commonRecyclerViewViewModel.loading()
        viewModelScope.launch {
            getData()
        }
    }

    fun changeType(option: Int) {
        _radioButtons.forEach {
            it.value = false
        }
        _radioButtons[option].value = true
        _selectedType.value = sortTypeList[option]
        pageViewModel.resetPage()
        commonRecyclerViewViewModel.loading()
        viewModelScope.launch {
            getData()
        }
    }

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = "$errorCode 에러 발생!"
        toastViewModel.showToastMsg()
        commonRecyclerViewViewModel.deleteLoading()
    }
}