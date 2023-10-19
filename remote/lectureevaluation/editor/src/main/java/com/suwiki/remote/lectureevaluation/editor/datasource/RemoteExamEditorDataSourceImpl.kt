package com.suwiki.remote.lectureevaluation.editor.datasource

import com.suwiki.data.lectureevaluation.editor.datasource.RemoteExamEditorDataSource
import com.suwiki.remote.lectureevaluation.editor.api.ExamEditorApi
import com.suwiki.remote.lectureevaluation.editor.request.PostLectureExamRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateLectureExamRequest
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
    ) {
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
        ).getOrThrow()
    }

    override suspend fun updateLectureExam(
        lectureId: Long,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ) {
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
        ).getOrThrow()
    }

    override suspend fun deleteExamInfo(id: Long) {
        return examApi.deleteExamInfo(id).getOrThrow()
    }
}
