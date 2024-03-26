package ir.codroid.onboarding_presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ir.codroid.core_ui.LocalSpacing

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnable,
        shape = RoundedCornerShape(100.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall)
        )
    }
}