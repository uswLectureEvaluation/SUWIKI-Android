package com.suwiki.domain.notice.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.notice.Notice
import com.suwiki.domain.notice.repository.NoticeRepository
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
  private val noticeRepository: NoticeRepository,
) {
  suspend operator fun invoke(page: Int): Result<List<Notice>> = runCatchingIgnoreCancelled {
    noticeRepository.getNoticeList(page)
  }
}
