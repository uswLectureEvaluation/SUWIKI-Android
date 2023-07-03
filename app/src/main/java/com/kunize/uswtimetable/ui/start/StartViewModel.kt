package com.kunize.uswtimetable.ui.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.suwiki.data.repository.VersionRepositoryImpl.Companion.DEFAULT_APP_VERSION
import com.suwiki.domain.model.OpenMajor
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.OpenMajorRepository
import com.suwiki.domain.repository.SettingRepository
import com.suwiki.domain.repository.TimetableRepository
import com.suwiki.domain.repository.VersionRepository
import com.suwiki.domain.usecase.LoginUsecase
import com.suwiki.domain.usecase.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val openMajorRepository: OpenMajorRepository,
    private val versionRepository: VersionRepository,
    private val timetableRepository: TimetableRepository,
    private val settingRepository: SettingRepository,
    private val loginUsecase: LoginUsecase,
    private val logoutUsecase: LogoutUsecase,
) : ViewModel() {

    private val remoteAppVersionFlow: StateFlow<Long> =
        versionRepository.remoteAppVersion.transform {
            emit(it.getOrNull() ?: DEFAULT_APP_VERSION)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DEFAULT_APP_VERSION)

    private val _startState = MutableStateFlow(StartState())
    val startState = _startState.asStateFlow()
    val state: StartState
        get() = startState.value

    private val _event = MutableSharedFlow<StartEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            launch {
                versionRepository.remoteAppVersion.collect {
                    when (it) {
                        is Result.Success -> {
                            reduce {
                                state.copy(
                                    progressText = R.string.start_version_check_complete,
                                    progressPercent = 25,
                                )
                            }
                            if (it.data > versionRepository.appVersion.toLong()) {
                                Log.d("firebase", "업데이트 실행 ${versionRepository.appVersion}")
                                reduce {
                                    state.copy(progressText = R.string.start_loading_timetable)
                                }

                                // 새 시간표 로드
                                val newTimetable = timetableRepository.loadRemoteTimetable()
                                reduce {
                                    state.copy(progressText = R.string.start_storing_timetable)
                                }

                                // 새 시간표로 대체
                                timetableRepository.deleteAllLocalTimetable()
                                newTimetable.forEachIndexed { i, timetableData ->
                                    timetableRepository.insert(timetableData)
                                    reduce {
                                        state.copy(
                                            progressPercent =
                                            25 + ((i.toDouble() / newTimetable.size) * 70).toInt(),
                                        )
                                    }
                                }
                                // TODO 앱 버전 업데이트
                                versionRepository.setAppVersion(remoteAppVersionFlow.value.toString())
                            }
                            updateOpenMajorList(versionRepository.openMajorVersion)
                            reduce { state.copy(progressPercent = 100) }
                            event(StartEvent.GotoMain)
                        }

                        is Result.Failure -> {
                            reduce {
                                state.copy(
                                    progressText = R.string.start_version_check_fail,
                                    progressPercent = 100,
                                )
                            }
                            event(StartEvent.GotoMain)
                        }
                    }
                }
            }
            launch {
                settingRepository.isRememberLogin.collect { remember ->
                    if (remember) loginUsecase() else logoutUsecase()
                }
            }
        }
    }

    private suspend fun updateOpenMajorList(openMajorVersion: Float) {
        when (val majorVersionResponse = openMajorRepository.getOpenMajorVersion()) {
            is Result.Success -> {
                if (majorVersionResponse.data.version <= openMajorVersion) return
                val majorListResponse = openMajorRepository.getOpenMajorList().getOrNull()
                val data: List<OpenMajor> = mutableListOf<OpenMajor>().apply {
                    add(OpenMajor(0, "전체"))
                    addAll(majorListResponse?.map { OpenMajor(0, it) }?.toList() ?: emptyList())
                }
                openMajorRepository.deleteAllOpenMajors()
                openMajorRepository.saveAllOpenMajors(data)
                versionRepository.setOpenMajorVersion(majorVersionResponse.data.version)
            }

            is Result.Failure -> {}
        }
    }

    private suspend fun reduce(reducer: StartState.() -> StartState) {
        withContext(SINGLE_THREAD) {
            _startState.value = state.reducer()
        }
    }

    private fun event(event: StartEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val SINGLE_THREAD = newSingleThreadContext("single thread")
    }
}
