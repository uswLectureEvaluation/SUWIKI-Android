package com.suwiki.feature.myinfo.myreview.classreview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyClassReviewEditViewModel @Inject constructor() : ContainerHost<MyClassReviewEditState, MyClassReviewEditSideEffect>, ViewModel() {
  override val container: Container<MyClassReviewEditState, MyClassReviewEditSideEffect> = container(MyClassReviewEditState())

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

  fun popBackStack() = intent { postSideEffect(MyClassReviewEditSideEffect.PopBackStack) }
}
