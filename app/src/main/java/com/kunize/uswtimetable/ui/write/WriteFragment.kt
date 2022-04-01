package com.kunize.uswtimetable.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentWriteBinding
import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.WriteFragmentTitle
import com.kunize.uswtimetable.util.seekbarChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding

    private val writeViewModel: WriteViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var taskRadioBtnList: List<RadioButton>
    private lateinit var gradeRadioBtnList: List<RadioButton>

    private lateinit var testContentCheckBoxList: List<CheckBox>
    private lateinit var difficultyRadioBtnList: List<RadioButton>

    private var defaultHoneyProgress = 6
    private var defaultLearningProgress = 6
    private var defaultSatisfactionProgress = 6

    var lectureId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        binding.viewModel = writeViewModel
        binding.lifecycleOwner = this

        val args: WriteFragmentArgs by navArgs()
        lectureId = args.lectureId

        testContentCheckBoxList = listOf(
            binding.genealogyCheckBox,
            binding.textbookCheckBox,
            binding.pptCheckBox,
            binding.noteCheckBox,
            binding.applicationCheckBox,
            binding.practiceCheckBox,
            binding.taskCheckBox
        )

        difficultyRadioBtnList = listOf(
            binding.veryEasyRadioButton,
            binding.easyRadioButton,
            binding.normalRadioButton,
            binding.difficultRadioButton,
            binding.veryDifficultRadioButton
        )

        setInitValueWhenWrite(args)
        setFragmentViewType(args)
        setInitValueWhenEditMyEvaluation(args)
        setInitValueWhenEditMyExam(args)
        setSeekBarListener()
        setSeekBarProgress()

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.writeFragment, true)
        }

        binding.finishButton.setOnClickListener {
            CoroutineScope(IO).launch {
                val success = when(binding.writeType.text.toString()) {
                    getString(R.string.write_evaluation) -> writeViewModel.postLectureEvaluation(lectureId, getLectureEvaluationInfo())
                    getString(R.string.write_exam) -> writeViewModel.postLectureExam(lectureId, getLectureExamInfo())
                    getString(R.string.edit_evaluation) -> writeViewModel.updateLectureEvaluation(lectureId, getLectureEvaluationEditInfo())
                    else -> {false}
                }
                withContext(Main) {
                    if (success)
                        if (args.myExamInfo == null && args.myEvaluation == null)
                            goToLectureInfoFragment()
                        else
                            goToMyPostFragment()
                }
            }
        }

        return binding.root
    }

    private fun getLectureEvaluationInfo(): LectureEvaluationPostDto {
        val info: LectureEvaluationPostDto
        with(binding) {
            val team = if (teamNotExistRadioButton.isChecked) 0 else 1
            val difficulty =
                if (gradeGoodRadioButton.isChecked) 0 else if (binding.gradeNormalRadioButton.isChecked) 1 else 2
            val homework =
                if (taskNotExistRadioButton.isChecked) 0 else if (taskNormalRadioButton.isChecked) 1 else 2


            info = LectureEvaluationPostDto(
                writeLectureName.text.toString(),
                writeProfessor.text.toString(),
                writeYearSemesterSpinner.selectedItem.toString(),
                writeViewModel.satisfactionScore.value!!.toFloat(),
                writeViewModel.learningScore.value!!.toFloat(),
                writeViewModel.honeyScore.value!!.toFloat(),
                team,
                difficulty,
                homework,
                writeContent.text.toString()
            )
        }
        return info
    }

    private fun getLectureExamInfo(): LectureExamPostDto {
        val info: LectureExamPostDto
        with(binding) {
            var testContent = ""
            testContentCheckBoxList.forEach { checkBox ->
                if(checkBox.isChecked)
                    testContent += checkBox.text.toString() + ","
            }
            testContent = testContent.dropLast(1)

            var testDifficulty = ""
            difficultyRadioBtnList.forEach {
                if(it.isChecked)
                    testDifficulty = it.text.toString()
            }

            info = LectureExamPostDto(
                writeLectureName.text.toString(),
                writeProfessor.text.toString(),
                writeYearSemesterSpinner.selectedItem.toString(),
                testContent,
                testDifficulty,
                writeContent.text.toString()
            )
        }
        return info
    }

    private fun getLectureEvaluationEditInfo(): LectureEvaluationEditDto {
        val info: LectureEvaluationEditDto
        with(binding) {
            val team = if (teamNotExistRadioButton.isChecked) 0 else 1
            val difficulty =
                if (gradeGoodRadioButton.isChecked) 0 else if (binding.gradeNormalRadioButton.isChecked) 1 else 2
            val homework =
                if (taskNotExistRadioButton.isChecked) 0 else if (taskNormalRadioButton.isChecked) 1 else 2


            info = LectureEvaluationEditDto(
                writeYearSemesterSpinner.selectedItem.toString(),
                writeViewModel.satisfactionScore.value!!.toFloat(),
                writeViewModel.learningScore.value!!.toFloat(),
                writeViewModel.honeyScore.value!!.toFloat(),
                team,
                difficulty,
                homework,
                writeContent.text.toString()
            )
        }
        return info
    }

    private fun setSeekBarListener() {
        binding.honeySeekBar.seekbarChangeListener {
            writeViewModel.changeHoneyScore(it)
        }

        binding.satisfactionSeekBar.seekbarChangeListener {
            writeViewModel.changeSatisfactionScore(it)
        }

        binding.learningSeekBar.seekbarChangeListener {
            writeViewModel.changeLearningScore(it)
        }
    }

    private fun setInitValueWhenEditMyExam(args: WriteFragmentArgs) {
        args.myExamInfo?.let {
            setLectureProfessorName(it)
            setTestContentCheckBox(it)
            setDifficultyRadioBtn(it)
            binding.writeContent.setText(it.content)
            binding.writeType.text = WriteFragmentTitle.EDIT_MY_EXAM
            binding.finishButton.text = WriteFragmentTitle.FINISH_EDIT
        }
    }

    private fun setDifficultyRadioBtn(it: MyExamInfo) {
        for (radioButton in difficultyRadioBtnList) {
            if (it.examDifficulty == radioButton.text.toString()) {
                radioButton.isChecked = true
                break
            }
        }
    }

    private fun setTestContentCheckBox(it: MyExamInfo) {
        for (checkBox in testContentCheckBoxList) {
            for (dataString in it.examType.split(",")) {
                if (checkBox.text == dataString)
                    checkBox.isChecked = true
            }
        }
    }

    private fun setLectureProfessorName(it: MyExamInfo) {
        binding.writeLectureName.text = it.subject
        binding.writeProfessor.text = it.professor
    }

    private fun setFragmentViewType(args: WriteFragmentArgs) {
        if (args.isEvaluation) {
            binding.testGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EVALUATION
            binding.finishButton.text = WriteFragmentTitle.FINISH_EVALUATION
        } else {
            binding.lectureGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EXAM
            binding.finishButton.text = WriteFragmentTitle.FINISH_EXAM
        }
    }

    private fun setInitValueWhenWrite(args: WriteFragmentArgs) {
        args.lectureProfessorName?.let {
            binding.writeLectureName.text = it.subject
            binding.writeProfessor.text = it.professor
            setSpinnerList(args.lectureProfessorName!!.semester.split(","))
        }
    }

    private fun setSpinnerList(list: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list)
        binding.writeYearSemesterSpinner.adapter = spinnerAdapter
    }

    private fun setInitValueWhenEditMyEvaluation(args: WriteFragmentArgs) {
        args.myEvaluation?.let {
            setLectureProfessorName(it)
            setDefaultSeekBarProgressValue(it)
            setTeamRadioBtn(it)
            setTaskRadioBtn(it)
            setGradeRadioBtn(it)
            val list = it.semesterList.split(",")
            setSpinnerList(list)
            binding.writeYearSemesterSpinner.setSelection(list.indexOf(it.semester))
            binding.writeContent.setText(it.content)
            binding.writeType.text = WriteFragmentTitle.EDIT_MY_EVALUATION
            binding.finishButton.text = WriteFragmentTitle.FINISH_EDIT
        }
    }

    private fun setGradeRadioBtn(it: MyEvaluationDto) {
        gradeRadioBtnList = listOf(
            binding.gradeGoodRadioButton,
            binding.gradeNormalRadioButton,
            binding.gradeDifficultRadioButton
        )
        gradeRadioBtnList[it.difficulty].isChecked = true
    }

    private fun setTaskRadioBtn(it: MyEvaluationDto) {
        when (it.homework) {
            0 -> binding.taskNotExistRadioButton.isChecked = true
            1 -> binding.taskNormalRadioButton.isChecked = true
            2 -> binding.taskManyRadioButton.isChecked = true
        }
    }

    private fun setTeamRadioBtn(it: MyEvaluationDto) {
        if (it.team == 1)
            binding.teamExistRadioButton.isChecked = true
        else
            binding.teamNotExistRadioButton.isChecked = true
    }

    private fun setDefaultSeekBarProgressValue(it: MyEvaluationDto) {
        defaultHoneyProgress = (it.honey * 2).roundToInt()
        defaultLearningProgress = (it.learning * 2).roundToInt()
        defaultSatisfactionProgress = (it.satisfaction * 2).roundToInt()
    }

    private fun setLectureProfessorName(it: MyEvaluationDto) {
        binding.writeLectureName.text = it.lectureName
        binding.writeProfessor.text = it.professor
    }

    private fun setSeekBarProgress() {
        /*
        * progress 초기화를 하지 않을 경우
        * 강의평가 쓰기 -> 꿀강 지수 2, 배움 지수 4로 설정 -> 완료
        * 위 과정 진행 후 다시 강의평가 쓰기를 클릭할 경우
        * 꿀강 지수 및 배움 지수가 0으로 초기화 되지 않고 2와 4로 남아있음
        * AVD 및 V20에서는 해당 문제가 발생하지만 갤럭시 s10e 에서는 발생하지 않음
        *
        * 해당 문제를 해결하기 위해 progress 초기화
        * */
        binding.satisfactionSeekBar.progress = defaultHoneyProgress
        binding.learningSeekBar.progress = defaultLearningProgress
        binding.honeySeekBar.progress = defaultSatisfactionProgress
    }

    private fun goToLectureInfoFragment() {
        val action = NavGraphDirections.actionGlobalLectureInfoFragment(lectureId = lectureId)
        findNavController().navigate(action)
    }

    private fun goToMyPostFragment() {
        val action = NavGraphDirections.actionGlobalMyPostFragment()
        findNavController().navigate(action)
    }
}