package com.skyner.sourceworxdms.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skyner.sourceworxdms.ui.theme.ButtonPrimary
import com.skyner.sourceworxdms.ui.theme.TextPrimary
import com.skyner.sourceworxdms.ui.theme.TextSecondary

@Composable
fun SourceworxAlertDialog(
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirmClick: () -> Unit,
    dismissButtonText: String? = null,
    onDismissClick: (() -> Unit)? = null,
    icon: ImageVector? = null,
    iconTint: Color = ButtonPrimary
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary
                ),
                textAlign = TextAlign.Start
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonPrimary
                )
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = dismissButtonText?.let {
            {
                TextButton(
                    onClick = { onDismissClick?.invoke() }
                ) {
                    Text(dismissButtonText)
                }
            }
        },
        icon = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = iconTint,
        titleContentColor = TextPrimary,
        textContentColor = TextSecondary
    )
}
