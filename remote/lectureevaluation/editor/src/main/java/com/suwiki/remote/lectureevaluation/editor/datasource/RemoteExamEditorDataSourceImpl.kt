package com.suwiki.remote.lectureevaluation.editor.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamEditorDataSource
import com.suwiki.remote.lectureevaluation.editor.api.ExamEditorApi
import com.suwiki.remote.lectureevaluation.editor.request.PostLectureExamRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateLectureExamRequest
import com.suwiki.core.model.Result
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
    ): com.suwiki.core.model.Result<Unit> {
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
    ): com.suwiki.core.model.Result<Unit> {
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

    override suspend fun deleteExamInfo(id: Long): com.suwiki.core.model.Result<Unit> {
        return examApi.deleteExamInfo(id).toResult()
    }
}
