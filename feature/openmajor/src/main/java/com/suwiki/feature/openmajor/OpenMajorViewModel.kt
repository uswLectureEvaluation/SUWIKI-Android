package com.suwiki.feature.openmajor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.exception.AuthorizationException
import com.suwiki.domain.openmajor.usecase.GetBookmarkedOpenMajorListUseCase
import com.suwiki.domain.openmajor.usecase.GetOpenMajorListUseCase
import com.suwiki.feature.openmajor.navigation.OpenMajorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OpenMajorViewModel @Inject constructor(
  private val getOpenMajorListUseCase: GetOpenMajorListUseCase,
  private val getBookmarkedOpenMajorListUseCase: GetBookmarkedOpenMajorListUseCase,
  savedStateHandle: SavedStateHandle,
) : ContainerHost<OpenMajorState, OpenMajorSideEffect>, ViewModel() {
  override val container: Container<OpenMajorState, OpenMajorSideEffect> = container(OpenMajorState())

  private var isLoggedIn: Boolean = true
  private val selectedOpenMajor = savedStateHandle[OpenMajorRoute.ARGUMENT_NAME] ?: "전체"
  private val allOpenMajorList = mutableListOf<String>()
  private val bookmarkedOpenMajorList = mutableListOf<String>()

  fun changeBottomShadowVisible(show: Boolean) = intent {
    reduce { state.copy(showBottomShadow = show) }
  }

  fun initData() = intent {
    viewModelScope.launch {
      reduce { state.copy(isLoading = true) }
      getOpenMajor()
      getBookmarkedOpenMajor()
      reduce { state.copy(isLoading = false) }
    }
  }

  private fun getOpenMajor() = intent {
    getOpenMajorListUseCase().onEach {
      allOpenMajorList.clear()
      allOpenMajorList.addAll(it)
      reduceOpenMajorList()
    }.catch {
      Timber.e(it)
      postSideEffect(OpenMajorSideEffect.HandleException(it))
    }.collect()
  }

  private fun reduceOpenMajorList() = intent {
    reduce {
      state.copy(
        filteredAllOpenMajorList = allOpenMajorList.toOpenMajorList(
          bookmarkedOpenMajorList = bookmarkedOpenMajorList,
          selectedOpenMajor = selectedOpenMajor,
        ),
      )
    }
  }

  private suspend fun getBookmarkedOpenMajor() = intent {
    getBookmarkedOpenMajorListUseCase()
      .onSuccess {
        bookmarkedOpenMajorList.addAll(it)
        reduceBookmarkedOpenMajorList()
      }
      .onFailure {
        if (it is AuthorizationException) isLoggedIn = false
        else postSideEffect(OpenMajorSideEffect.HandleException(it))
      }
  }

  private fun reduceBookmarkedOpenMajorList() = intent {
    reduce {
      state.copy(
        filteredBookmarkedOpenMajorList = bookmarkedOpenMajorList.toBookmarkedOpenMajorList(selectedOpenMajor),
      )
    }
  }
}
