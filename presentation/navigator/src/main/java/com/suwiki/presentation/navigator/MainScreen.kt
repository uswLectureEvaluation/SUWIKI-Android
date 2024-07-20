package com.suwiki.presentation.navigator

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.suwiki.presentation.common.designsystem.component.dialog.SuwikiDialog
import com.suwiki.presentation.common.designsystem.shadow.bottomNavigationShadow
import com.suwiki.presentation.common.designsystem.theme.GrayDA
import com.suwiki.presentation.common.designsystem.theme.Primary
import com.suwiki.presentation.common.designsystem.theme.White
import com.suwiki.presentation.common.ui.extension.suwikiClickable
import com.suwiki.presentation.common.ui.extension.versionCode
import com.suwiki.presentation.common.ui.util.PLAY_STORE_SITE
import com.suwiki.presentation.lectureevaluation.navigation.lectureEvaluationNavGraph
import com.suwiki.presentation.lectureevaluation.navigation.myEvaluationEditNavGraph
import com.suwiki.presentation.lectureevaluation.navigation.myEvaluationNavGraph
import com.suwiki.presentation.login.navigation.loginNavGraph
import com.suwiki.presentation.myinfo.navigation.myInfoNavGraph
import com.suwiki.presentation.navigator.component.SuwikiToast
import com.suwiki.presentation.notice.navigation.noticeNavGraph
import com.suwiki.presentation.openmajor.navigation.OpenMajorRoute
import com.suwiki.presentation.openmajor.navigation.openMajorNavGraph
import com.suwiki.presentation.signup.navigation.signupNavGraph
import com.suwiki.presentation.timetable.navigation.timetableNavGraph
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun MainScreen(
  modifier: Modifier = Modifier,
  viewModel: MainViewModel = hiltViewModel(),
  navigator: MainNavigator = rememberMainNavigator(),
) {
  val uiState = viewModel.collectAsState().value
  val uriHandler = LocalUriHandler.current
  val context = LocalContext.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MainSideEffect.OpenPlayStoreSite -> uriHandler.openUri(PLAY_STORE_SITE)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.checkUpdateMandatory(context.versionCode)
  }

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
          navigateLogin = {
            navigator.navigateLogin(
              navOptions {
                popUpTo(navigator.startDestination)
              },
            )
          },
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
          argumentName = OpenMajorRoute.ARGUMENT_NAME,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateTimetableEditor = navigator::navigateTimetableEditor,
          navigateTimetableList = navigator::navigateTimetableList,
          navigateOpenLecture = navigator::navigateOpenLecture,
          handleException = viewModel::handleException,
          onShowToast = viewModel::onShowToast,
          navigateOpenMajor = navigator::navigateOpenMajor,
          navigateCellEditor = navigator::navigateCellEditor,
        )

        lectureEvaluationNavGraph(
          padding = innerPadding,
          argumentName = OpenMajorRoute.ARGUMENT_NAME,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateLogin = navigator::navigateLogin,
          navigateSignUp = navigator::navigateSignup,
          navigateLectureEvaluationEditor = navigator::navigateLectureEvaluationEditor,
          navigateExamEvaluationEditor = navigator::navigateExamEvaluationEditor,
          handleException = viewModel::handleException,
          navigateOpenMajor = navigator::navigateOpenMajor,
          onShowToast = viewModel::onShowToast,
          navigateLectureEvaluationDetail = navigator::navigateLectureEvaluationDetail,
        )

        myEvaluationNavGraph(
          popBackStack = navigator::popBackStackIfNotHome,
          navigateLectureEvaluationEditor = navigator::navigateLectureEvaluationEditor,
          navigateExamEvaluationEditor = navigator::navigateExamEvaluationEditor,
          handleException = viewModel::handleException,
        )

        myEvaluationEditNavGraph(
          popBackStack = navigator::popBackStackIfNotHome,
          onShowToast = viewModel::onShowToast,
          handleException = viewModel::handleException,
        )

        myInfoNavGraph(
          padding = innerPadding,
          popBackStack = navigator::popBackStackIfNotHome,
          navigateNotice = navigator::navigateNotice,
          navigateMyEvaluation = navigator::navigateMyEvaluation,
          navigateMyAccount = navigator::navigateMyAccount,
          navigateResetPassword = navigator::navigateResetPassword,
          navigateQuit = navigator::navigateQuit,
          navigateFindPassword = navigator::navigateFindPassword,
          navigateLogin = navigator::navigateLogin,
          navigateMyPoint = navigator::navigateMyPoint,
          navigateBanHistory = navigator::navigateBanHistory,
          handleException = viewModel::handleException,
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
          headerText = stringResource(R.string.dialog_network_header),
          bodyText = stringResource(R.string.dialog_network_body),
          confirmButtonText = stringResource(id = com.suwiki.presentation.common.ui.R.string.word_confirm),
          onDismissRequest = viewModel::hideNetworkErrorDialog,
          onClickConfirm = viewModel::hideNetworkErrorDialog,
        )
      }

      if (uiState.showUpdateMandatoryDialog) {
        SuwikiDialog(
          headerText = stringResource(R.string.dialog_update_mandatory_header),
          bodyText = stringResource(R.string.dialog_update_mandatory_body),
          confirmButtonText = stringResource(id = com.suwiki.presentation.common.ui.R.string.word_confirm),
          onDismissRequest = {},
          onClickConfirm = viewModel::openPlayStoreSite,
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
