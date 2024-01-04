package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myreview.MyReviewRoute
import com.suwiki.feature.myinfo.myreview.classreview.MyClassReviewEditRoute
import com.suwiki.feature.myinfo.myreview.testreview.MyTestReviewEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyReview(point: Int) {
  navigate(MyInfoRoute.myReviewRoute(point.toString()))
}

fun NavController.navigateMyClassReview(point: Int) {
  navigate(MyInfoRoute.myClassReviewEditRoute(point.toString()))
}

fun NavController.navigateMyTestReview(point: Int) {
  navigate(MyInfoRoute.myTestReviewEditRoute(point.toString()))
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyReview: (Int) -> Unit = {},
  navigateMyClassReviewEdit: (Int) -> Unit = {},
  navigateMyTestReviewEdit: (Int) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyReview = navigateMyReview,
    )
  }
  composable(
    route = MyInfoRoute.myReviewRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyReviewRoute(
      padding = padding,
      popBackStack = popBackStack,
      navigateMyClassReview = navigateMyClassReviewEdit,
      navigateMyTestReview = navigateMyTestReviewEdit,
    )
  }
  composable(
    route = MyInfoRoute.myClassReviewEditRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyClassReviewEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
  composable(
    route = MyInfoRoute.myTestReviewEditRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyTestReviewEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myPoint = "point"

  fun myReviewRoute(point: String) = "my-review/$point"
  fun myClassReviewEditRoute(point: String) = "my-class-review-edit/$point"
  fun myTestReviewEditRoute(point: String) = "my-test-review-edit/$point"
}
