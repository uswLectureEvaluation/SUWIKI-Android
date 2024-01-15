package com.suwiki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.login.findid.FindIdRoute
import com.suwiki.feature.login.findpassword.FindPasswordRoute
import com.suwiki.feature.login.login.LoginRoute
import com.suwiki.feature.login.resetpassword.ResetPasswordRoute

fun NavController.navigateLogin() {
  navigate(LoginRoute.route)
}

fun NavController.navigateFindId() {
  navigate(LoginRoute.findIdRoute)
}

fun NavController.navigateFindPassword() {
  navigate(LoginRoute.findPasswordRoute)
}

fun NavController.navigateResetPassword() {
  navigate(LoginRoute.resetPasswordRoute)
}

fun NavGraphBuilder.loginNavGraph(
  popBackStack: () -> Unit,
  navigateFindId: () -> Unit,
  navigateFindPassword: () -> Unit,
  navigateSignup: () -> Unit,
  navigateLogin: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  composable(route = LoginRoute.route) {
    LoginRoute(
      popBackStack = popBackStack,
      navigateFindId = navigateFindId,
      navigateFindPassword = navigateFindPassword,
      navigateSignup = navigateSignup,
      handleException = handleException,
    )
  }

  composable(route = LoginRoute.findIdRoute) {
    FindIdRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }

  composable(route = LoginRoute.findPasswordRoute) {
    FindPasswordRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }

  composable(route = LoginRoute.resetPasswordRoute) {
    ResetPasswordRoute(
      popBackStack = popBackStack,
      navigateFindPassword = navigateFindPassword,
      navigateLogin = navigateLogin,
      handleException = handleException,
    )
  }
}

object LoginRoute {
  const val route = "login"
  const val findIdRoute = "find-id"
  const val findPasswordRoute = "find-password"
  const val resetPasswordRoute = "reset-password"
}
