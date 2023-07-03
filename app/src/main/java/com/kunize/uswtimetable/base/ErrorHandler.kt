package com.kunize.uswtimetable.base

import android.content.Context
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.extensions.startActivity
import com.kunize.uswtimetable.util.extensions.toast
import com.suwiki.domain.model.SuwikiError

interface ErrorHandler {
    fun handleError(context: Context, error: SuwikiError)
}

class CommonErrorHandler : ErrorHandler {
    override fun handleError(context: Context, error: SuwikiError) {
        when (error) {
            is SuwikiError.CustomError -> toast(context, error.message)
            is SuwikiError.HttpError -> TODO()
            SuwikiError.NetworkError -> toast(context, "네트워크 연결이 불안정합니다. 다시 시도해주세요")
            SuwikiError.NoResult -> TODO()
            SuwikiError.RequestFailure -> toast(context, "요청을 처리할 수 없습니다")
            SuwikiError.RestrictApproach -> toast(context, "권한이 없습니다")
            SuwikiError.TokenExpired -> {
                toast(context, "로그인이 필요합니다")
                context.startActivity<LoginActivity> {}
            }
        }
    }
}
