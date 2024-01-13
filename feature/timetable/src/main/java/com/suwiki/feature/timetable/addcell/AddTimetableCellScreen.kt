package com.suwiki.feature.timetable.addcell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTextButton
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.core.designsystem.component.card.SuwikiClassInformationCard
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBar
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.OnBottomReached
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.timetable.R
import com.suwiki.feature.timetable.addcell.model.SchoolLevel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddTimetableCellRoute(
  viewModel: AddTimetableCellViewModel = hiltViewModel(),
  selectedOpenMajor: String,
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
  @Suppress("detekt:UnusedParameter")
  onShowToast: (String) -> Unit,
  navigateOpenMajor: (String) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is AddTimetableCellSideEffect.HandleException -> handleException(sideEffect.throwable)
      AddTimetableCellSideEffect.NavigateAddCustomTimetableCell -> TODO()
      is AddTimetableCellSideEffect.NavigateOpenMajor -> navigateOpenMajor(sideEffect.selectedOpenMajor)
      AddTimetableCellSideEffect.PopBackStack -> popBackStack()
      AddTimetableCellSideEffect.ScrollToTop -> scope.launch {
        awaitFrame()
        listState.animateScrollToItem(0)
      }
    }
  }

  LaunchedEffect(selectedOpenMajor) {
    viewModel.updateSelectedOpenMajor(selectedOpenMajor)
  }

  LaunchedEffect(key1 = viewModel) {
    viewModel.initData()
  }

  listState.OnBottomReached {
    viewModel.getOpenLectureList(needClear = false)
  }

  AddTimetableCellScreen(
    uiState = uiState,
    listState = listState,
    onClickOpenMajorFilterContainer = navigateOpenMajor,
    onDismissSchoolLevelBottomSheet = viewModel::hideGradeBottomSheet,
    onClickSchoolLevelFilterContainer = viewModel::showGradeBottomSheet,
    onClickSchoolLevelBottomSheetItem = { position ->
      viewModel.hideGradeBottomSheet()
      viewModel.updateSchoolLevelPosition(SchoolLevel.entries[position])
    },
    onClickBack = viewModel::popBackStack,
    onClickSearchButton = viewModel::searchOpenLecture,
    onClickClearButton = { viewModel.updateSearchValue("") },
    onValueChangeSearch = viewModel::updateSearchValue,
  )
}

@Composable
fun AddTimetableCellScreen(
  uiState: AddTimetableCellState = AddTimetableCellState(),
  listState: LazyListState = rememberLazyListState(),
  onClickOpenMajorFilterContainer: (String) -> Unit = {},
  onDismissSchoolLevelBottomSheet: () -> Unit = {},
  onClickSchoolLevelFilterContainer: () -> Unit = {},
  onClickSchoolLevelBottomSheetItem: (Int) -> Unit = {},
  onClickBack: () -> Unit = {},
  onClickSearchButton: (String) -> Unit = {},
  onClickClearButton: () -> Unit = {},
  onValueChangeSearch: (String) -> Unit = {},
) {
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTextButton(
      buttonText = stringResource(R.string.add_timetable_screen_add_lecture),
      onClickBack = onClickBack,
    )

    Spacer(modifier = Modifier.size(12.dp))

    Row(
      modifier = Modifier.padding(horizontal = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      FilterContainer(
        filterName = stringResource(R.string.word_open_major),
        value = uiState.selectedOpenMajor,
        onClick = { onClickOpenMajorFilterContainer(uiState.selectedOpenMajor) },
      )

      FilterContainer(
        filterName = stringResource(R.string.word_school_level),
        value = stringResource(id = uiState.schoolLevel.stringResId),
        onClick = onClickSchoolLevelFilterContainer,
      )
    }

    SuwikiSearchBar(
      modifier = Modifier.padding(top = 10.dp),
      placeholder = stringResource(R.string.add_timetable_cell_search_bar_placeholder),
      onClickSearchButton = onClickSearchButton,
      value = uiState.searchValue,
      onClickClearButton = onClickClearButton,
      onValueChange = onValueChangeSearch,
    )

    if (uiState.openLectureList.isEmpty()) {
      EmptyText(
        stringResource(R.string.word_empty_result),
      )
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize(),
      contentPadding = PaddingValues(bottom = 24.dp),
      state = listState,
    ) {
      items(
        items = uiState.openLectureList,
        key = { it.id },
      ) { lectureEvaluation ->
        with(lectureEvaluation) {
          SuwikiClassInformationCard(
            className = name,
            professor = professorName,
            cellInfo = originalCellList.toText(context),
            grade = stringResource(id = R.string.word_num_school_level, grade),
            classType = type,
            openMajor = major,
            onClick = { /*TODO*/ },
            onClickAdd = {},
          )
        }
      }
    }
  }

  if (uiState.isLoading) {
    LoadingScreen()
  }

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showSchoolLevelBottomSheet,
    onDismissRequest = onDismissSchoolLevelBottomSheet,
    onClickItem = onClickSchoolLevelBottomSheetItem,
    itemList = SchoolLevel.entries.map { stringResource(id = it.stringResId) }.toPersistentList(),
    title = stringResource(R.string.word_school_level),
    selectedPosition = uiState.schoolLevel.ordinal,
  )
}

@Composable
private fun FilterContainer(
  filterName: String,
  value: String,
  onClick: () -> Unit,
) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(size = 10.dp))
      .border(width = 1.dp, color = GrayF6, shape = RoundedCornerShape(size = 10.dp))
      .suwikiClickable(onClick = onClick)
      .padding(vertical = 6.dp, horizontal = 9.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(text = filterName, style = SuwikiTheme.typography.body6, color = Gray6A)
    Spacer(modifier = Modifier.size(4.dp))
    Text(text = value, style = SuwikiTheme.typography.body6, color = Primary)
    Icon(
      painter = painterResource(id = R.drawable.ic_dropdown_arrow_down),
      contentDescription = null,
      tint = Gray6A,
    )
  }
}

@Composable
private fun EmptyText(
  text: String = "",
) {
  Text(
    modifier = Modifier
      .padding(52.dp)
      .fillMaxSize(),
    textAlign = TextAlign.Center,
    text = text,
    style = SuwikiTheme.typography.header4,
    color = Gray95,
  )
}

@Preview
@Composable
fun AddTimetableCellScreenPreview() {
  SuwikiTheme {
    AddTimetableCellScreen()
  }
}
