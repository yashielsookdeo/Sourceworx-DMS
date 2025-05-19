package com.skyner.sourceworxdms.ui.screens.dashboard

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.data.models.Project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@Composable
fun DashboardScreen(
    onNavigateToAlerts: () -> Unit,
    onNavigateToScanTasks: () -> Unit,
    onNavigateToTasksToScan: () -> Unit,
    onNavigateToReportProblem: () -> Unit,
    onNavigateToTaskDetail: (String) -> Unit,
    viewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory(Application()))
) {
    // Collect state from ViewModel
    val projects by viewModel.allocatedProjects.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with welcome message and notification icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Welcome message
                Column {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = "Zanele",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )
                    )
                }

                // Notification icons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Warning icon (yellow) - Report Problem
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFC107))
                            .clickable { onNavigateToReportProblem() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warnings",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Notification icon (red) with badge
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE53935))
                                .clickable { onNavigateToAlerts() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }

                        // Notification badge
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .align(Alignment.TopEnd)
                                .offset(x = 3.dp, y = (-3).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "6",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFE53935),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }

            // Quick action buttons (first row)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Manage Profile button
                ActionButton(
                    title = "Manage Profile",
                    icon = Icons.Default.Person,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle profile management */ }
                )

                // View Stats button
                ActionButton(
                    title = "View Stats",
                    icon = Icons.AutoMirrored.Outlined.List,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle stats view */ }
                )
            }

            // My Tasks button (full width)
            ActionButton(
                title = "My Tasks",
                icon = Icons.AutoMirrored.Outlined.List,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onClick = { onNavigateToTasksToScan() }
            )

            // New Tasks section
            Text(
                text = "New Tasks",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading) {
                // Show loading indicator with message
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading tasks...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            } else if (error != null) {
                // Enhanced error message with retry button
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Unable to load tasks",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.refreshData() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Retry",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Retry")
                        }
                    }
                }
            } else if (projects.isEmpty()) {
                // Enhanced empty state with icon
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "No Tasks",
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No tasks available",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Check back later for new tasks",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // Display projects as task cards
                projects.forEach { project ->
                    TaskCard(
                        caseNumber = project.Name,
                        location = project.Description.take(50) + if (project.Description.length > 50) "..." else "",
                        priority = project.Tags.firstOrNull()?.Title ?: "Medium",
                        dueDate = "Due soon",
                        isUrgent = project.Category.Title == "Allocated",
                        onAccept = { onNavigateToTaskDetail(project.Name) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Rejected Tasks section
            Text(
                text = "Rejected Tasks",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Rejected task cards
            RejectedTaskCard(
                caseNumber = "CASE-034",
                location = "Steve Biko Academic Hospital",
                priority = "High",
                dueDate = "2025-04-20",
                reason = "Poor Quality",
                affectedPages = "3, 6, 53, 89",
                onRescan = { onNavigateToTasksToScan() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RejectedTaskCard(
                caseNumber = "CASE-041",
                location = "Charlotte Maxeke Hospital",
                priority = "Medium",
                dueDate = "2025-04-22",
                reason = "Blurry Images",
                affectedPages = "12, 15, 18",
                onRescan = { onNavigateToTasksToScan() }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Pending Review section
            Text(
                text = "Pending Review",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Pending review card
            PendingReviewCard(
                caseNumber = "CASE-006",
                location = "Tembisa Hospital",
                priority = "High",
                dueDate = "2025-04-14",
                submittedDate = "2025-04-13",
                status = "Awaiting supervisor review"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // End of dashboard content
        }
    }
}

@Composable
fun ActionButton(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2196F3) // Blue color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun TaskCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    isUrgent: Boolean,
    onAccept: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Case number and urgent tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caseNumber,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                if (isUrgent) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFFFEBEE))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Urgent",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color(0xFFE53935),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Priority
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Priority: $priority",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Due date
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Due: $dueDate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accept button
            Button(
                onClick = onAccept,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(
                    text = "Accept Task",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun RejectedTaskCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    reason: String,
    affectedPages: String,
    onRescan: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Case number and rejected tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caseNumber,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFEBEE))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Rejected",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFFE53935),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Priority
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Priority: $priority",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rejection reason
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Reason: $reason",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Affected pages
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Affected Pages: $affectedPages",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Due date
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Due: $dueDate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Re-scan button
            Button(
                onClick = onRescan,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935) // Red color
                )
            ) {
                Text(
                    text = "Re-scan Document",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun DashboardScreenPreview() {
    SourceworxDMSTheme {
        // Create a mock ViewModel for preview
        val mockViewModel = DashboardViewModel(Application())

        DashboardScreen(
            onNavigateToAlerts = {},
            onNavigateToScanTasks = {},
            onNavigateToTasksToScan = {},
            onNavigateToReportProblem = {},
            onNavigateToTaskDetail = {},
            viewModel = mockViewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    SourceworxDMSTheme {
        ActionButton(
            title = "Manage Profile",
            icon = Icons.Default.Person,
            modifier = Modifier.width(180.dp),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    SourceworxDMSTheme {
        TaskCard(
            caseNumber = "CASE-001",
            location = "Chris Hani Baragwanath Academic Hospital",
            priority = "High",
            dueDate = "2025-04-15",
            isUrgent = true,
            onAccept = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RejectedTaskCardPreview() {
    SourceworxDMSTheme {
        RejectedTaskCard(
            caseNumber = "CASE-034",
            location = "Steve Biko Academic Hospital",
            priority = "High",
            dueDate = "2025-04-20",
            reason = "Poor Quality",
            affectedPages = "3, 6, 53, 89"
        )
    }
}

@Composable
fun PendingReviewCard(
    caseNumber: String,
    location: String,
    priority: String,
    dueDate: String,
    submittedDate: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Case number and pending tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caseNumber,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF8E1))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Pending",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFFFFA000),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Priority
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Priority: $priority",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Due date
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Due: $dueDate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Submitted date
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Submitted: $submittedDate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Status
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Status: $status",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PendingReviewCardPreview() {
    SourceworxDMSTheme {
        PendingReviewCard(
            caseNumber = "CASE-006",
            location = "Tembisa Hospital",
            priority = "High",
            dueDate = "2025-04-14",
            submittedDate = "2025-04-13",
            status = "Awaiting supervisor review"
        )
    }
}
