package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.ItemType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val ITEMS_PER_PAGE = 10

class MyEvaluationViewModel(private val repository: MyPostRepository) : ViewModel() {
    val loading = MutableLiveData(true)

    private val _eventClicked = MutableLiveData<Event<Pair<ItemType, MyEvaluationDto>>>()
    val eventClicked: LiveData<Event<Pair<ItemType, MyEvaluationDto>>> get() = _eventClicked

    init {
    }

    val items: Flow<PagingData<MyEvaluationDto>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.evaluationPagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun itemClicked(type: ItemType, data: MyEvaluationDto) {
        _eventClicked.value = Event(type to data)
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            repository.deleteEvaluation(id)
        }
    }
}