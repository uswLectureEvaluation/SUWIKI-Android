package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

class CommonRecyclerViewViewModel<T> {
    private val _itemList = MutableLiveData<List<T?>>()
    val itemList: LiveData<List<T?>>
        get() = _itemList.map { it.toList() }

    init {
        loading()
    }

    fun loading() {
        _itemList.value = mutableListOf(null)
    }

    fun deleteLoading() {
        if (_itemList.value?.isEmpty() == true) {
            return
        }
        if (_itemList.value?.get(_itemList.value?.size!! - 1) == null) {
            _itemList.value = itemList.value?.dropLast(1)
        }
    }

    fun changeRecyclerViewData(data: List<T?>) {
        _itemList.value = data
    }
}
