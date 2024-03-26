package ir.codroid.tracker_presentation.tracker_overview

import ir.codroid.tracker_domain.model.TrackedFood
import java.time.LocalDate

interface TrackerOverviewContract {

    data class State(
        val totalCarbs: Int = 0,
        val totalProtein: Int = 0,
        val totalFat: Int = 0,
        val totalCalories: Int = 0,
        val carbsGoal: Int = 0,
        val proteinGoal: Int = 0,
        val fatGoal: Int = 0,
        val caloriesGoal: Int = 0,
        val date: LocalDate = LocalDate.now(),
        val trackedFoods: List<TrackedFood> = emptyList(),
        val meals: List<Meal> = defaultMeals,
    )

    sealed class Event {
        data object OnNextDayClick : Event()
        data object OnPreviousDayClick : Event()
        data class OnToggleMealClick(val meal: Meal) : Event()
        data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood) : Event()
    }
}