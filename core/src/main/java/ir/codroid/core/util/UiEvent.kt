package ir.codroid.core.util

sealed class UiEvent {
    data object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}