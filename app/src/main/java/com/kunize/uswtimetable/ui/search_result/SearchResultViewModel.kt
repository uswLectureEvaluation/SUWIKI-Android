package com.kunize.uswtimetable.ui.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.ui.repository.search_result.SearchResultRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) :
    ViewModel() {
    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationList: LiveData<ArrayList<EvaluationData?>>
        get() = _evaluationList

    private val _selectedType = MutableLiveData<String>()
    private val _page = MutableLiveData<Int>()
    private val _searchValue = MutableLiveData<String>()

    init {
        _page.value = 1
        _searchValue.value = ""
        _evaluationList.value = arrayListOf()
    }

    private fun changeEvaluationData() {
        Log.d("searchTest", "${_searchValue.value}")
        viewModelScope.launch {
            val response = if (_searchValue.value.toString() == "") {
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
            if (response.isSuccessful) {
                deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if(tmpEvaluationData.isNullOrEmpty()) {
                    deleteLoading()
                    Log.d("scrollTest","${tmpEvaluationData!!.size}")
                } else {
                    tmpEvaluationData?.add(null)
                    _evaluationList.value = tmpEvaluationData ?: arrayListOf()
                }
            } else {
                _evaluationList.value = arrayListOf()
            }
        }
    }

    fun search(searchValue: String) {
        _searchValue.value = searchValue
        Log.d("searchTest", "${_searchValue.value}")
        _page.value = 1
        loading()
        changeEvaluationData()
    }

    fun scrollBottomEvent() {
        viewModelScope.launch {
            val response = if (_searchValue.value.toString() == "") {
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
            if (response.isSuccessful) {
                deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if(tmpEvaluationData.isNullOrEmpty()) {
                    deleteLoading()
                    Log.d("scrollTest","${tmpEvaluationData!!.size}")
                }
                else {
                    tmpEvaluationData.add(null)
                    _evaluationList.value!!.addAll(tmpEvaluationData ?: arrayListOf())
                    _evaluationList.value = _evaluationList.value //대입을 해줘야지만 옵저버가 변화를 감지함.
                    _page.value = _page.value?.plus(1)
                    _page.value = _page.value
                }
            } else {
                _evaluationList.value = arrayListOf()
            }
        }
    }

    private fun loading() {
        _evaluationList.value = arrayListOf(null)
    }

    fun changeType(option: String) {
        _selectedType.value = option
        _page.value = 1
        loading()
        changeEvaluationData()
    }

    fun deleteLoading() {
        if (_evaluationList.value?.isEmpty() == true)
            return
        if (_evaluationList.value?.last() == null) {
            _evaluationList.value?.removeLast()
            _evaluationList.value = _evaluationList.value
        }
    }
}