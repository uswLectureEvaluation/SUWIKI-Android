package com.suwiki.domain.repository

import com.suwiki.domain.model.LectureDetailEvaluationData
import com.suwiki.domain.model.LectureDetailExamData
import com.suwiki.domain.model.LectureDetailInfo
import com.suwiki.domain.model.Result

interface LectureInfoRepository {
    suspend fun getLectureDetailInfo(lectureId: Long): Result<LectureDetailInfo>

    suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData>

    suspend fun getLectureDetailExam(lectureId: Long, page: Int): Result<LectureDetailExamData>

    suspend fun buyExam(lectureId: Long): Result<String>

    suspend fun reportLecture(lectureId: Long): Result<Unit>

    suspend fun reportExam(lectureId: Long): Result<Unit>
}
