package com.kunize.uswtimetable.util.extensions

import android.widget.SeekBar

fun SeekBar.seekbarChangeListener(doChangeProgress: (progress: Float) -> Unit) {
    this.setOnSeekBarChangeListener(
        (
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (progress < 1)
                        progress = 1
                    doChangeProgress(progress.toFloat() / 2)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
            )
    )
}
