package com.suwiki.feature.myinfo.myevaluation.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.DeleteLectureEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.UpdateLectureEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
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

  private var selectedSemester: String = ""
  private var satisfaction: Float = 0f
  private var learning: Float = 0f
  private var honey: Float = 0f
  private var team: Int = 0
  private var difficulty: Int = 0
  private var homework: Int = 0
  private var content: String = ""

  suspend fun setInitData() = intent {
    showLoadingScreen()
    /* TODO 에러 처리 */
    with(myLectureEvaluationItem) {
      getUserInfoUseCase().collect(::setPoint)

      reduce { state.copy(selectedSemester = selectedSemester) }
      reduce { state.copy(semesterList = lectureInfo.semesterList) }

      updateHoneyRating(honey)
      updateLearningRating(learning)
      updateSatisfactionRating(satisfaction)
      updateTotalAvg()
      updateGradeLevel(
        when (difficulty) {
          2 -> GradeLevel.EASY
          1 -> GradeLevel.NORMAL
          0 -> GradeLevel.DIFFICULT
          else -> GradeLevel.EASY
        },
      )
      updateHomeworkLevel(
        when (homework) {
          2 -> HomeworkLevel.MANY
          1 -> HomeworkLevel.NORMAL
          0 -> HomeworkLevel.NONE
          else -> HomeworkLevel.MANY
        },
      )
      updateTeamLevel(
        if (team == 0) {
          TeamLevel.NOT_EXIST
        } else {
          TeamLevel.EXIST
        },
      )
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
          selectedSemester = selectedSemester,
          satisfaction = satisfaction,
          learning = learning,
          honey = honey,
          team = team,
          difficulty = difficulty,
          homework = homework,
          content = content,
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

  fun clickSemesterItem(semester: String) = intent {
    selectedSemester = semester
    reduce { state.copy(selectedSemester = semester) }
    hideSemesterBottomSheet()
  }

  fun updateHoneyRating(honeyRating: Float) = intent {
    honey = honeyRating
    reduce { state.copy(honeyRating = if (honeyRating < 0.5) 0.5F else honeyRating) }
  }

  fun updateLearningRating(learningRating: Float) = intent {
    learning = learningRating
    reduce { state.copy(learningRating = if (learningRating < 0.5) 0.5F else learningRating) }
  }

  fun updateSatisfactionRating(satisfactionRating: Float) = intent {
    satisfaction = satisfactionRating
    reduce { state.copy(satisfactionRating = if (satisfactionRating < 0.5) 0.5F else satisfactionRating) }
  }

  fun updateMyLectureEvaluationValue(lectureEvaluationValue: String) = intent {
    content = lectureEvaluationValue
    reduce { state.copy(lectureEvaluation = lectureEvaluationValue) }
  }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }
  fun updateTotalAvg() = intent {
    reduce { state.copy(totalAvg = (state.honeyRating + state.learningRating + state.satisfactionRating) / 3) }
  }
  fun updateGradeLevel(gradeLevel: GradeLevel) = intent {
    difficulty = gradeLevel.value
    reduce { state.copy(gradeLevel = gradeLevel) }
  }
  fun updateHomeworkLevel(homeworkLevel: HomeworkLevel) = intent {
    homework = homeworkLevel.value
    reduce { state.copy(homeworkLevel = homeworkLevel) }
  }
  fun updateTeamLevel(teamLevel: TeamLevel) = intent {
    team = teamLevel.value
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
