package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.ui.repository.search_result.SearchResultRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) :
    ViewModel() {
    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationList: LiveData<ArrayList<EvaluationData?>>
        get() = _evaluationList

    private val _selectedType = MutableLiveData<String>()
    private val _page = MutableLiveData<Int>()
    private val _searchValue = MutableLiveData<String>()

    private val delayTime = 200L

    init {
        _page.value = 1
        _searchValue.value = ""
        _evaluationList.value = arrayListOf(null)
    }

    fun scrollBottomEvent() {
        viewModelScope.launch {
            val response = getResponse()
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                deleteLoading()
                if(!tmpEvaluationData.isNullOrEmpty()) {
                    if(tmpEvaluationData.size == 10)
                        tmpEvaluationData.add(null)
                    if(_page.value == 1) _evaluationList.value = tmpEvaluationData!!
                    else {
                        _evaluationList.value!!.addAll(tmpEvaluationData)
                        _evaluationList.value = _evaluationList.value //대입을 해줘야지만 옵저버가 변화를 감지함.
                    }
                    nextPage()
                }
            } else {
                _evaluationList.value = arrayListOf()
            }
        }
    }

    private fun nextPage() {
        _page.value = _page.value?.plus(1)
        _page.value = _page.value
    }

    private suspend fun getResponse() = if (_searchValue.value.toString().isBlank()) {
        searchResultRepository.getLectureMainList(
            _selectedType.value.toString(),
            _page.value ?: 1
        )
    } else {
        searchResultRepository.getSearchResultList(
            _searchValue.value.toString(),
            _selectedType.value.toString(),
            _page.value ?: 1
        )
    }

    fun search(searchValue: String) {
        _searchValue.value = searchValue
        _page.value = 1
        loading()
    }

    private fun loading() {
        _evaluationList.value = arrayListOf(null)
    }

    fun changeType(option: String) {
        _selectedType.value = option
        _page.value = 1
        loading()
    }

    private fun deleteLoading() {
        if (_evaluationList.value?.isEmpty() == true)
            return
        if (_evaluationList.value?.last() == null) {
            _evaluationList.value?.removeLast()
            _evaluationList.value = _evaluationList.value
        }
    }
}