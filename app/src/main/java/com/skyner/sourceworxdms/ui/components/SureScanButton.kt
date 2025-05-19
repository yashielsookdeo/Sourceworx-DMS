package com.skyner.sourceworxdms.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skyner.sourceworxdms.ui.theme.SignInButtonColor
import com.skyner.sourceworxdms.ui.theme.SignInButtonTextColor
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@Composable
fun SureScanButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SignInButtonColor,
            contentColor = SignInButtonTextColor,
            disabledContainerColor = SignInButtonColor.copy(alpha = 0.5f),
            disabledContentColor = SignInButtonTextColor.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SureScanButtonPreview() {
    SourceworxDMSTheme {
        SureScanButton(
            text = "Sign In",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}
