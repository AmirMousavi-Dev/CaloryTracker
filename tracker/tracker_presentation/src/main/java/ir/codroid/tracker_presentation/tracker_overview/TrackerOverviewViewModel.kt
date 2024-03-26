package ir.codroid.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.core.domain.preferences.Preferences
import ir.codroid.tracker_domain.use_case.TrackerUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCase: TrackerUseCase
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewContract.State())
        private set

    private var getFoodsForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewContract.Event) {
        when (event) {
            is TrackerOverviewContract.Event.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCase.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }

            is TrackerOverviewContract.Event.OnNextDayClick -> {
                state = state.copy(date = state.date.plusDays(1))
                refreshFoods()
            }

            is TrackerOverviewContract.Event.OnPreviousDayClick -> {
                state = state.copy(date = state.date.minusDays(1))
                refreshFoods()
            }

            is TrackerOverviewContract.Event.OnToggleMealClick -> {
                state = state.copy(meals = state.meals.map {
                    if (it.name == event.meal.name) {
                        it.copy(isExpanded = !it.isExpanded)
                    } else it
                })
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCase
            .getFoodsForDateUseCase(state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCase.calculateMealNutrientsUseCase(foods)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal = nutrientsResult.mealNutrients[it.mealType]
                            ?: return@map it.copy(
                                carbs = 0,
                                fat = 0,
                                protein = 0,
                                calories = 0
                            )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            fat = nutrientsForMeal.fat,
                            protein = nutrientsForMeal.protein,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)

    }
}