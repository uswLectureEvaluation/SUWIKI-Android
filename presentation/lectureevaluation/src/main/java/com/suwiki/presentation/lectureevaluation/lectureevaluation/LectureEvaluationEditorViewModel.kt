package com.suwiki.presentation.lectureevaluation.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.common.model.enums.GradeLevel
import com.suwiki.common.model.enums.HomeworkLevel
import com.suwiki.common.model.enums.TeamLevel
import com.suwiki.common.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.domain.lectureevaluation.usecase.lecture.PostLectureEvaluationUseCase
import com.suwiki.domain.lectureevaluation.usecase.lecture.UpdateLectureEvaluationUseCase
import com.suwiki.presentation.common.ui.extension.decodeFromUri
import com.suwiki.presentation.lectureevaluation.navigation.EvaluationEditorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationEditorViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val postLectureEvaluationUseCase: PostLectureEvaluationUseCase,
  val updateLectureEvaluationUseCase: UpdateLectureEvaluationUseCase,
) : ContainerHost<LectureEvaluationEditorState, LectureEvaluationEditorSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationEditorState, LectureEvaluationEditorSideEffect> =
    container(LectureEvaluationEditorState())

  private val myLectureEvaluation = savedStateHandle.get<String>(EvaluationEditorRoute.lectureEvaluation)!!
  private val myLectureEvaluationItem: MyLectureEvaluation = Json.decodeFromUri(myLectureEvaluation)
  private val isEditMode = myLectureEvaluationItem.content.isNotEmpty()

  suspend fun initData() = intent {
    showLoadingScreen()
    with(myLectureEvaluationItem) {
      reduce {
        state.copy(
          selectedSemester = selectedSemester,
          semesterList = lectureInfo.semesterList.toPersistentList(),
        )
      }
      updateHoneyRating(honey)
      updateLearningRating(learning)
      updateSatisfactionRating(satisfaction)
      updateTotalAvg()
      updateMyLectureEvaluationValue(content)
      GradeLevel.entries.forEach {
        if (it.value == difficulty) {
          updateGradeLevel(it)
        }
      }
      HomeworkLevel.entries.forEach {
        if (it.value == homework) {
          updateHomeworkLevel(it)
        }
      }
      TeamLevel.entries.forEach {
        if (it.value == team) {
          updateTeamLevel(it)
        }
      }
    }
    hideLoadingScreen()
  }

  fun postOrUpdateLectureEvaluation() = intent {
    if (state.lectureEvaluation.length < 30) {
      postSideEffect(LectureEvaluationEditorSideEffect.ShowInputMoreTextToast)
      return@intent
    }

    if (state.selectedSemester.isEmpty()) {
      postSideEffect(LectureEvaluationEditorSideEffect.ShowSelectSemesterToast)
      return@intent
    }

    if (isEditMode) {
      updateLectureEvaluation()
    } else {
      postLectureEvaluation()
    }
  }

  private fun postLectureEvaluation() = intent {
    postLectureEvaluationUseCase(
      PostLectureEvaluationUseCase.Param(
        id = myLectureEvaluationItem.id,
        lectureName = myLectureEvaluationItem.lectureInfo.lectureName,
        professor = myLectureEvaluationItem.lectureInfo.professor,
        selectedSemester = state.selectedSemester,
        satisfaction = "%.1f".format(state.satisfactionRating).toFloat(),
        learning = "%.1f".format(state.learningRating).toFloat(),
        honey = "%.1f".format(state.honeyRating).toFloat(),
        team = state.teamLevel!!.value,
        difficulty = state.gradeLevel!!.value,
        homework = state.homeworkLevel!!.value,
        content = state.lectureEvaluation,
      ),
    )
      .onSuccess {
        popBackStack()
      }
      .onFailure {
        postSideEffect(LectureEvaluationEditorSideEffect.HandleException(it))
      }
  }

  private fun updateLectureEvaluation() = intent {
    updateLectureEvaluationUseCase(
      UpdateLectureEvaluationUseCase.Param(
        lectureId = myLectureEvaluationItem.id,
        selectedSemester = state.selectedSemester,
        satisfaction = "%.1f".format(state.satisfactionRating).toFloat(),
        learning = "%.1f".format(state.learningRating).toFloat(),
        honey = "%.1f".format(state.honeyRating).toFloat(),
        team = state.teamLevel!!.value,
        difficulty = state.gradeLevel!!.value,
        homework = state.homeworkLevel!!.value,
        content = state.lectureEvaluation,
      ),
    )
      .onSuccess {
        popBackStack()
      }
      .onFailure {
        postSideEffect(LectureEvaluationEditorSideEffect.HandleException(it))
      }
  }

  fun updateSemester(selectedPosition: Int) = intent {
    reduce { state.copy(selectedSemester = state.semesterList[selectedPosition]) }
    hideSemesterBottomSheet()
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

  @OptIn(OrbitExperimental::class)
  fun updateMyLectureEvaluationValue(lectureEvaluationValue: String) = blockingIntent {
    reduce { state.copy(lectureEvaluation = lectureEvaluationValue) }
  }

  fun updateTotalAvg() = intent {
    reduce { state.copy(totalAvg = (state.honeyRating + state.learningRating + state.satisfactionRating) / 3) }
  }
  fun updateGradeLevel(gradeLevel: GradeLevel) = intent {
    reduce { state.copy(gradeLevel = gradeLevel) }
  }
  fun updateHomeworkLevel(homeworkLevel: HomeworkLevel) = intent {
    reduce { state.copy(homeworkLevel = homeworkLevel) }
  }
  fun updateTeamLevel(teamLevel: TeamLevel) = intent {
    reduce { state.copy(teamLevel = teamLevel) }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(LectureEvaluationEditorSideEffect.PopBackStack) }
}
