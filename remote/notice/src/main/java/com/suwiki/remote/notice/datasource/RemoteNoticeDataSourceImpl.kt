package com.suwiki.remote.notice.datasource

import com.suwiki.common.model.notice.Notice
import com.suwiki.common.model.notice.NoticeDetail
import com.suwiki.data.notice.datasource.RemoteNoticeDataSource
import com.suwiki.remote.notice.api.NoticeApi
import com.suwiki.remote.notice.response.toModel
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
  private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

  override suspend fun getNoticeList(page: Int): List<Notice> {
    return noticeApi.getNoticeList(page).getOrThrow().data.map { it.toModel() }
  }

  override suspend fun getNoticeDetail(id: Long): NoticeDetail {
    return noticeApi.getNotice(id).getOrThrow().data.toModel()
  }

  override suspend fun checkUpdateMandatory(versionCode: Long): Boolean {
    return noticeApi.checkUpdateMandatory(versionCode = versionCode).getOrThrow().isUpdateMandatory
  }
}
