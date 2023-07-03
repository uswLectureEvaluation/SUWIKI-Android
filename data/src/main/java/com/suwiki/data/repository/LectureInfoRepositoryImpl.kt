package com.suwiki.data.repository

import com.suwiki.data.datasource.LectureInfoDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.LectureDetailEvaluationData
import com.suwiki.domain.model.LectureDetailExamData
import com.suwiki.domain.model.LectureDetailInfo
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.LectureInfoRepository
import javax.inject.Inject

class LectureInfoRepositoryImpl @Inject constructor(
    private val dataSource: LectureInfoDataSource,
) : LectureInfoRepository {
    override suspend fun getLectureDetailInfo(lectureId: Long): Result<LectureDetailInfo> =
        dataSource.getLectureDetailInfoDataSource(lectureId).map { it.toDomain() }

    override suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData> {
        return dataSource.getLectureDetailEvaluationDataSource(lectureId, page).map {
            it.toDomain()
        }
    }

    override suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamData> {
        return dataSource.getLectureDetailExamDataSource(lectureId, page).map {
            it.toDomain()
        }
    }

    override suspend fun buyExam(lectureId: Long): Result<String> {
        return dataSource.buyExam(lectureId)
    }

    override suspend fun reportLecture(lectureId: Long): Result<Unit> {
        return dataSource.reportLecture(lectureId)
    }

    override suspend fun reportExam(lectureId: Long): Result<Unit> {
        return dataSource.reportExam(lectureId)
    }
}
