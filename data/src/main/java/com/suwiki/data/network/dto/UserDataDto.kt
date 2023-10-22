package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDataDto(
  @SerializedName("loginId") val userId: String,
  val email: String,
  val point: Int,
  @SerializedName("writtenEvaluation") val writtenEvaluation: Int,
  @SerializedName("writtenExam") val writtenExam: Int,
  @SerializedName("viewExam") val viewExam: Int,
) : Serializable
