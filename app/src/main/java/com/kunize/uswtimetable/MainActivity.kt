package com.kunize.uswtimetable


import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakao.adfit.ads.AdListener
import com.kunize.uswtimetable.databinding.ActivityMainBinding
import com.kunize.uswtimetable.dataclass.TimeData
import org.json.JSONArray
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navigationFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navigationFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.setOnItemReselectedListener {  }

        binding.bannerAdView.setClientId(getString(R.string.kakaoAdfitID))  // 할당 받은 광고단위 ID 설정
        binding.bannerAdView.loadAd()
    }

    override fun onPause() {
        super.onPause()
        binding.bannerAdView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bannerAdView.destroy()
    }


    override fun onResume() {
        super.onResume()
        binding.bannerAdView.resume()
    }

    companion object {
        fun bitmapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val byte = baos.toByteArray()
            return Base64.encodeToString(byte, Base64.DEFAULT)
        }

        fun stringToBitmap(encodedString: String): Bitmap? {
            return try {
                val byte = Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(byte, 0, byte.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}