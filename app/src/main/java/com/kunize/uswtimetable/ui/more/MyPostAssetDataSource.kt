package com.kunize.uswtimetable.ui.more

import com.google.gson.Gson
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.util.AssetLoader
import org.json.JSONObject

class MyPostAssetDataSource(private val assetLoader: AssetLoader): MyPostDataSource {

    private val gson = Gson()

    override fun getMyPostData(): List<MyEvaluation> {
        val evaluationsList = mutableListOf<MyEvaluation>()
        assetLoader.getJsonString("MyEvaluationsDummy.json")?.let { jsonString ->
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                val jsonData = jsonArray.getJSONObject(i)
                val evaluation = gson.fromJson(jsonData.toString(), MyEvaluation::class.java)
                evaluationsList.add(evaluation)
            }
        }
        return evaluationsList.toList()
    }
}