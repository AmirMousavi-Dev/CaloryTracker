package ir.codroid.tracker_domain.use_case

import ir.codroid.tracker_domain.model.MealType
import ir.codroid.tracker_domain.model.TrackableFood
import ir.codroid.tracker_domain.model.TrackedFood
import ir.codroid.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPre100g / 100f) * amount).roundToInt(),
                protein = ((food.proteinPre100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPre100g / 100f) * amount).roundToInt(),
                calories = ((food.caloriesPre100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date
            )
        )
    }
}