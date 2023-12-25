package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoScreen

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
) {
  composable(route = MyInfoRoute.route) {
    MyInfoScreen(padding)
  }
}

object MyInfoRoute {
  const val route = "my-info"
}
