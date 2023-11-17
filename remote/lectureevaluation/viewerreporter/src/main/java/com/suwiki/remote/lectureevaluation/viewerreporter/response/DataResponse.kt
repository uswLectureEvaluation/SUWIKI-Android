package com.suwiki.remote.lectureevaluation.viewerreporter.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(val data: T)
