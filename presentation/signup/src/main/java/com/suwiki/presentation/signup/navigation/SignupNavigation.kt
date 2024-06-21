package com.suwiki.presentation.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.suwiki.presentation.SignupComplete.complete.SignupCompleteRoute
import com.suwiki.presentation.signup.signup.SignupRoute

fun NavController.navigateSignup() {
  navigate(SignupRoute.route)
}

fun NavController.navigateSignupComplete() {
  val navOptions = navOptions {
    popUpTo(SignupRoute.route) {
      inclusive = true
    }
  }

  navigate(
    route = SignupRoute.completeRoute,
    navOptions = navOptions,
  )
}

fun NavGraphBuilder.signupNavGraph(
  popBackStack: () -> Unit,
  navigateSignupComplete: () -> Unit,
  navigateLogin: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  composable(route = SignupRoute.route) {
    SignupRoute(
      popBackStack = popBackStack,
      navigateSignupComplete = navigateSignupComplete,
      handleException = handleException,
    )
  }

  composable(route = SignupRoute.completeRoute) {
    SignupCompleteRoute(
      navigateLogin = navigateLogin,
    )
  }
}

object SignupRoute {
  const val route = "signup"
  const val completeRoute = "signup-complete"
}
