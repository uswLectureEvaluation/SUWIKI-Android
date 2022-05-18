package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonRecyclerViewViewModel<T> {
    private val _itemList = MutableLiveData<MutableList<T?>>()
    val itemList: LiveData<MutableList<T?>>
        get() = _itemList

    init {
        loading()
    }

    fun loading() {
        _itemList.value = mutableListOf(null)
    }

    fun deleteLoading() {
        if(_itemList.value?.isEmpty() == true)
            return
        if(_itemList.value?.get(_itemList.value?.size!! - 1) == null) {
            _itemList.value?.removeLast()
            _itemList.value = _itemList.value
        }
    }

    fun changeRecyclerViewData(data: MutableList<T?>) {
        _itemList.value = data
    }
}