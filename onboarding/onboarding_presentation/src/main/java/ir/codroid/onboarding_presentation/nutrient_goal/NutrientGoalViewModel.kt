package ir.codroid.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.codroid.core.domain.preferences.Preferences
import ir.codroid.core.domain.use_case.FilterOutDigitsUseCase
import ir.codroid.core.util.UiEvent
import ir.codroid.onboarding_domain.use_case.ValidateNutrientsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase,
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalContract.NutrientGoalState())
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalContract.NutrientGoalEvent) {
        when (event) {
            is NutrientGoalContract.NutrientGoalEvent.OnCarbRatioEnter ->
                state = state.copy(carbsRatio = filterOutDigitsUseCase(event.ratio))

            is NutrientGoalContract.NutrientGoalEvent.OnFatRatioEnter ->
                state = state.copy(fatRatio = filterOutDigitsUseCase(event.ratio))


            is NutrientGoalContract.NutrientGoalEvent.OnProteinRatioEnter ->
                state = state.copy(proteinRatio = filterOutDigitsUseCase(event.ratio))

            NutrientGoalContract.NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrientsUseCase(
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText = state.fatRatio
                )
                when (result) {
                    is ValidateNutrientsUseCase.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }

                    is ValidateNutrientsUseCase.Result.Success -> {
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                }

            }

        }
    }

}