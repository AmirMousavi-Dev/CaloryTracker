package ir.codroid.onboarding_presentation.nutrient_goal

interface NutrientGoalContract {
    data class NutrientGoalState
        (
        val carbsRatio: String = "40",
        val proteinRatio: String = "30",
        val fatRatio: String = "40",
    )

    sealed class NutrientGoalEvent {
        data class OnCarbRatioEnter(val ratio: String) : NutrientGoalEvent()
        data class OnProteinRatioEnter(val ratio: String) : NutrientGoalEvent()
        data class OnFatRatioEnter(val ratio: String) : NutrientGoalEvent()
        data object OnNextClick : NutrientGoalEvent()
    }

}