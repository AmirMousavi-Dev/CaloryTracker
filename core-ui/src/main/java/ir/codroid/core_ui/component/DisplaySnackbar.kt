package ir.codroid.core_ui.component

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
fun displaySnackBar(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    message: String,
    actionLabel: String = "",
    withDismissAction: Boolean = false,
    onDismissed: () -> Unit,
    onActionPerformed: () -> Unit
) {

    scope.launch {
        val snackBarResult = snackBarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = SnackbarDuration.Short
        )
        when (snackBarResult) {
            SnackbarResult.Dismissed -> onDismissed()
            SnackbarResult.ActionPerformed -> onActionPerformed()
        }
    }

}