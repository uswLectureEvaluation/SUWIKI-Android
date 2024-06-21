package com.suwiki.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.suwiki.presentation.lectureevaluation.editor.navigation.navigateExamEvaluationEditor
import com.suwiki.presentation.lectureevaluation.editor.navigation.navigateLectureEvaluationEditor
import com.suwiki.presentation.lectureevaluation.my.navigation.navigateMyEvaluation
import com.suwiki.presentation.lectureevaluation.viewerreporter.navigation.navigateLectureEvaluation
import com.suwiki.presentation.lectureevaluation.viewerreporter.navigation.navigateLectureEvaluationDetail
import com.suwiki.presentation.login.navigation.navigateFindId
import com.suwiki.presentation.login.navigation.navigateFindPassword
import com.suwiki.presentation.login.navigation.navigateLogin
import com.suwiki.presentation.myinfo.navigation.navigateBanHistory
import com.suwiki.presentation.myinfo.navigation.navigateMyAccount
import com.suwiki.presentation.myinfo.navigation.navigateMyInfo
import com.suwiki.presentation.myinfo.navigation.navigateMyPoint
import com.suwiki.presentation.myinfo.navigation.navigateQuit
import com.suwiki.presentation.myinfo.navigation.navigateResetPassword
import com.suwiki.presentation.notice.navigation.navigateNotice
import com.suwiki.presentation.notice.navigation.navigateNoticeDetail
import com.suwiki.presentation.openmajor.navigation.navigateOpenMajor
import com.suwiki.presentation.signup.navigation.navigateSignup
import com.suwiki.presentation.signup.navigation.navigateSignupComplete
import com.suwiki.presentation.timetable.navigation.TimetableRoute
import com.suwiki.presentation.timetable.navigation.argument.CellEditorArgument
import com.suwiki.presentation.timetable.navigation.argument.TimetableEditorArgument
import com.suwiki.presentation.timetable.navigation.navigateCellEditor
import com.suwiki.presentation.timetable.navigation.navigateOpenLecture
import com.suwiki.presentation.timetable.navigation.navigateTimetable
import com.suwiki.presentation.timetable.navigation.navigateTimetableEditor
import com.suwiki.presentation.timetable.navigation.navigateTimetableList

internal class MainNavigator(
  val navController: NavHostController,
) {
  private val currentDestination: NavDestination?
    @Composable get() = navController
      .currentBackStackEntryAsState().value?.destination

  val startDestination = MainTab.TIMETABLE.route

  val currentTab: MainTab?
    @Composable get() = currentDestination
      ?.route
      ?.let(MainTab::find)

  fun navigate(tab: MainTab) {
    val navOptions = navOptions {
      popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
      }
      launchSingleTop = true
      restoreState = true
    }

    when (tab) {
      MainTab.TIMETABLE -> navController.navigateTimetable(navOptions)
      MainTab.LECTURE_EVALUATION -> navController.navigateLectureEvaluation(navOptions)
      MainTab.MY_INFO -> navController.navigateMyInfo(navOptions)
    }
  }

  fun navigateLogin(navOptions: NavOptions? = null) {
    navController.navigateLogin(navOptions)
  }

  fun navigateFindId() {
    navController.navigateFindId()
  }

  fun navigateFindPassword() {
    navController.navigateFindPassword()
  }

  fun navigateSignup() {
    navController.navigateSignup()
  }

  fun navigateSignupComplete() {
    navController.navigateSignupComplete()
  }

  fun navigateLectureEvaluationDetail(id: String) {
    navController.navigateLectureEvaluationDetail(id)
  }

  fun navigateMyAccount() {
    navController.navigateMyAccount()
  }

  fun navigateResetPassword() {
    navController.navigateResetPassword()
  }

  fun navigateQuit() {
    navController.navigateQuit()
  }

  fun navigateMyPoint() {
    navController.navigateMyPoint()
  }

  fun navigateBanHistory() {
    navController.navigateBanHistory()
  }

  fun navigateNotice() {
    navController.navigateNotice()
  }

  fun navigateNoticeDetail(noticeId: Long) {
    navController.navigateNoticeDetail(noticeId)
  }

  fun navigateMyEvaluation() {
    navController.navigateMyEvaluation()
  }

  fun navigateLectureEvaluationEditor(lectureEvaluation: String) {
    navController.navigateLectureEvaluationEditor(lectureEvaluation)
  }

  fun navigateExamEvaluationEditor(examEvaluation: String) {
    navController.navigateExamEvaluationEditor(examEvaluation)
  }

  fun navigateOpenMajor(selectedOpenMajor: String) {
    navController.navigateOpenMajor(selectedOpenMajor)
  }

  fun navigateCellEditor(argument: CellEditorArgument) {
    navController.navigateCellEditor(argument)
  }

  fun navigateTimetableEditor(argument: TimetableEditorArgument = TimetableEditorArgument()) {
    navController.navigateTimetableEditor(argument)
  }

  fun navigateTimetableList() {
    navController.navigateTimetableList()
  }

  fun navigateOpenLecture() {
    navController.navigateOpenLecture()
  }

  fun popBackStackIfNotHome() {
    if (!isSameCurrentDestination(TimetableRoute.route)) {
      navController.popBackStack()
    }
  }

  private fun isSameCurrentDestination(route: String) =
    navController.currentDestination?.route == route

  @Composable
  fun shouldShowBottomBar(): Boolean {
    val currentRoute = currentDestination?.route ?: return false
    return currentRoute in MainTab
  }
}

@Composable
internal fun rememberMainNavigator(
  navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
  MainNavigator(navController)
}
