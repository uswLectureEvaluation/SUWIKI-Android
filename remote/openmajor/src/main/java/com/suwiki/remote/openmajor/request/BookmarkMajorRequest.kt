package com.suwiki.remote.openmajor.request

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkMajorRequest(val majorType: String)
