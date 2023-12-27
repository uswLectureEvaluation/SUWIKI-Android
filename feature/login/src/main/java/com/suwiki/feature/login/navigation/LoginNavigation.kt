package com.suwiki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.domain.login.usecase.LoginUseCase
import com.suwiki.feature.login.LoginRoute
import com.suwiki.feature.login.LoginViewModel

fun NavController.navigateLogin() {
  navigate(LoginRoute.route)
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
}

object LoginRoute {
  const val route = "login"
}
