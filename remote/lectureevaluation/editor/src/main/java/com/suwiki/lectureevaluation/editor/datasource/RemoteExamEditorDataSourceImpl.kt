package com.suwiki.lectureevaluation.editor.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamEditorDataSource
import com.suwiki.lectureevaluation.editor.api.ExamEditorApi
import com.suwiki.lectureevaluation.editor.request.PostLectureExamRequest
import com.suwiki.lectureevaluation.editor.request.UpdateLectureExamRequest
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteExamEditorDataSourceImpl @Inject constructor(
    private val examApi: ExamEditorApi,
) : RemoteExamEditorDataSource {

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
