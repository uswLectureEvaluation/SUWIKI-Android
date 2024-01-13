package com.suwiki.feature.timetable.addcell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTextButton
import com.suwiki.core.designsystem.component.card.SuwikiClassInformationCard
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBar
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.timetable.R
import timber.log.Timber

@Composable
fun AddTimetableCellRoute(
  selectedOpenMajor: String,
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
  navigateOpenMajor: (String) -> Unit,
) {
  AddTimetableCellScreen(
    onClickOpenMajorFilterContainer = navigateOpenMajor,
  )
}

@Composable
fun AddTimetableCellScreen(
  onClickOpenMajorFilterContainer: (String) -> Unit = {},
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTextButton(
      buttonText = stringResource(R.string.add_timetable_screen_add_lecture),
      onClickBack = {},
    )

    Spacer(modifier = Modifier.size(12.dp))

    Row(
      modifier = Modifier.padding(horizontal = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      FilterContainer(
        filterName = stringResource(R.string.word_open_major),
        value = "전체",
        onClick = { onClickOpenMajorFilterContainer("전체") },
      )

      FilterContainer(
        filterName = stringResource(R.string.word_grade),
        value = "전체",
        onClick = {},
      )
    }

    SuwikiSearchBar(
      placeholder = stringResource(R.string.add_timetable_cell_search_bar_placeholder),
    )

    repeat(10) {
      SuwikiClassInformationCard(
        className = "과학적글쓰기와고전읽기2",
        professor = "김지수",
        day = "목",
        classPeriod = "6,7교시",
        location = "(미래 211)",
        grade = "2학년",
        classType = "기교",
        openMajor = "건설에너지공학과학부",
        onClick = { /*TODO*/ },
        onClickAdd = {}
      )
    }
  }
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

@Preview
@Composable
fun AddTimetableCellScreenPreview() {
  SuwikiTheme {
    AddTimetableCellScreen()
  }
}
