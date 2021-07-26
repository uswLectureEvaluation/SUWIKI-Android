package com.kunize.uswtimetable

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.con.visibility = View.GONE

        val intent = Intent(this, CreateTimeTableActivity::class.java)
        startActivity(intent)


    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        var location = IntArray(2)
        var iv = TextView(this)
        val r = Rect()
        val bot = binding.button4.bottom
        val x = binding.button4.left
        var params = RelativeLayout.LayoutParams(binding.timeTable.width / 5,300)
        params.leftMargin = 0
        params.topMargin = 0
        iv.text = "${params.leftMargin}\n${params.topMargin}"
        iv.setTextColor(Color.WHITE)
        iv.setBackgroundColor(Color.BLACK)
        iv.setOnClickListener {
            Log.d("Test","클릭")
        }
        binding.timeTable.addView(iv,params)
    }
}