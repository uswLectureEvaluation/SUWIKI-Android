package com.suwiki.domain.lectureevaluation.my.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.lectureevaluation.PurchaseHistory
import com.suwiki.domain.lectureevaluation.my.repository.ExamMyRepository
import javax.inject.Inject

class GetPurchaseHistoryUseCase @Inject constructor(
  private val examMyRepository: ExamMyRepository,
) {
  suspend operator fun invoke(): Result<List<PurchaseHistory>> = runCatchingIgnoreCancelled {
    examMyRepository.getPurchaseHistory()
  }
}
