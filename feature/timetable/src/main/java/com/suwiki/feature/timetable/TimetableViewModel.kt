package com.suwiki.feature.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.timetable.usecase.GetAllTimetableUseCase
import com.suwiki.domain.timetable.usecase.GetTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
  private val getTimetableUseCase: GetTimetableUseCase,
  private val getAllTimetableUseCase: GetAllTimetableUseCase,
) : ViewModel() {

  init {
      viewModelScope.launch {
        getAllTimetableUseCase()
          .onSuccess { list ->
            Timber.tag("테스트").d("시간표 목록 가져오기 성공! $list")
            list.forEach { time ->
              getTimetableUseCase(time.createTime)
                .onSuccess {
                  Timber.tag("테스트").d("개별 시간표 성공 $it")
                }
                .onFailure {
                  Timber.tag("테스트").d("개별 시간표 실패 $it")
                }
            }

          }
          .onFailure {
            Timber.tag("테스트").d("시간표 목록 가져오기 실패! $it")

          }
      }
  }

}
