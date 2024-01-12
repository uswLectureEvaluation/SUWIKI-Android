package com.suwiki.feature.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.toast.SuwikiToast
import com.suwiki.core.designsystem.shadow.bottomNavigationShadow
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.lectureEvaluationNavGraph
import com.suwiki.feature.login.R
import com.suwiki.feature.login.navigation.loginNavGraph
import com.suwiki.feature.myinfo.navigation.myInfoNavGraph
import com.suwiki.feature.notice.navigation.noticeNavGraph
import com.suwiki.feature.openmajor.navigation.OpenMajorRoute
import com.suwiki.feature.openmajor.navigation.openMajorNavGraph
import com.suwiki.feature.signup.navigation.signupNavGraph
import com.suwiki.feature.timetable.navigation.timetableNavGraph
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun MainScreen(
  modifier: Modifier = Modifier,
  viewModel: MainViewModel = hiltViewModel(),
  navigator: MainNavigator = rememberMainNavigator(),
) {
  val uiState = viewModel.collectAsState().value

  Scaffold(
    modifier = modifier,
    content = { innerPadding ->
      NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
      ) {
        loginNavGraph(
          popBackStack = navigator::popBackStackIfNotHome,
          navigateFindId = navigator::navigateFindId,
          navigateFindPassword = navigator::navigateFindPassword,
          navigateSignup = navigator::navigateSignup,
          handleException = viewModel::handleException,
        )

        signupNavGraph(
          popBackStack = navigator::popBackStackIfNotHome,
          handleException = viewModel::handleException,
          navigateSignupComplete = navigator::navigateSignupComplete,
          navigateLogin = navigator::navigateLogin,
        )

        openMajorNavGraph(
          popBackStack = navigator::popBackStackIfNotHome,
          popBackStackWithArgument = { openMajor ->
            navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
              OpenMajorRoute.ARGUMENT_NAME,
              openMajor,
            )
            navigator.popBackStackIfNotHome()
          },
          handleException = viewModel::handleException,
          onShowToast = viewModel::onShowToast,
        )

        timetableNavGraph(
          padding = innerPadding,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateCreateTimetable = navigator::navigateCreateTimetable,
          handleException = viewModel::handleException,
          onShowToast = viewModel::onShowToast,
        )

        lectureEvaluationNavGraph(
          padding = innerPadding,
          argumentName = OpenMajorRoute.ARGUMENT_NAME,
          navigateLogin = navigator::navigateLogin,
          navigateSignUp = navigator::navigateSignup,
          handleException = viewModel::handleException,
          navigateOpenMajor = navigator::navigateOpenMajor,
        )

        myInfoNavGraph(
          padding = innerPadding,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateNotice = navigator::navigateNotice,
          navigateMyEvaluation = navigator::navigateMyEvaluation,
          navigateMyLectureEvaluationEdit = navigator::navigateMyLectureEvaluationEdit,
          navigateMyExamEvaluationEdit = navigator::navigateMyExamEvaluationEdit,
          onShowToast = viewModel::onShowToast,
        )

        noticeNavGraph(
          padding = innerPadding,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateNoticeDetail = navigator::navigateNoticeDetail,
          handleException = viewModel::handleException,
        )
      }

      if (uiState.showNetworkErrorDialog) {
        SuwikiDialog(
          headerText = stringResource(com.suwiki.feature.navigator.R.string.dialog_network_header),
          bodyText = stringResource(com.suwiki.feature.navigator.R.string.dialog_network_body),
          confirmButtonText = stringResource(id = R.string.word_confirm),
          onDismissRequest = viewModel::hideNetworkErrorDialog,
          onClickConfirm = viewModel::hideNetworkErrorDialog,
        )
      }

      SuwikiToast(
        visible = uiState.toastVisible,
        message = uiState.toastMessage,
      )
    },
    bottomBar = {
      MainBottomBar(
        visible = navigator.shouldShowBottomBar(),
        tabs = MainTab.entries.toImmutableList(),
        currentTab = navigator.currentTab,
        onTabSelected = navigator::navigate,
      )
    },
  )
}

@Composable
private fun MainBottomBar(
  visible: Boolean,
  tabs: ImmutableList<MainTab>,
  currentTab: MainTab?,
  onTabSelected: (MainTab) -> Unit,
) {
  AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + slideIn { IntOffset(0, it.height) },
    exit = fadeOut() + slideOut { IntOffset(0, it.height) },
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .bottomNavigationShadow()
        .background(
          color = White,
          shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        ),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      tabs.forEach { tab ->
        MainBottomBarItem(
          tab = tab,
          selected = tab == currentTab,
          onClick = { onTabSelected(tab) },
        )
      }
    }
  }
}

@Composable
private fun RowScope.MainBottomBarItem(
  tab: MainTab,
  selected: Boolean,
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .weight(1f)
      .fillMaxHeight()
      .suwikiClickable(
        rippleEnabled = false,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      painter = painterResource(tab.iconResId),
      contentDescription = tab.contentDescription,
      tint = if (selected) {
        Primary
      } else {
        GrayDA
      },
      modifier = Modifier.size(24.dp),
    )
  }
}
