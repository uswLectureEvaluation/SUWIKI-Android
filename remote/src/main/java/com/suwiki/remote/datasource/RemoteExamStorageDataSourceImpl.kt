package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteExamStorageDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.request.exam.PostLectureExamRequest
import com.suwiki.remote.request.exam.UpdateLectureExamRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteExamStorageDataSourceImpl @Inject constructor(
    private val examApi: ExamApi,
) : RemoteExamStorageDataSource {

    override suspend fun postLectureExam(
        lectureId: Long,
        lectureName: String?,
        professor: String?,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit> {
        val request = PostLectureExamRequest(
            lectureName = lectureName,
            professor = professor,
            selectedSemester = selectedSemester,
            examInfo = examInfo,
            examType = examType,
            examDifficulty = examDifficulty,
            content = content,
        )

        return examApi.postLectureExam(
            lectureId = lectureId,
            request = request,
        ).toResult()
    }

    override suspend fun updateLectureExam(
        lectureId: Long,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit> {
        val request = UpdateLectureExamRequest(
            selectedSemester = selectedSemester,
            examInfo = examInfo,
            examType = examType,
            examDifficulty = examDifficulty,
            content = content,
        )

        return examApi.updateLectureExam(
            lectureId = lectureId,
            request = request,
        ).toResult()
    }

    override suspend fun deleteExamInfo(id: Long): Result<Unit> {
        return examApi.deleteExamInfo(id).toResult()
    }
}
