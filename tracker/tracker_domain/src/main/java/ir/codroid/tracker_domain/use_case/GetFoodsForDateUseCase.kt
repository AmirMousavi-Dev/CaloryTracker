package ir.codroid.tracker_domain.use_case

import ir.codroid.tracker_domain.model.TrackedFood
import ir.codroid.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsForDateUseCase(
    private val repository: TrackerRepository
) {
    operator fun invoke(
        date: LocalDate,
    ): Flow<List<TrackedFood>> {
        return repository.getFoodForDate(date)
    }
}