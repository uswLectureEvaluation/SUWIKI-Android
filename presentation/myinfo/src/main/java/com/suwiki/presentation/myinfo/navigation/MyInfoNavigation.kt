package com.suwiki.presentation.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.suwiki.presentation.myinfo.MyInfoRoute
import com.suwiki.presentation.myinfo.banhistory.BanHistoryRoute
import com.suwiki.presentation.myinfo.myaccount.MyAccountRoute
import com.suwiki.presentation.myinfo.mypoint.MyPointRoute
import com.suwiki.presentation.myinfo.quit.QuitRoute
import com.suwiki.presentation.myinfo.resetpassword.ResetPasswordRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyAccount() {
  navigate(MyInfoRoute.myAccountRoute)
}

fun NavController.navigateResetPassword() {
  navigate(MyInfoRoute.resetPasswordRoute)
}

fun NavController.navigateQuit() {
  navigate(MyInfoRoute.quitRoute)
}

fun NavController.navigateMyPoint() {
  navigate(MyInfoRoute.myPointRoute)
}

fun NavController.navigateBanHistory() {
  navigate(MyInfoRoute.banHistoryRoute)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyAccount: () -> Unit = {},
  navigateResetPassword: () -> Unit = {},
  navigateQuit: () -> Unit = {},
  navigateFindPassword: () -> Unit = {},
  navigateMyPoint: () -> Unit = {},
  navigateBanHistory: () -> Unit = {},
  navigateLogin: (NavOptions?) -> Unit = {},
  handleException: (Throwable) -> Unit = {},
  onShowToast: (String) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
      navigateMyAccount = navigateMyAccount,
      navigateMyPoint = navigateMyPoint,
      navigateBanHistory = navigateBanHistory,
      navigateLogin = {
        navigateLogin(null)
      },
      onShowToast = onShowToast,
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.myAccountRoute) {
    MyAccountRoute(
      popBackStack = popBackStack,
      navigateResetPassword = navigateResetPassword,
      navigateQuit = navigateQuit,
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.resetPasswordRoute) {
    ResetPasswordRoute(
      popBackStack = popBackStack,
      navigateFindPassword = navigateFindPassword,
      navigateLogin = {
        navigateLogin(
          navOptions {
            popUpTo(MyInfoRoute.route)
          },
        )
      },
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.quitRoute) {
    QuitRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }
  composable(route = MyInfoRoute.myPointRoute) {
    MyPointRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.banHistoryRoute) {
    BanHistoryRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myAccountRoute = "my-account"
  const val resetPasswordRoute = "reset-password"
  const val quitRoute = "quit"
  const val myPointRoute = "my-point"
  const val banHistoryRoute = "ban-history"
}
