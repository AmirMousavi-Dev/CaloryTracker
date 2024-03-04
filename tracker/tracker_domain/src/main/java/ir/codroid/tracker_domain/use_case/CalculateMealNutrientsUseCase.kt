package ir.codroid.tracker_domain.use_case

import ir.codroid.core.domain.model.ActivityLevel
import ir.codroid.core.domain.model.Gender
import ir.codroid.core.domain.model.GoalType
import ir.codroid.core.domain.model.UseInfo
import ir.codroid.core.domain.preferences.Preferences
import ir.codroid.tracker_domain.model.MealType
import ir.codroid.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val preferences: Preferences
) {

    operator fun invoke(
        trackedFoods: List<TrackedFood>
    ): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val food = entry.value
                MealNutrients(
                    carbs = food.sumOf { it.carbs },
                    protein = food.sumOf { it.protein },
                    fat = food.sumOf { it.fat },
                    calories = food.sumOf { it.calories },
                    mealType = type,
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val caloryGoal = dailyCaloryRequirement(userInfo)
        val carbsGoal = (caloryGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt()
        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )

    }


    private fun bmr(userInfo: UseInfo): Int {
        return when (userInfo.gender) {
            Gender.Male ->
                (66.47f + 13.75f * userInfo.weight + 5f * userInfo.height - 6.75f * userInfo.age)
                    .roundToInt()

            Gender.Female ->
                (665.09f + 9.56f * userInfo.weight + 1.84f * userInfo.height - 4.67 * userInfo.age)
                    .roundToInt()
        }
    }

    private fun dailyCaloryRequirement(userInfo: UseInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            ActivityLevel.Low -> 1.2f
            ActivityLevel.Medium -> 1.3f
            ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when (userInfo.goalType) {
            GoalType.LoseWeight -> -500
            GoalType.KeepWeight -> 0
            GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }


    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )

}