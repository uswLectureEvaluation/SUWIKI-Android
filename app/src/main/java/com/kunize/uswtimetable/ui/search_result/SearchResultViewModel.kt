package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.BaseInfiniteRecyclerItemViewModel
import com.kunize.uswtimetable.ui.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) :
    BaseInfiniteRecyclerItemViewModel() {
    private val _selectedType = MutableLiveData<String>()
    private val _searchValue = MutableLiveData<String>()

    init {
        page.value = 1
        _searchValue.value = ""
        evaluationList.value = arrayListOf(null)
    }

    fun scrollBottomEvent() {
        if(page.value == LAST_PAGE)
            return
        viewModelScope.launch {
            val response = getResponse()
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                deleteLoading()
                if(!tmpEvaluationData.isNullOrEmpty()) {
                    if(tmpEvaluationData.size == 10)
                        tmpEvaluationData.add(null)
                    else
                        page.value = LAST_PAGE

                    evaluationList.value!!.addAll(tmpEvaluationData)
                    evaluationList.value = evaluationList.value

                    nextPage()
                }
            } else {
                evaluationList.value = arrayListOf()
            }
        }
    }

    private suspend fun getResponse() = if (_searchValue.value.toString().isBlank()) {
        searchResultRepository.getLectureMainList(
            _selectedType.value.toString(),
            page.value ?: 1
        )
    } else {
        searchResultRepository.getSearchResultList(
            _searchValue.value.toString(),
            _selectedType.value.toString(),
            page.value ?: 1
        )
    }

    fun search(searchValue: String) {
        _searchValue.value = searchValue
        page.value = 1
        loading()
    }

    fun changeType(option: String) {
        _selectedType.value = option
        page.value = 1
        loading()
    }
}