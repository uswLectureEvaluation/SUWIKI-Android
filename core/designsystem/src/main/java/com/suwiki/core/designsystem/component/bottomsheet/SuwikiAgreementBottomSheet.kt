package com.suwiki.core.designsystem.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiAgreementBottomSheet(
  sheetState: SheetState = rememberModalBottomSheetState(),
  isSheetOpen: Boolean,
  buttonEnabled: Boolean,
  isCheckedTerm: Boolean,
  onClickTermCheckIcon: () -> Unit,
  onClickTermArrowIcon: () -> Unit,
  isCheckedPersonalPolicy: Boolean,
  onClickPersonalCheckIcon: () -> Unit,
  onClickPersonalArrowIcon: () -> Unit,
  onDismissRequest: () -> Unit = {},
) {
  SuwikiBottomSheet(
    isSheetOpen = isSheetOpen,
    sheetState = sheetState,
    onDismissRequest = onDismissRequest,
  ) {
    Column(
      modifier = Modifier.padding(top = 38.dp, start = 24.dp, end = 24.dp, bottom = 71.dp),
    ) {
      Text(
        text = stringResource(R.string.bottom_sheet_agreement_title),
        style = SuwikiTheme.typography.header2,
      )

      Spacer(modifier = Modifier.size(27.dp))

      SuwikiAgreementContainer(
        isChecked = isCheckedTerm,
        onClickCheckIcon = onClickTermCheckIcon,
        onClickArrowIcon = onClickTermArrowIcon,
        text = stringResource(R.string.agreement_container_term),
      )

      SuwikiAgreementContainer(
        isChecked = isCheckedPersonalPolicy,
        onClickCheckIcon = onClickPersonalCheckIcon,
        onClickArrowIcon = onClickPersonalArrowIcon,
        text = stringResource(R.string.agreement_container_personal_policy),
      )

      Spacer(modifier = Modifier.size(40.dp))

      SuwikiContainedLargeButton(
        text = stringResource(R.string.bottom_sheet_agreement_button),
        enabled = buttonEnabled,
        clickable = buttonEnabled,
      )
    }
  }
}

@Composable
private fun SuwikiAgreementContainer(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  text: String,
  onClickCheckIcon: () -> Unit = {},
  onClickArrowIcon: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(vertical = 18.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier.suwikiClickable(rippleEnabled = false, onClick = onClickCheckIcon),
      painter = painterResource(id = R.drawable.ic_check_filled),
      tint = if (isChecked) Primary else GrayDA,
      contentDescription = "",
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = text,
      style = SuwikiTheme.typography.body2,
      color = Black,
    )
    Image(
      modifier = Modifier
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickArrowIcon),
      painter = painterResource(id = R.drawable.ic_arrow_gray_right),
      contentDescription = "",
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SuwikiAgreementBottomSheetPreview() {
  SuwikiTheme {
    SuwikiAgreementBottomSheet(
      isSheetOpen = true,
      buttonEnabled = false,
      isCheckedTerm = false,
      onClickTermCheckIcon = {},
      onClickTermArrowIcon = {},
      isCheckedPersonalPolicy = false,
      onClickPersonalCheckIcon = {},
      onClickPersonalArrowIcon = {},
      onDismissRequest = {},
    )
  }
}
