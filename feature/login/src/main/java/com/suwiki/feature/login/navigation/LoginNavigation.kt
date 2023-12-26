package com.suwiki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.login.LoginRoute

fun NavController.navigateLogin() {
  navigate(LoginRoute.route)
}

fun NavGraphBuilder.loginNavGraph() {
  composable(route = LoginRoute.route) {
    LoginRoute()
  }
}

object LoginRoute {
  const val route = "login"
}
