package com.kunize.uswtimetable.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.*

fun EditText.afterTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            completion(p0)
        }
    })
}

fun EditText.onTextChanged(completion: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            completion(p0)
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun EditText.afterEditTextChanged(completion: (Editable?) -> Unit) {
    var lastInput: String
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            val newInput = p0.toString()
            debounceJob?.cancel()

            lastInput = newInput
            debounceJob = uiScope.launch {
                delay(200L)
                if (lastInput == newInput)
                    completion(p0)
            }
        }
    })
}
