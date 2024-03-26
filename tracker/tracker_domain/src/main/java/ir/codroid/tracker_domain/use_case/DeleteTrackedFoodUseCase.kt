package ir.codroid.tracker_domain.use_case

import ir.codroid.tracker_domain.model.TrackedFood
import ir.codroid.tracker_domain.repository.TrackerRepository

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackedFood
    ) {
        repository.deleteTrackedFood(food)
    }
}