package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myaccount.MyAccountRoute
import com.suwiki.feature.myinfo.mypoint.MyPointRoute
import com.suwiki.feature.myinfo.quit.QuitRoute
import com.suwiki.feature.myinfo.resetpassword.ResetPasswordRoute

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

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyAccount: () -> Unit = {},
  navigateResetPassword: () -> Unit = {},
  navigateQuit: () -> Unit = {},
  navigateFindPassword: () -> Unit = {},
  navigateLogin: () -> Unit = {},
  navigateMyPoint: () -> Unit = {},
  handleException: (Throwable) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
      navigateMyAccount = navigateMyAccount,
      navigateMyPoint = navigateMyPoint,
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
      navigateLogin = navigateLogin,
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.quitRoute) {
    QuitRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
  composable(route = MyInfoRoute.myPointRoute) {
    MyPointRoute(
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
}
