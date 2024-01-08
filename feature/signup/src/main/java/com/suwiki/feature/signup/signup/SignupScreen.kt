package com.suwiki.feature.signup.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.util.LaunchedEffectWithLifecycle
import com.suwiki.core.ui.util.TEXT_FIELD_DEBOUNCE
import com.suwiki.feature.signup.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(FlowPreview::class)
@Composable
fun SignupRoute(
  viewModel: SignupViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  navigateSignupComplete: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  val passwordFocusRequester = remember { FocusRequester() }
  val passwordConfirmFocusRequester = remember { FocusRequester() }
  val emailFocusRequester = remember { FocusRequester() }

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is SignupSideEffect.HandleException -> handleException(sideEffect.throwable)
      SignupSideEffect.NavigateSignupComplete -> navigateSignupComplete()
      SignupSideEffect.PopBackStack -> popBackStack()
      SignupSideEffect.FocusEmail -> {
        awaitFrame()
        emailFocusRequester.requestFocus()
      }

      SignupSideEffect.FocusPassword -> {
        awaitFrame()
        passwordFocusRequester.requestFocus()
      }

      SignupSideEffect.FocusPasswordConfirm -> {
        awaitFrame()
        passwordConfirmFocusRequester.requestFocus()
      }
    }
  }

  LaunchedEffectWithLifecycle(key1 = uiState.idState.id) {
    snapshotFlow { uiState.idState.id }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach(viewModel::checkIdInvalid)
      .launchIn(this)
  }

  LaunchedEffectWithLifecycle(key1 = uiState.passwordState) {
    snapshotFlow { uiState.passwordState }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach {
        viewModel.checkPasswordInvalid(it.password)
        viewModel.checkPasswordConfirmInvalid(
          password = it.password,
          passwordConfirm = it.passwordConfirm,
        )
      }
      .launchIn(this)
  }

  LaunchedEffectWithLifecycle(key1 = uiState.emailState.email) {
    snapshotFlow { uiState.emailState.email }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach(viewModel::checkEmailInvalid)
      .launchIn(this)
  }

  SignupScreen(
    uiState = uiState,
    passwordFocusRequester = passwordFocusRequester,
    passwordConfirmFocusRequester = passwordConfirmFocusRequester,
    emailFocusRequester = emailFocusRequester,
    onValueChangeId = viewModel::updateId,
    onClickIdTextFieldClearButton = { viewModel.updateId("") },
    onClickIdOverlapButton = viewModel::checkIdOverlap,
    onValueChangePassword = viewModel::updatePassword,
    onClickPasswordTextFieldClearButton = { viewModel.updatePassword("") },
    onClickPasswordEyeIcon = viewModel::toggleShowPasswordValue,
    onValueChangePasswordConfirm = viewModel::updatePasswordConfirm,
    onClickPasswordConfirmTextFieldClearButton = { viewModel.updatePasswordConfirm("") },
    onClickPasswordConfirmEyeIcon = viewModel::toggleShowPasswordConfirmValue,
    onValueChangeEmail = viewModel::updateEmail,
    onClickEmailTextFieldClearButton = { viewModel.updateEmail("") },
    onClickSendEmailAuthButton = viewModel::signup,
  )
}

@Composable
fun SignupScreen(
  uiState: SignupState = SignupState(),
  passwordFocusRequester: FocusRequester = remember { FocusRequester() },
  passwordConfirmFocusRequester: FocusRequester = remember { FocusRequester() },
  emailFocusRequester: FocusRequester = remember { FocusRequester() },
  onValueChangeId: (String) -> Unit = {},
  onClickIdTextFieldClearButton: () -> Unit = {},
  onClickIdOverlapButton: () -> Unit = {},
  onValueChangePassword: (String) -> Unit = {},
  onClickPasswordTextFieldClearButton: () -> Unit = {},
  onClickPasswordEyeIcon: () -> Unit = {},
  onValueChangePasswordConfirm: (String) -> Unit = {},
  onClickPasswordConfirmTextFieldClearButton: () -> Unit = {},
  onClickPasswordConfirmEyeIcon: () -> Unit = {},
  onValueChangeEmail: (String) -> Unit = {},
  onClickEmailTextFieldClearButton: () -> Unit = {},
  onClickSendEmailAuthButton: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 63.dp, bottom = 28.dp, start = 24.dp, end = 24.dp),
  ) {
    Column {
      Text(
        text = stringResource(id = uiState.titleResId),
        style = SuwikiTheme.typography.header1,
      )

      Spacer(modifier = Modifier.size(26.dp))

      Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        with(uiState.emailState) {
          AnimatedVisibility(visible = showEmailTextField) {
            SuwikiRegularTextField(
              modifier = Modifier.focusRequester(emailFocusRequester),
              label = stringResource(R.string.word_email),
              placeholder = stringResource(R.string.textfield_email_placeholder),
              value = email,
              onValueChange = onValueChangeEmail,
              onClickClearButton = onClickEmailTextFieldClearButton,
              helperText = stringResource(id = emailHelperTextResId),
              isError = isErrorEmailTextField,
            )
          }
        }

        with(uiState.passwordState) {
          AnimatedVisibility(visible = showPasswordConfirmTextField) {
            SuwikiRegularTextField(
              modifier = Modifier.focusRequester(passwordConfirmFocusRequester),
              label = stringResource(R.string.word_password_confirm),
              placeholder = stringResource(R.string.textfield_password_placeholder),
              value = passwordConfirm,
              onValueChange = onValueChangePasswordConfirm,
              onClickClearButton = onClickPasswordConfirmTextFieldClearButton,
              helperText = stringResource(id = passwordConfirmHelperTextResId),
              isError = isErrorPasswordConfirmTextField,
              showEyeIcon = true,
              showValue = showPasswordConfirmValue,
              onClickEyeIcon = onClickPasswordConfirmEyeIcon,
            )
          }

          AnimatedVisibility(visible = showPasswordTextField) {
            SuwikiRegularTextField(
              modifier = Modifier.focusRequester(passwordFocusRequester),
              label = stringResource(R.string.word_password),
              placeholder = stringResource(R.string.textfield_password_placeholder),
              value = password,
              onValueChange = onValueChangePassword,
              onClickClearButton = onClickPasswordTextFieldClearButton,
              helperText = stringResource(id = passwordHelperTextResId),
              isError = isErrorPasswordTextField,
              showEyeIcon = true,
              showValue = showPasswordValue,
              onClickEyeIcon = onClickPasswordEyeIcon,
            )
          }
        }

        with(uiState.idState) {
          AnimatedVisibility(visible = showIdTextField) {
            SuwikiRegularTextField(
              label = stringResource(R.string.word_id),
              placeholder = stringResource(R.string.textfield_id_placeholder),
              value = id,
              onValueChange = onValueChangeId,
              onClickClearButton = onClickIdTextFieldClearButton,
              helperText = stringResource(id = idHelperTextResId),
              isError = isErrorIdTextField,
            )
          }
        }
      }
    }

    if (uiState.emailState.showSendAuthEmailButton) {
      SuwikiContainedLargeButton(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .imePadding(),
        text = stringResource(R.string.signup_screen_receive_auth_email),
        onClick = onClickSendEmailAuthButton,
      )
    }

    if (uiState.idState.showIdCheckButton) {
      SuwikiContainedLargeButton(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .imePadding(),
        text = stringResource(R.string.signup_screen_id_overlap),
        onClick = onClickIdOverlapButton,
      )
    }

    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
  SuwikiTheme {
    SignupScreen()
  }
}
