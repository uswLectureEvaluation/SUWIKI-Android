package com.suwiki.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.navigateLectureEvaluation
import com.suwiki.feature.login.navigation.navigateFindId
import com.suwiki.feature.login.navigation.navigateFindPassword
import com.suwiki.feature.login.navigation.navigateLogin
import com.suwiki.feature.myinfo.navigation.navigateMyEvaluation
import com.suwiki.feature.myinfo.navigation.navigateMyExamEvaluation
import com.suwiki.feature.myinfo.navigation.navigateMyInfo
import com.suwiki.feature.myinfo.navigation.navigateMyLectureEvaluation
import com.suwiki.feature.notice.navigation.navigateNotice
import com.suwiki.feature.notice.navigation.navigateNoticeDetail
import com.suwiki.feature.openmajor.navigation.navigateOpenMajor
import com.suwiki.feature.signup.navigation.navigateSignup
import com.suwiki.feature.signup.navigation.navigateSignupComplete
import com.suwiki.feature.timetable.navigation.TimetableRoute
import com.suwiki.feature.timetable.navigation.navigateCreateTimetable
import com.suwiki.feature.timetable.navigation.navigateTimetable

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

  fun navigateLogin() {
    navController.navigateLogin()
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

  fun navigateNotice() {
    navController.navigateNotice()
  }

  fun navigateNoticeDetail(noticeId: Long) {
    navController.navigateNoticeDetail(noticeId)
  }

  fun navigateMyEvaluation() {
    navController.navigateMyEvaluation()
  }

  fun navigateMyLectureEvaluationEdit(lectureEvaluation: String) {
    navController.navigateMyLectureEvaluation(lectureEvaluation)
  }

  fun navigateMyExamEvaluationEdit(examEvaluation: String) {
    navController.navigateMyExamEvaluation(examEvaluation)
  }

  fun navigateOpenMajor(selectedOpenMajor: String) {
    navController.navigateOpenMajor(selectedOpenMajor)
  }

  fun navigateCreateTimetable() {
    navController.navigateCreateTimetable()
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
