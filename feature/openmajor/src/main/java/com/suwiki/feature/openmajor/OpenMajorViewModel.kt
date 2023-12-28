package com.suwiki.feature.openmajor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OpenMajorViewModel @Inject constructor() : ContainerHost<OpenMajorState, OpenMajorSideEffect>, ViewModel() {
  override val container: Container<OpenMajorState, OpenMajorSideEffect> = container(OpenMajorState())
}
