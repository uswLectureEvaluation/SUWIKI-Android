package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.PageViewModel
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.ui.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) :
    ViewModel(), HandlingErrorInterface {
    val toastViewModel = ToastViewModel()
    private val pageViewModel = PageViewModel()
    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel()
    private val _selectedType = MutableLiveData<String>()
    private val _searchValue = MutableLiveData<String>()

    init {
        _searchValue.value = ""
    }

    fun scrollBottomEvent() {
        if(pageViewModel.page.value == LAST_PAGE)
            return
        viewModelScope.launch {
            val response = getResponse()
            if (response.isSuccessful) {
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                commonRecyclerViewViewModel.deleteLoading()
                if(!tmpEvaluationData.isNullOrEmpty()) {
                    pageViewModel.isLastData(tmpEvaluationData)
                    commonRecyclerViewViewModel.evaluationList.value!!.addAll(tmpEvaluationData)
                    commonRecyclerViewViewModel.changeRecyclerViewData(commonRecyclerViewViewModel.evaluationList.value!!)

                    pageViewModel.nextPage()
                }
            } else {
                commonRecyclerViewViewModel.deleteLoading()
            }
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
    }

    fun changeType(option: String) {
        _selectedType.value = option
        pageViewModel.resetPage()
        commonRecyclerViewViewModel.loading()
    }

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = "$errorCode 에러 발생!"
        toastViewModel.showToastMsg()
        commonRecyclerViewViewModel.deleteLoading()
    }
}