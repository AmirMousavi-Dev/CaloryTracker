package ir.codroid.calorytracker.navigation

import androidx.navigation.NavController
import ir.codroid.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}