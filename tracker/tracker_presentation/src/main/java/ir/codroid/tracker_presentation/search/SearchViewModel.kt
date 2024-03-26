package ir.codroid.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.core.R
import ir.codroid.core.domain.use_case.FilterOutDigitsUseCase
import ir.codroid.core.util.UiEvent
import ir.codroid.core.util.UiText
import ir.codroid.tracker_domain.use_case.TrackerUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCase: TrackerUseCase,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchContract.State())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnQueryChange -> {
                state = state.copy(query = event.query)
            }

            is SearchContract.Event.OnAmountForFoodChange -> {
                state = state.copy(trackableFood = state.trackableFood.map {
                    if (it.food == event.food)
                        it.copy(amount = filterOutDigitsUseCase(event.amount))
                    else it
                })
            }

            is SearchContract.Event.OnSearch -> {
                executeSearch()
            }

            is SearchContract.Event.OnSearchFocusChange -> {
                state = state.copy(isHintVisible = !event.isFocused && state.query.isBlank())
            }

            is SearchContract.Event.OnToggleTrackableFood -> {
                state = state.copy(trackableFood = state.trackableFood.map {
                    if (it.food == event.food)
                        it.copy(isExpanded = !it.isExpanded)
                    else it
                })
            }

            is SearchContract.Event.OnTrackedFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(isSearching = true, trackableFood = emptyList())
            trackerUseCase.searchFoodUseCase(state.query)
                .onSuccess { foods ->
                    state = state.copy(
                        isSearching = false,
                        trackableFood = foods.map {
                            TrackableFoodUiState(it)
                        },
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_something_went_wrong)))
                }
        }

    }

    private fun trackFood(event: SearchContract.Event.OnTrackedFoodClick) {

        viewModelScope.launch {
            val uiState = state.trackableFood.find { it.food.equals(event.food) }
            trackerUseCase.trackFoodUseCase(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

}