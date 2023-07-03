package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.LectureDetailInfoDto
import com.suwiki.domain.model.LectureDetailInfo

fun LectureDetailInfoDto.toDomain(): LectureDetailInfo {
    return LectureDetailInfo(
        id,
        semester,
        professor,
        majorType,
        lectureType,
        lectureName,
        lectureTotalAvg,
        lectureSatisfactionAvg,
        lectureHoneyAvg,
        lectureLearningAvg,
        lectureTeamAvg,
        lectureDifficultyAvg,
        lectureHomeworkAvg,
    )
}
