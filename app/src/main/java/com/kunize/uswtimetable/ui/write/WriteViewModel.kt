package com.kunize.uswtimetable.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel() {
    private val _honeyScore = MutableLiveData<Float>()
    val honeyScore: LiveData<Float>
        get() = _honeyScore

    fun changeHoneyScore(value: Float) {
        _honeyScore.value = value
    }

    private val _satisfactionScore = MutableLiveData<Float>()
    val satisfactionScore: LiveData<Float>
        get() = _satisfactionScore

    fun changeSatisfactionScore(value: Float) {
        _satisfactionScore.value = value
    }

    private val _learningScore = MutableLiveData<Float>()
    val learningScore: LiveData<Float>
        get() = _learningScore

    fun changeLearningScore(value: Float) {
        _learningScore.value = value
    }

    init {
        _honeyScore.value = 0f
        _satisfactionScore.value = 0f
        _learningScore.value = 0f
    }
}