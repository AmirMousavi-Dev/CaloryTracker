package ir.codroid.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import ir.codroid.core.R
import ir.codroid.core.util.UiEvent
import ir.codroid.core_ui.LocalSpacing
import ir.codroid.core_ui.component.displaySnackBar
import ir.codroid.tracker_domain.model.MealType
import ir.codroid.tracker_presentation.search.component.SearchTextField
import ir.codroid.tracker_presentation.search.component.TrackableFoodItem
import java.time.LocalDate

@ExperimentalCoilApi
@Composable
fun SearchScreen(
    snackBarHostState: SnackbarHostState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    displaySnackBar(
                        scope = this,
                        snackBarHostState = snackBarHostState,
                        message = event.message.asString(context),
                        onDismissed = {},
                        onActionPerformed = {}
                    )
                    keyboardController?.hide()
                }

                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {

        Text(
            text = stringResource(
                id = R.string.add_meal, mealName
            ),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        SearchTextField(
            text = state.query,
            onValueChange = {
                viewModel.onEvent(
                    SearchContract
                        .Event
                        .OnQueryChange(it)
                )
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchContract.Event.OnSearch)
            },
            onFocusChanged = {
                viewModel.onEvent(SearchContract.Event.OnSearchFocusChange(it.isFocused))
            })

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.trackableFood) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchContract.Event.OnToggleTrackableFood(food.food))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchContract.Event.OnAmountForFoodChange(
                                food.food,
                                it
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchContract.Event.OnTrackedFoodClick(
                                food = food.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFood.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}