package com.kunize.uswtimetable.dataclass

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ResponseData (
    @SerializedName("data") val receivedData: JsonArray,
    @SerializedName("success") val responseState: Boolean,
)