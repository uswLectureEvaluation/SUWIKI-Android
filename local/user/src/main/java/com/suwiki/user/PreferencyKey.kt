package com.suwiki.user

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val LOGGED_IN = booleanPreferencesKey("[KEY] is logged in")

val USER_ID = stringPreferencesKey("[KEY] user id")
val POINT = intPreferencesKey("[KEY] user point")
val WRITTEN_EVALUATION = intPreferencesKey("[KEY]user written evaluation count")
val WRITTEN_EXAM = intPreferencesKey("[KEY] user written exam count")
val VIEW_EXAM = intPreferencesKey("[KEY] user exam view count")
val EMAIL = stringPreferencesKey("[KEY] user email")