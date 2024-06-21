package com.suwiki.presentation.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.presentation.login.findid.FindIdRoute
import com.suwiki.presentation.login.findpassword.FindPasswordRoute
import com.suwiki.presentation.login.login.LoginRoute

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
  navigate(
    route = LoginRoute.route,
    navOptions = navOptions,
  )
}

fun NavController.navigateFindId() {
  navigate(LoginRoute.findIdRoute)
}

fun NavController.navigateFindPassword() {
  navigate(LoginRoute.findPasswordRoute)
}

fun NavGraphBuilder.loginNavGraph(
  popBackStack: () -> Unit,
  navigateFindId: () -> Unit,
  navigateFindPassword: () -> Unit,
  navigateSignup: () -> Unit,
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
}

object LoginRoute {
  const val route = "login"
  const val findIdRoute = "find-id"
  const val findPasswordRoute = "find-password"
}
