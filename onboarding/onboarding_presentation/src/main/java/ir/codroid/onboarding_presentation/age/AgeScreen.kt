package ir.codroid.onboarding_presentation.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.core.R
import ir.codroid.core.util.UiEvent
import ir.codroid.core_ui.LocalSpacing
import ir.codroid.core_ui.component.displaySnackBar
import ir.codroid.onboarding_presentation.components.ActionButton
import ir.codroid.onboarding_presentation.components.UnitTextField

@Composable
fun AgeScreen(
    snackBarHostState: SnackbarHostState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AgeViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    displaySnackBar(
                        scope = this,
                        snackBarHostState = snackBarHostState,
                        message = event.message.asString(context),
                        onDismissed = {

                        }) {

                    }
                }

                else -> Unit

            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(value = viewModel.age, onValueChange = {
                viewModel.onAgeEnter(it)
            }, unit = stringResource(id = R.string.years))
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            isEnable = true,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}