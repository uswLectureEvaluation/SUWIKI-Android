package com.mangbaam.presentation.common

class CommonRecyclerViewState<T> {
    private val _items = mutableListOf<T?>()
    val items: List<T?>
        get() = _items.toList()

    var loading: Boolean = false
        set(value) = run {
            when (value) {
                true -> {
                    if (items.isEmpty() || items.last() != null) {
                        _items.add(null)
                    }
                }

                false -> {
                    if (items.isNotEmpty() && items.last() == null) {
                        _items.removeLast()
                    }
                }
            }
            field = value
        }

    fun setItems(items: List<T?>) {
        _items.removeAll { true }
        _items.addAll(items)
    }

    fun addItems(items: List<T?>) {
        _items.addAll(items)
    }
}
