package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.NoticeDetailDto
import com.suwiki.data.network.dto.NoticeDto
import com.suwiki.domain.model.Notice
import com.suwiki.domain.model.SimpleNotice

fun NoticeDto.toDomain(): SimpleNotice {
  return SimpleNotice(
    id,
    title,
    date,
  )
}

fun NoticeDetailDto.toDomain(): Notice {
  return Notice(
    id,
    title,
    date,
    content,
  )
}
