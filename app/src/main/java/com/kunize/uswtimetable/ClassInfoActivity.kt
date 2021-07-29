package com.kunize.uswtimetable

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.kunize.uswtimetable.databinding.ActivityClassInfoBinding
import com.kunize.uswtimetable.dialog.EditTimeDialog
import java.util.*

class ClassInfoActivity : AppCompatActivity() {
    companion object {
        val colorMap = mapOf<String, Int>("Pink" to Color.rgb(254, 136, 136), //핑크
            "Orange" to Color.rgb(255, 193, 82), //주황
            "Purple" to Color.rgb(204, 154, 243), //보라
        "Sky" to Color.rgb(137, 200, 254), //하늘
        "Green" to Color.rgb(165, 220, 129), //연두
        "Brown" to Color.rgb(194, 143, 98), //갈색
        "Gray" to Color.rgb(194, 193, 189), //회색
        "Navy" to Color.rgb(67, 87, 150) //남색
        )
    }
    private val binding by lazy { ActivityClassInfoBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val className = intent.getStringExtra("className")
        val professor = intent.getStringExtra("professor")
        val time = intent.getStringExtra("time")
        var timeSplit: List<String>

        binding.editClassName.setText(className)
        binding.editProfessorName.setText(professor)

        setVisibilityTime2(View.GONE)
        setVisibilityTime3(View.GONE)

        if(className!!.contains("이러닝")) {
            if(time == "None")
                binding.time1.text = "이러닝"
            else
                binding.time1.text = "이러닝 " + time
        } else if(time!!.contains("),")) {
            timeSplit = time.split("),")
            binding.time1.text = timeSplit[0] + ")"
            binding.time2.text = timeSplit[1]
            setVisibilityTime2(View.VISIBLE)
            //TODO 미래421(화1,2,3),(목7,8) 예외처리 필요
        } else if(time!!.contains(" ")){
            timeSplit = time.split("(")
            val location = timeSplit[0]
            val daySplit = timeSplit[1].replace("(","").replace(")","").split(" ")
            binding.time1.text = location + "(" + daySplit[0] + ")"
            binding.time2.text = location + "(" + daySplit[1] + ")"
            setVisibilityTime2(View.VISIBLE)
            if(daySplit.size > 2) {
                binding.time3.text = location + "(" + daySplit[2] + ")"
                setVisibilityTime3(View.VISIBLE)
            }
        } else {
            timeSplit = time.split("(")
            val location = timeSplit[0]
            val day = timeSplit[1][0]
            val checkTime = checkSeq(timeSplit[1].replace(")","").substring(1))
            binding.time1.text = location+"("+day + checkTime[0]+")"

            if(checkTime.size == 2) {
                binding.time2.text = location+"("+ day +checkTime[1]+")"
                setVisibilityTime2(View.VISIBLE)
            }
            if(checkTime.size == 3) {
                binding.time3.text = location+"("+ day +checkTime[2]+")"
                setVisibilityTime3(View.VISIBLE)
            }
        }

        binding.addTime.setOnClickListener {
            if(!binding.time1.isVisible)
                setVisibilityTime1(View.VISIBLE)
            else if(!binding.time2.isVisible)
                setVisibilityTime2(View.VISIBLE)
            else if(!binding.time3.isVisible)
                setVisibilityTime3(View.VISIBLE)
        }

        binding.deleteTime1.setOnClickListener {
            setVisibilityTime1(View.GONE)
        }
        binding.deleteTime2.setOnClickListener {
            setVisibilityTime2(View.GONE)
        }
        binding.deleteTime3.setOnClickListener {
            setVisibilityTime3(View.GONE)
        }

        binding.editTime1.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time1.text = className+"("+day+time+")"
            }
            try {
                val tempSplit = binding.time1.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        binding.editTime2.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time2.text = className+"("+day+time+")"
            }
            try {
                val tempSplit = binding.time2.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        binding.editTime3.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time3.text = className+"("+day+time+")"
            }
            try {
                val tempSplit = binding.time3.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        val randomNum = Random().nextInt(8)
        val randomColor = colorMap.values.toIntArray()[randomNum]
        binding.imgColor.imageTintList = ColorStateList.valueOf(randomColor)

        binding.imgColor.setOnClickListener {
            val dlg = EditColorDialog(this)
            Log.d("color","클릭함")
            dlg.setOnOKClickedListener(object : EditColorDialog.OKClickedListener {
                override fun onOKClicked(color: ColorStateList?) {
                    binding.imgColor.imageTintList = color
                    Log.d("color","$color")
                }

            })
            dlg.start()
        }
    }

    private fun startDialogWithData(
        tempSplit: List<String>,
        dlg: EditTimeDialog
    ) {
        val location = tempSplit[0]
        val day = tempSplit[1][0].toString()
        val startTime = tempSplit[1][1].toString()
        val endTime = tempSplit[1][tempSplit[1].length - 2].toString()
        dlg.start(location, day, startTime, endTime)
    }


    private fun checkSeq(str: String) : List<String> {
        val onlyNumList = str.split(",")
        var tempNumString = ""
        for(idx in 0 until onlyNumList.size) {
            try {
                if ((onlyNumList[idx].toInt() - onlyNumList[idx + 1].toInt()) == -1) {
                    tempNumString = tempNumString + onlyNumList[idx] + ","
                    Log.d("divide"," $tempNumString $idx")
                }
                else {
                    tempNumString = tempNumString + onlyNumList[idx] + "!"
                    Log.d("divide"," $tempNumString $idx")
                }
            } catch( e: Exception) {
                tempNumString += onlyNumList[idx]
            }
        }
        return tempNumString.split("!")
    }

    private fun setVisibilityTime1(set: Int) {
        if(set == View.GONE) {
            binding.time1.text = ""
        }
        binding.time1.visibility = set
        binding.editTime1.visibility = set
        binding.deleteTime1.visibility = set
    }

    private fun setVisibilityTime2(set: Int) {
        if(set == View.GONE) {
            binding.time2.text = ""
        }
        binding.time2.visibility = set
        binding.editTime2.visibility = set
        binding.deleteTime2.visibility = set
    }

    private fun setVisibilityTime3(set: Int) {
        if(set == View.GONE) {
            binding.time3.text = ""
        }
        binding.time3.visibility = set
        binding.editTime3.visibility = set
        binding.deleteTime3.visibility = set
    }
}