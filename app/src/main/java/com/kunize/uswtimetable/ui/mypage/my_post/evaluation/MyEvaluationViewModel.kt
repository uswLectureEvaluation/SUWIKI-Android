package com.kunize.uswtimetable.ui.mypage.my_post.evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val ITEMS_PER_PAGE = 10

class MyEvaluationViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    val items: Flow<PagingData<MyEvaluationDto>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.evaluationPagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun deletePost(id: Long) {
        viewModelScope.launch {
            repository.deleteEvaluation(id)
            repository.evaluationPagingSource().invalidate()
        }
    }

    fun editButtonClickEvent(data: MyEvaluationDto) {
        event(Event.EditEvent(data))
    }

    fun deleteButtonClickEvent(data: MyEvaluationDto) {
        event(Event.DeleteEvent(data))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
