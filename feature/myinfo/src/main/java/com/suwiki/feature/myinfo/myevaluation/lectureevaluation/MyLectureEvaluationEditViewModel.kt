package com.suwiki.feature.myinfo.myevaluation.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.DeleteLectureEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.UpdateLectureEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyLectureEvaluationEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val getUserInfoUseCase: GetUserInfoUseCase,
  val deleteLectureEvaluationUseCase: DeleteLectureEvaluationUseCase,
  val updateLectureEvaluationUseCase: UpdateLectureEvaluationUseCase,
) : ContainerHost<MyLectureEvaluationEditState, MyLectureEvaluationEditSideEffect>, ViewModel() {
  override val container: Container<MyLectureEvaluationEditState, MyLectureEvaluationEditSideEffect> =
    container(MyLectureEvaluationEditState())

  private val myLectureEvaluation = savedStateHandle.get<String>(MyInfoRoute.myLectureEvaluation)!!
  private val myLectureEvaluationItem: MyLectureEvaluation = Json.decodeFromString(myLectureEvaluation)

  suspend fun setInitData() = intent {
    showLoadingScreen()
    with(myLectureEvaluationItem) {
      getUserInfoUseCase().collect(::setPoint)

      reduce { state.copy(selectedSemester = selectedSemester) }
      reduce { state.copy(semesterList = lectureInfo.semesterList.toPersistentList()) }

      updateHoneyRating(honey)
      updateLearningRating(learning)
      updateSatisfactionRating(satisfaction)
      updateTotalAvg()
      updateGradeLevel(difficulty)
      updateHomeworkLevel(homework)
      updateTeamLevel(team)
      updateMyLectureEvaluationValue(content)
      hideLoadingScreen()
    }
  }

  fun clickReviseButton() = intent {
    viewModelScope.launch {
      updateLectureEvaluationUseCase(
        UpdateLectureEvaluationUseCase.Param(
          lectureId = myLectureEvaluationItem.id,
          professor = myLectureEvaluationItem.lectureInfo.professor,
          selectedSemester = state.selectedSemester,
          satisfaction = "%.1f".format(state.satisfactionRating).toFloat(),
          learning = "%.1f".format(state.learningRating).toFloat(),
          honey = "%.1f".format(state.honeyRating).toFloat(),
          team = state.teamLevel,
          difficulty = state.gradeLevel,
          homework = state.homeworkLevel,
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
  }

  fun clickDeleteButton() = intent {
    viewModelScope.launch {
      deleteLectureEvaluationUseCase(myLectureEvaluationItem.id)
        .onSuccess {
          showDeleteToast()
          popBackStack()
        }
        .onFailure {
          postSideEffect(MyLectureEvaluationEditSideEffect.HandleException(it))
        }
    }
  }

  fun clickSemesterItem(selectedPosition: Int) = intent {
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

  fun updateMyLectureEvaluationValue(lectureEvaluationValue: String) = intent {
    reduce { state.copy(lectureEvaluation = lectureEvaluationValue) }
  }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }
  fun updateTotalAvg() = intent {
    reduce { state.copy(totalAvg = (state.honeyRating + state.learningRating + state.satisfactionRating) / 3) }
  }
  fun updateGradeLevel(gradeLevel: Int) = intent {
    reduce { state.copy(gradeLevel = gradeLevel) }
  }
  fun updateHomeworkLevel(homeworkLevel: Int) = intent {
    reduce { state.copy(homeworkLevel = homeworkLevel) }
  }
  fun updateTeamLevel(teamLevel: Int) = intent {
    reduce { state.copy(teamLevel = teamLevel) }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showMyLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = true) } }
  fun hideMyLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationReviseToast) }
}
