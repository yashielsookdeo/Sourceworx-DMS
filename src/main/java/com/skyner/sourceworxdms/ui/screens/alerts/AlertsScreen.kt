package com.skyner.sourceworxdms.ui.screens.alerts

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.data.models.Project
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    onBackClick: () -> Unit,
    viewModel: AlertsViewModel = viewModel(factory = AlertsViewModelFactory(Application()))
) {
    // Collect state from ViewModel
    val projects by viewModel.outstandingProjects.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Alerts",
                onBackClick = onBackClick,
                useCloseIcon = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (isLoading) {
                // Show loading indicator
                SourceworxLoadingIndicator(message = "Loading alerts...")
            } else if (error != null) {
                // Show error message with retry button
                SourceworxErrorMessage(
                    message = "Failed to load alerts: ${error ?: "Unknown error"}",
                    onRetry = { viewModel.refreshData() }
                )
            } else {
                // Outstanding Projects Section (from seed data)
                if (projects.isNotEmpty()) {
                    AlertSectionHeader(
                        icon = Icons.Default.Warning,
                        title = "Outstanding",
                        count = projects.size,
                        iconColor = Color(0xFFFF3300) // Red/Orange from seed data
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display outstanding projects
                    projects.forEach { project ->
                        OutstandingProjectCard(project = project)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    // Show empty state for outstanding projects
                    Text(
                        text = "No outstanding projects",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                }

                // Static sections for demonstration
                // Resolved Problems Section
                AlertSectionHeader(
                    icon = Icons.Default.CheckCircle,
                    title = "Resolved Problems",
                    count = 2,
                    iconColor = Color(0xFF4CAF50) // Green
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Resolved Problem Cards
                ResolvedProblemCard(
                    caseNumber = "CASE-006",
                    issueType = "Large File Size",
                    resolvedDate = "2025-04-14",
                    resolution = "Increased file size limit"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ResolvedProblemCard(
                    caseNumber = "CASE-008",
                    issueType = "System Error",
                    resolvedDate = "2025-04-13",
                    resolution = "System restart fixed the issue"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Rejected Section
                AlertSectionHeader(
                    icon = Icons.Default.Cancel,
                    title = "Rejected",
                    count = 2,
                    iconColor = Color(0xFFE53935) // Red
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rejected Cards
                RejectedCard(caseNumber = "CASE-004", priority = "High")

                Spacer(modifier = Modifier.height(8.dp))

                RejectedCard(caseNumber = "CASE-005", priority = "Medium")
            }
        }
    }
}

@Composable
fun AlertSectionHeader(
    icon: ImageVector,
    title: String,
    count: Int,
    iconColor: Color
) {
    SourceworxSectionHeader(
        title = "$title ($count)",
        icon = icon,
        iconTint = iconColor
    )
}

@Composable
fun ResolvedProblemCard(
    caseNumber: String,
    issueType: String,
    resolvedDate: String,
    resolution: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9) // Light green background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = caseNumber,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )

                Text(
                    text = issueType,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4CAF50) // Green text
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Resolved: $resolvedDate",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray
                )
            )

            Text(
                text = resolution,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray
                )
            )
        }
    }
}

@Composable
fun OutstandingProjectCard(project: Project) {
    SourceworxCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = project.Name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )

                // Display tag if available
                project.Tags.firstOrNull()?.let { tag ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = try {
                                    Color(android.graphics.Color.parseColor(tag.Color))
                                } catch (e: Exception) {
                                    Color.Gray // Fallback color
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tag.Title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display description
            Text(
                text = project.Description.take(100) + if (project.Description.length > 100) "..." else "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display tasks
            Text(
                text = "Tasks: ${project.Tasks.size}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            )
        }
    }
}

@Composable
fun RejectedCard(
    caseNumber: String,
    priority: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE) // Light red background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = caseNumber,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )

            Text(
                text = priority,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE53935) // Red text
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun AlertsScreenPreview() {
    SourceworxDMSTheme {
        // Create a mock ViewModel for preview
        val mockViewModel = AlertsViewModel(Application())

        AlertsScreen(
            onBackClick = {},
            viewModel = mockViewModel
        )
    }
}
