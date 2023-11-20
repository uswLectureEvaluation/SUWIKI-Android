package com.mangbaam.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mangbaam.presentation.ui.theme.UswtimetableTheme
import com.suwiki.domain.openmajor.usecase.GetBookmarkedOpenMajorListUseCase
import com.suwiki.domain.signup.usecase.CheckEmailOverlapUseCase
import com.suwiki.domain.signup.usecase.CheckIdOverlapUseCase
import com.suwiki.domain.signup.usecase.SignUpUseCase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var useCase: CheckEmailOverlapUseCase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      UswtimetableTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {
          Greeting(name = "Suwiki", useCase = useCase)
        }
      }
    }
  }
}

@Composable
fun Greeting(
  name: String,
  modifier: Modifier = Modifier,
  useCase: CheckEmailOverlapUseCase,
) {
  LaunchedEffect(key1 = Unit) {
    useCase(
      email = "pos1070@suwon.ac.kr"
    )
      .onSuccess {
        Timber.d("$it")
      }
      .onFailure {
        Timber.d("$it")
      }
  }

  Text(
    text = "Hello $name!",
    modifier = modifier,
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  UswtimetableTheme {
  }
}
