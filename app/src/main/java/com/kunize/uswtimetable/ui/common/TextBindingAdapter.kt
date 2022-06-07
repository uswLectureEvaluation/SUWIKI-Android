package com.kunize.uswtimetable.ui.common

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.PostData
import com.kunize.uswtimetable.util.PostData.difficulty
import com.kunize.uswtimetable.util.PostData.homework
import com.kunize.uswtimetable.util.PostData.team
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

fun dateTimeFormat(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

fun dateFormat(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}

@BindingAdapter("dateTime")
fun applyDateTime(view: TextView, dateTime: LocalDateTime) {
    view.text = dateTimeFormat(dateTime)
}

@BindingAdapter("date")
fun applyDate(view: TextView, dateTime: LocalDateTime) {
    view.text = dateFormat(dateTime)
}

@BindingAdapter("banCreatedAt")
fun applyBanCreatedAt(view: TextView, createdAt: LocalDateTime) {
    view.text = view.resources.getString(R.string.suspension_created_at, dateTimeFormat(createdAt))
}

@BindingAdapter("banCreatedAt", "banExpiredAt")
fun applyBanExpiredAt(view: TextView, createdAt: LocalDateTime, expiredAt: LocalDateTime) {
    val diff = expiredAt.compareTo(createdAt)
    view.text = view.resources.getString(R.string.suspension_expired_at, dateTimeFormat(expiredAt), diff)
}

@BindingAdapter("point")
fun applyUserPoint(view: TextView, point: Int) {
    if (point >= 0) {
        view.text = view.resources.getString(R.string.detail_point_positive, point)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.suwiki_blue_900))
    } else {
        view.text = view.resources.getString(R.string.detail_point, point)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.suwiki_purple))
    }
}

@BindingAdapter("count")
fun applyCount(view: TextView, count: Int?) {
    count?:return
    val ssb = SpannableStringBuilder(count.toString()+"개")
    val color = if (count >= 0) "#346CFD" else "#7800FF"
    ssb.setSpan(ForegroundColorSpan(Color.parseColor(color)), 0, count.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = ssb
}

@BindingAdapter("floatText")
fun applyFloatToText(view: TextView, value: Float) {
    view.text = (round(value * 10) / 10).toString() // 소수 둘째 자리에서 반올림
}

@BindingAdapter("postData", "postDataType")
fun applyPostData(view: TextView, level: Int, type: PostData.PostDataType) {
    applyTextColor(view, level)
    view.text = when (type) {
        PostData.PostDataType.TEAM -> team[level]
        PostData.PostDataType.HOMEWORK -> homework[level]
        PostData.PostDataType.DIFFICULTY -> difficulty[level]
    }
}

@BindingAdapter("textColorLevel")
fun applyTextColor(view: TextView, level: Int) {
    val color = when(level) {
        0 -> ContextCompat.getColor(view.context, R.color.suwiki_blue_900)
        2 -> ContextCompat.getColor(view.context, R.color.suwiki_purple)
        else -> ContextCompat.getColor(view.context, R.color.suwiki_black_700)
    }
    view.setTextColor(color)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("major", "professor")
fun applyMajorAndProfessorText(view: TextView, major: String, professor: String) {
    view.text = "$major | $professor"
}