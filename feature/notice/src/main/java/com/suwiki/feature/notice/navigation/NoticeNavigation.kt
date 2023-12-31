package com.suwiki.feature.notice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.notice.detail.NoticeDetailRoute
import com.suwiki.feature.notice.NoticeRoute

fun NavController.navigateNotice() {
  navigate(NoticeRoute.route)
}

fun NavController.navigateNoticeDetail(noticeId: Long) {
  navigate(NoticeRoute.detailRoute(noticeId.toString()))
}

fun NavGraphBuilder.noticeNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNoticeDetail: (Long) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(route = NoticeRoute.route) {
    NoticeRoute(
      padding = padding,
      navigateNoticeDetail = navigateNoticeDetail,
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
  composable(
    route = NoticeRoute.detailRoute("{${NoticeRoute.DETAIL_ARGUMENT_NAME}}"),
    arguments = listOf(
      navArgument(NoticeRoute.DETAIL_ARGUMENT_NAME) {
        type = NavType.StringType
      },
    ),
  ) {
    NoticeDetailRoute(
      padding = padding,
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
}

object NoticeRoute {
  const val route = "notice"
  const val DETAIL_ARGUMENT_NAME = "noticeId"

  fun detailRoute(noticeId: String) = "notice/detail/$noticeId"
}
