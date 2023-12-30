package com.suwiki.feature.notice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.notice.NoticeDetailRoute
import com.suwiki.feature.notice.NoticeRoute

fun NavController.navigateNotice() {
  navigate(NoticeRoute.route)
}

fun NavController.navigateNoticeDetail() {
  navigate(NoticeDetailRoute.route)
}

fun NavGraphBuilder.noticeNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNoticeDetail: () -> Unit = {},
) {
  composable(route = NoticeRoute.route) {
    NoticeRoute(
      padding = padding,
      navigateNoticeDetail = navigateNoticeDetail,
      popBackStack = popBackStack,
    )
  }
  composable(route = NoticeDetailRoute.route) {
    NoticeDetailRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
}

object NoticeRoute {
  const val route = "notice"
}

object NoticeDetailRoute {
  const val route = "notice-detail"
}
