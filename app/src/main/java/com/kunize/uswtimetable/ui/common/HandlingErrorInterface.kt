package com.kunize.uswtimetable.ui.common

import com.suwiki.domain.model.SuwikiError

interface HandlingErrorInterface {
    fun handleError(error: SuwikiError)
}
