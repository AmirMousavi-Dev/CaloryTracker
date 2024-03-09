package ir.codroid.tracker_presentation.search

import ir.codroid.tracker_domain.model.MealType
import ir.codroid.tracker_domain.model.TrackableFood
import ir.codroid.tracker_domain.model.TrackedFood
import java.time.LocalDate

interface SearchContract {


    data class State(
        val query: String = "",
        val isHintVisible: Boolean = false,
        val isSearching: Boolean = false,
        val trackableFood: List<TrackableFoodUiState> = emptyList()
    )

    sealed class Event {
        data class OnQueryChange(val query: String) : Event()
        data object OnSearch : Event()
        data class OnToggleTrackableFood(val food: TrackableFood) : Event()
        data class OnAmountForFoodChange(
            val food: TrackableFood,
            val amount: String
        ) : Event()

        data class OnTrackedFoodClick(
            val food: TrackedFood,
            val mealType: MealType,
            val date: LocalDate
        ) : Event()

        data class OnSearchFocusChange(val isFocused: Boolean) : Event()
    }
}