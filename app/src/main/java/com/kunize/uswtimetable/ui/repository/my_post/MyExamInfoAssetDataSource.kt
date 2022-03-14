package com.kunize.uswtimetable.ui.repository.my_post

import com.google.gson.Gson
import com.kunize.uswtimetable.dataclass.MyExamInfo
import com.kunize.uswtimetable.util.AssetLoader
import org.json.JSONObject

class MyExamInfoAssetDataSource(private val assetLoader: AssetLoader): MyExamInfoDataSource {

    private val gson = Gson()

    override fun getMyExamInfoData(): List<MyExamInfo> {
        val examInfoList = mutableListOf<MyExamInfo>()
        assetLoader.getJsonString("MyExamInfosDummy.json")?.let { jsonString ->
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                val jsonData = jsonArray.getJSONObject(i)
                val examInfo = gson.fromJson(jsonData.toString(), MyExamInfo::class.java)
                examInfoList.add(examInfo)
            }
        }
        return examInfoList.toList()
    }
}