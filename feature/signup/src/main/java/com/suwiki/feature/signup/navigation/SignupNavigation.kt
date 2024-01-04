package com.suwiki.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.signup.SignupRoute

fun NavController.navigateSignup() {
  navigate(SignupRoute.route)
}

fun NavGraphBuilder.signupNavGraph(
  popBackStack: () -> Unit,
  navigateLogin: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  composable(route = SignupRoute.route) {
    SignupRoute(
      popBackStack = popBackStack,
      navigateLogin = navigateLogin,
      handleException = handleException,
    )
  }
}

object SignupRoute {
  const val route = "signup"
}
