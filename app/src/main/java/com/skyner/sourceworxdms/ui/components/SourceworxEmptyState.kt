package com.skyner.sourceworxdms.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skyner.sourceworxdms.ui.theme.TextPrimary
import com.skyner.sourceworxdms.ui.theme.TextSecondary

@Composable
fun SourceworxEmptyState(
    message: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    actionContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextSecondary.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            ),
            textAlign = TextAlign.Center
        )
        
        if (actionContent != null) {
            Spacer(modifier = Modifier.height(16.dp))
            actionContent()
        }
    }
}
