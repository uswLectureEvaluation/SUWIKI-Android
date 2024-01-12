package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
}
