package ir.codroid.onboarding_presentation.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.core.R
import ir.codroid.core.domain.model.ActivityLevel
import ir.codroid.core.util.UiEvent
import ir.codroid.core_ui.LocalSpacing
import ir.codroid.onboarding_presentation.components.ActionButton
import ir.codroid.onboarding_presentation.components.SelectableButton

@Composable
fun ActivityScreen(
    onNextClick: () -> Unit,
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
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
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    isSelected = viewModel.selectedActivity is ActivityLevel.Low,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityClick(ActivityLevel.Low) },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = viewModel.selectedActivity is ActivityLevel.Medium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityClick(ActivityLevel.Medium) },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = viewModel.selectedActivity is ActivityLevel.High,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityClick(ActivityLevel.High) },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            isEnable = true,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}