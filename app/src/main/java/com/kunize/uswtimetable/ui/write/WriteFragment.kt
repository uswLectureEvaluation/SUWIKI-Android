package com.kunize.uswtimetable.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.remote.LectureEvaluationEditDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationPostDto
import com.kunize.uswtimetable.data.remote.LectureExamEditDto
import com.kunize.uswtimetable.data.remote.LectureExamPostDto
import com.kunize.uswtimetable.databinding.FragmentWriteBinding
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.dataclass.MyExamInfo
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.WriteFragmentTitle
import com.kunize.uswtimetable.util.seekbarChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding

    private val writeViewModel: WriteViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var semesterDialog: SemesterDialog? = null
    private var testDialog: TestDialog? = null
    private val args: WriteFragmentArgs by navArgs()
    private var lectureName = ""
    private var professorName = ""

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
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        binding.viewModel = writeViewModel
        binding.lifecycleOwner = this

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
            binding.easyRadioButton,
            binding.normalRadioButton,
            binding.difficultRadioButton
        )

        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        setInitValueWhenWrite(args)
        setFragmentViewType(args)
        setInitValueWhenEditMyEvaluation(args)
        setInitValueWhenEditMyExam(args)
        setSeekBarListener()
        setSeekBarProgress()

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.finishButton.setOnClickListener {
            CoroutineScope(IO).launch {

                val emptyMsg = when(binding.writeType.text.toString()) {
                    WriteFragmentTitle.WRITE_EVALUATION, WriteFragmentTitle.EDIT_MY_EVALUATION  -> {
                        when {
                            binding.teamRadioGroup.checkedRadioButtonId == -1 -> "조모임을 선택해주세요!"
                            binding.taskRadioGroup.checkedRadioButtonId == -1 -> "과제를 선택해주세요!"
                            binding.gradeRadioGroup.checkedRadioButtonId == -1 -> "학점을 선택해주세요!"
                            binding.writeContent.text.toString().isBlank() -> "내용을 입력해주세요!"
                            else -> ""
                        }
                    }
                    else -> when {
                        getTestContentString().isBlank() -> "시험 내용을 선택해주세요!"
                        binding.difficultyGroup.checkedRadioButtonId == -1 ->"난이도를 선택해주세요!"
                        binding.writeContent.text.toString().isBlank() -> "내용을 입력해주세요!"
                        else -> ""
                    }
                }

                if(emptyMsg.isNotBlank()) {
                    withContext(Main) {
                        Toast.makeText(requireContext(), emptyMsg, Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val response = when(binding.writeType.text.toString()) {
                    WriteFragmentTitle.WRITE_EVALUATION -> writeViewModel.postLectureEvaluation(lectureId, getLectureEvaluationInfo())
                    WriteFragmentTitle.WRITE_EXAM -> writeViewModel.postLectureExam(lectureId, getLectureExamInfo())
                    WriteFragmentTitle.EDIT_MY_EVALUATION -> writeViewModel.updateLectureEvaluation(lectureId, getLectureEvaluationEditInfo())
                    else -> writeViewModel.updateLectureExam(lectureId, getLectureExamEditInfo())
                }

                withContext(Main) {
                    if (response.isSuccessful) {
                        if (args.myExamInfo == null && args.myEvaluation == null)
                            goToLectureInfoFragment()
                        else
                            goToMyPostFragment()
                    }
                    else {
                        writeViewModel.handleError(response.code())
                    }
                }
            }
        }

        writeViewModel.toastViewModel.toastLiveData.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(
                requireContext(),
                writeViewModel.toastViewModel.toastMessage,
                Toast.LENGTH_LONG
            ).show()
        })

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
                lectureName,
                professorName,
                writeViewModel.semesterText.value!!,
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
            var testContent = getTestContentString()
            testContent = testContent.dropLast(2)

            var testDifficulty = ""
            difficultyRadioBtnList.forEach {
                if(it.isChecked)
                    testDifficulty = it.text.toString()
            }

            //TODO 수정
            info = LectureExamPostDto(
                lectureName,
                professorName,
                writeViewModel.semesterText.value!!,
                testContent,
                testDifficulty,
                writeContent.text.toString()
            )
        }
        return info
    }

    private fun getLectureExamEditInfo(): LectureExamEditDto {
        val info: LectureExamEditDto
        with(binding) {
            var testContent = getTestContentString()
            testContent = testContent.dropLast(2)

            var testDifficulty = ""
            difficultyRadioBtnList.forEach {
                if(it.isChecked)
                    testDifficulty = it.text.toString()
            }

            //TODO 수정
            info = LectureExamEditDto(
                writeViewModel.semesterText.value!!,
                testContent,
                testDifficulty,
                writeContent.text.toString()
            )
        }
        return info
    }

    private fun getTestContentString(): String {
        var testContent = ""
        testContentCheckBoxList.forEach { checkBox ->
            if (checkBox.isChecked)
                testContent += checkBox.text.toString() + ", "
        }
        return testContent
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
                writeViewModel.semesterText.value!!,
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
            val list = it.semesterList.replace(" ","").split(",")
            setSemesterSpinnerList(list)
            //TODO MyExamInfo 수정되면 선택한 시험 종류 설정 ("기말고사" 선택 시 -> spinner 항목 기말고사 선택되게 해야함)
            writeViewModel.initSemesterText(it.semester)
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
            for (dataString in it.examType.replace(" ","").split(",")) {
                if (checkBox.text == dataString)
                    checkBox.isChecked = true
            }
        }
    }

    private fun setLectureProfessorName(it: MyExamInfo) {
        lectureName = it.subject
        professorName = it.professor
    }

    private fun setFragmentViewType(args: WriteFragmentArgs) {
        if (args.isEvaluation) {
            binding.tvTestType.visibility = View.GONE
            binding.clSelectTestType.visibility = View.GONE
            binding.testGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EVALUATION
            binding.finishButton.text = WriteFragmentTitle.FINISH_EVALUATION
            binding.writeContent.hint = "강의평가를 작성해주세요."
        } else {
            binding.lectureGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EXAM
            binding.finishButton.text = WriteFragmentTitle.FINISH_EXAM
            binding.writeContent.hint = "시험에 대한 정보, 공부법, 문제유형 등을 자유롭게 작성해주세요."
        }
    }

    private fun setInitValueWhenWrite(args: WriteFragmentArgs) {
        args.lectureProfessorName?.let {
            lectureName = it.subject
            professorName = it.professor
            setSemesterSpinnerList(args.lectureProfessorName!!.semester.replace(" ","").split(","))
        }
        setTestSpinnerList(listOf("중간고사","기말고사","쪽지","기타"))
    }

    private fun setTestSpinnerList(list: List<String>) {
        writeViewModel.initTestText(list[0])

        binding.clSelectTestType.setOnClickListener {
            testDialog = TestDialog(context as AppCompatActivity, writeViewModel, list)
            testDialog?.show()
        }

        writeViewModel.dialogTestClickEvent.observe(viewLifecycleOwner, EventObserver {
            testDialog?.dismiss()
        })
    }

    private fun setSemesterSpinnerList(list: List<String>) {
        writeViewModel.initSemesterText(list[0])

        binding.clSelectSemester.setOnClickListener {
            semesterDialog = SemesterDialog(context as AppCompatActivity, writeViewModel, list)
            semesterDialog?.show()
        }

        writeViewModel.dialogSemesterClickEvent.observe(viewLifecycleOwner, EventObserver {
            semesterDialog?.dismiss()
        })
    }

    private fun setInitValueWhenEditMyEvaluation(args: WriteFragmentArgs) {
        args.myEvaluation?.let {
            setLectureProfessorName(it)
            setDefaultSeekBarProgressValue(it)
            setTeamRadioBtn(it)
            setTaskRadioBtn(it)
            setGradeRadioBtn(it)

            val list = it.semesterList.replace(" ","").split(",")
            setSemesterSpinnerList(list)
            writeViewModel.initSemesterText(it.selectedSemester)
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
        lectureName = it.lectureName
        professorName = it.professor
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