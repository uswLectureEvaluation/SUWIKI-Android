package com.suwiki.data.repository

import com.suwiki.data.datasource.MyPostDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.LectureExam
import com.suwiki.domain.model.MyEvaluation
import com.suwiki.domain.model.PurchaseHistory
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.MyPostRepository
import javax.inject.Inject

class MyPostRepositoryImpl @Inject constructor(
    private val dataSource: MyPostDataSource,
) : MyPostRepository {
    override suspend fun getEvaluation(page: Int): Result<List<MyEvaluation>> {
        return dataSource.getMyEvaluations(page).map { evaluations ->
            evaluations.map { it.toDomain() }
        }
    }

    override suspend fun getExamInfos(page: Int): Result<List<LectureExam>> {
        return dataSource.getMyExamInfos(page).map { exams ->
            exams.map { it.toDomain() }
        }
    }

    override suspend fun deleteEvaluation(id: Long) {
        dataSource.deleteMyEvaluation(id)
    }

    override suspend fun deleteExamInfo(id: Long) {
        dataSource.deleteMyExamInfo(id)
    }

    override suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>> {
        return dataSource.getPurchaseHistory().map { purchases ->
            purchases.map { it.toDomain() }
        }
    }
}
