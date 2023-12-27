package com.suwiki.feature.notice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.notice.NoticeDetailScreen
import com.suwiki.feature.notice.NoticeRoute

fun NavController.navigateNotice() {
  navigate(NoticeRoute.route)
}

fun NavController.navigateNoticeDetail() {
  navigate(NoticeDetailRoute.route)
}

fun NavGraphBuilder.noticeNavGraph(
  padding: PaddingValues,
  navigateNoticeDetail: () -> Unit = {},
) {
  composable(route = NoticeRoute.route) {
    NoticeRoute(
      padding = padding,
      navigateNoticeDetail = navigateNoticeDetail,
    )
  }
  composable(route = NoticeDetailRoute.route) {
    NoticeDetailScreen(
      title = "asd",
      date = "2030.03.12",
      content = "adsasdasd\nsadasd\nasd\n\nasdasd",
    )
  }
}

object NoticeRoute {
  const val route = "notice"
}

object NoticeDetailRoute {
  const val route = "notice-detail"
}
