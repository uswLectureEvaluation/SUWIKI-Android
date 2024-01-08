package com.suwiki.feature.myinfo.myreview.classreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

const val SHOW_TOAST_LENGTH = 2000L

@HiltViewModel
class MyClassReviewEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ContainerHost<MyClassReviewEditState, MyClassReviewEditSideEffect>, ViewModel() {
  override val container: Container<MyClassReviewEditState, MyClassReviewEditSideEffect> =
    container(MyClassReviewEditState())

  private val point: Int = savedStateHandle.get<String>(MyInfoRoute.myPoint)!!.toInt()
  private val mutex = Mutex()

  fun getSemester(semester: String) = intent {
    reduce { state.copy(selectedSemester = semester) }
  }

  fun updateHoneyRating(honeyRating: Float) = intent {
    reduce { state.copy(honeyRating = if (honeyRating < 0.5) 0.5F else honeyRating) }
  }

  fun updateLearningRating(learningRating: Float) = intent {
    reduce { state.copy(learningRating = if (learningRating < 0.5) 0.5F else learningRating) }
  }

  fun updateSatisfactionRating(satisfactionRating: Float) = intent {
    reduce { state.copy(satisfactionRating = if (satisfactionRating < 0.5) 0.5F else satisfactionRating) }
  }

  fun updateMyClassReviewValue(classReviewValue: String) = intent {
    reduce { state.copy(classReview = classReviewValue) }
  }

  fun setPoint() = intent { reduce { state.copy(point = point) } }
  fun setDifficultyGenerous() = intent { reduce { state.copy(difficulty = 2) } }
  fun setDifficultyNormal() = intent { reduce { state.copy(difficulty = 1) } }
  fun setDifficultyPicky() = intent { reduce { state.copy(difficulty = 0) } }
  fun setHomeworkNone() = intent { reduce { state.copy(homework = 0) } }
  fun setHomeworkNormal() = intent { reduce { state.copy(homework = 1) } }
  fun setHomeworkMuch() = intent { reduce { state.copy(homework = 2) } }
  fun setTeamNone() = intent { reduce { state.copy(team = 0) } }
  fun setTeamExist() = intent { reduce { state.copy(team = 1) } }

  fun showMyClassReviewDeleteDialog() = intent { reduce { state.copy(showDeleteClassReviewDialog = true) } }
  fun hideMyClassReviewDeleteDialog() = intent { reduce { state.copy(showDeleteClassReviewDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun showMyClassReviewToast(msg: String) = intent {
    hideMyClassReviewDeleteDialog()
    mutex.withLock {
      reduce { state.copy(showDeleteClassReviewToastMessage = msg, showDeleteClassReviewToastVisible = true) }
      delay(SHOW_TOAST_LENGTH)
      reduce { state.copy(showDeleteClassReviewToastVisible = false) }
      popBackStack()
    }
  }

  fun popBackStack() = intent { postSideEffect(MyClassReviewEditSideEffect.PopBackStack) }
}
