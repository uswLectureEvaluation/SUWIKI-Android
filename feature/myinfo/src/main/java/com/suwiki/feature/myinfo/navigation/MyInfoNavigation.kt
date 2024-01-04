package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myreview.MyReviewRoute
import com.suwiki.feature.myinfo.myreview.classreview.MyClassReviewEditRoute
import com.suwiki.feature.myinfo.myreview.testreview.MyTestReviewEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyReview() {
  navigate(MyInfoRoute.myReviewRoute)
}

fun NavController.navigateMyClassReview() {
  navigate(MyInfoRoute.myClassReviewEditRoute)
}

fun NavController.navigateMyTestReview() {
  navigate(MyInfoRoute.myTestReviewEditRoute)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyReview: () -> Unit = {},
  navigateMyClassReviewEdit: () -> Unit = {},
  navigateMyTestReviewEdit: () -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyReview = navigateMyReview,
    )
  }
  composable(route = MyInfoRoute.myReviewRoute) {
    MyReviewRoute(
      padding = padding,
      popBackStack = popBackStack,
      navigateMyClassReview = navigateMyClassReviewEdit,
      navigateMyTestReview = navigateMyTestReviewEdit,
    )
  }
  composable(route = MyInfoRoute.myClassReviewEditRoute) {
    MyClassReviewEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
  composable(route = MyInfoRoute.myTestReviewEditRoute) {
    MyTestReviewEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myReviewRoute = "my-review"
  const val myClassReviewEditRoute = "my-class-review-edit"
  const val myTestReviewEditRoute = "my-test-review-edit"
}
