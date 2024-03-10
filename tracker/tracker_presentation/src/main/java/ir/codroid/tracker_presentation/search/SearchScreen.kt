package ir.codroid.tracker_presentation.search

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ir.codroid.core.R
import ir.codroid.core.util.UiEvent
import ir.codroid.core_ui.LocalSpacing
import ir.codroid.core_ui.component.displaySnackBar
import ir.codroid.tracker_presentation.search.component.SearchTextField

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
            onSearch = {
                viewModel.onEvent(SearchContract.Event.OnSearch)
            },
            onFocusChanged = {
                viewModel.onEvent(SearchContract.Event.OnSearchFocusChange(it.isFocused))
            })
    }
}