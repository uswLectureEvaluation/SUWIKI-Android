package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myaccount.MyAccountRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyAccount() {
  navigate(MyInfoRoute.myAccount)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyAccount: () -> Unit = {},
  navigateChangePassword: () -> Unit = {},
  navigateWithdrawal: () -> Unit = {},
  handleException: (Throwable) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
      navigateMyAccount = navigateMyAccount,
    )
  }
  composable(route = MyInfoRoute.myAccount) {
    MyAccountRoute(
      popBackStack = popBackStack,
      navigateChangePassword = navigateChangePassword,
      navigateWithdrawal = navigateWithdrawal,
      handleException = handleException,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myAccount = "my-account"
}
