package com.suwiki.remote.lectureevaluation.viewer.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(val data: T)
