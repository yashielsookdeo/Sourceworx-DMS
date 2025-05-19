package com.skyner.sourceworxdms.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import android.widget.Toast
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skyner.sourceworxdms.R
import com.skyner.sourceworxdms.ui.components.SureScanButton
import com.skyner.sourceworxdms.ui.theme.SignUpTextColor
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("admin@example.com") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Main content card with shadow
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.surescan_logo),
                        contentDescription = "SureScan Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp)
                    )

                    // App name
                    Text(
                        text = "SureScan",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // Email field label
                    Text(
                        text = stringResource(R.string.email),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    // Email field
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F8FF))
                            .padding(0.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F8FF),
                            unfocusedContainerColor = Color(0xFFF5F8FF),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Password field label
                    Text(
                        text = stringResource(R.string.password),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    // Password field
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F8FF))
                            .padding(0.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F8FF),
                            unfocusedContainerColor = Color(0xFFF5F8FF),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black
                        ),
                        placeholder = { Text("Enter your password") }
                    )

                    // Error message
                    if (isError) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sign In button
                    SureScanButton(
                        text = stringResource(R.string.sign_in),
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                isError = true
                                errorMessage = "Email and password cannot be empty"
                            } else if (!isValidEmail(email)) {
                                isError = true
                                errorMessage = "Please enter a valid email address"
                            } else {
                                // In a real app, you would validate credentials here
                                onLoginSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Don't have an account? Sign up
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.dont_have_account),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            )
                        )
                        // Sign up button bypasses login and takes user directly to dashboard
                        val context = LocalContext.current
                        TextButton(
                            onClick = {
                                Toast.makeText(context, "Signing up and proceeding to dashboard", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            },
                            contentPadding = PaddingValues(start = 4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.sign_up),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SignUpTextColor,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Email validation function
private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    return email.matches(emailRegex.toRegex())
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreview() {
    SourceworxDMSTheme {
        LoginScreen(onLoginSuccess = {})
    }
}
