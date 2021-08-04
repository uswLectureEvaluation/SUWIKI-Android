package com.kunize.uswtimetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class OpenSourceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        findViewById<TextView>(R.id.btnMore).setOnClickListener {
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
    }
}