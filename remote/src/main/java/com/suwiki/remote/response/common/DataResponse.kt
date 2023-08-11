package com.suwiki.remote.response.common

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(val data: T)
