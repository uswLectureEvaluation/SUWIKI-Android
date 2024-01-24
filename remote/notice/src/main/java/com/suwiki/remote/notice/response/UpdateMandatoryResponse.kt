package com.suwiki.remote.notice.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMandatoryResponse(
  val isUpdateMandatory: Boolean
)
