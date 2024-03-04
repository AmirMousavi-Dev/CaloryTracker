package ir.codroid.tracker_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ir.codroid.core.domain.preferences.Preferences
import ir.codroid.tracker_domain.repository.TrackerRepository
import ir.codroid.tracker_domain.use_case.CalculateMealNutrientsUseCase
import ir.codroid.tracker_domain.use_case.DeleteTrackedFoodUseCase
import ir.codroid.tracker_domain.use_case.GetFoodsForDateUseCase
import ir.codroid.tracker_domain.use_case.SearchFoodUseCase
import ir.codroid.tracker_domain.use_case.TrackFoodUseCase
import ir.codroid.tracker_domain.use_case.TrackerUseCase

@Module
@InstallIn(ViewModelComponent::class)
class TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun providesTrackerUseCase(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCase {
        return TrackerUseCase(
            trackFoodUseCase = TrackFoodUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(repository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(repository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
    }
}