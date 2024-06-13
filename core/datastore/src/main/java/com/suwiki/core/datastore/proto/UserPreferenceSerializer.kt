package com.suwiki.core.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.suwiki.core.datastore.UserPreference
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferenceSerializer @Inject constructor() : Serializer<UserPreference> {
  override val defaultValue: UserPreference = UserPreference.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): UserPreference =
    try {
      UserPreference.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: UserPreference, output: OutputStream) {
    t.writeTo(output)
  }
}
