package com.suwiki.feature.lectureevaluation.editor.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.core.ui.extension.decodeFromUri
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.DeleteLectureEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.UpdateLectureEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.lectureevaluation.editor.navigation.MyEvaluationEditRoute
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

const val MINIMUM_DELETE_POINT = 30

@HiltViewModel
class MyLectureEvaluationEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val getUserInfoUseCase: GetUserInfoUseCase,
  val deleteLectureEvaluationUseCase: DeleteLectureEvaluationUseCase,
  val updateLectureEvaluationUseCase: UpdateLectureEvaluationUseCase,
) : ContainerHost<MyLectureEvaluationEditState, MyLectureEvaluationEditSideEffect>, ViewModel() {
  override val container: Container<MyLectureEvaluationEditState, MyLectureEvaluationEditSideEffect> =
    container(MyLectureEvaluationEditState())

  private val myLectureEvaluation = savedStateHandle.get<String>(MyEvaluationEditRoute.myLectureEvaluation)!!
  private val myLectureEvaluationItem: MyLectureEvaluation = Json.decodeFromUri(myLectureEvaluation)

  suspend fun initData() = intent {
    showLoadingScreen()
    with(myLectureEvaluationItem) {
      getUserInfoUseCase().collect(::getPoint).runCatching {}
        .onFailure {
          postSideEffect(MyLectureEvaluationEditSideEffect.HandleException(it))
        }
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

  private fun getPoint(user: User) = intent { reduce { state.copy(point = user.point) } }

  fun updateLectureEvaluation() = intent {
    updateLectureEvaluationUseCase(
      UpdateLectureEvaluationUseCase.Param(
        lectureId = myLectureEvaluationItem.id,
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
        showReviseToast()
        popBackStack()
      }
      .onFailure {
        postSideEffect(MyLectureEvaluationEditSideEffect.HandleException(it))
      }
  }

  fun deleteLectureEvaluation() = intent {
    deleteLectureEvaluationUseCase(myLectureEvaluationItem.id)
      .onSuccess {
        showDeleteToast()
        popBackStack()
      }
      .onFailure {
        postSideEffect(MyLectureEvaluationEditSideEffect.HandleException(it))
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

  fun showDeleteOrLackPointDialog() = intent {
    if (state.point > MINIMUM_DELETE_POINT) {
      showLectureEvaluationDeleteDialog()
    } else {
      showLackPointDialog()
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  private fun showLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = true) } }
  fun hideLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = false) } }
  private fun showLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = true) } }
  fun hideLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationReviseToast) }
}
