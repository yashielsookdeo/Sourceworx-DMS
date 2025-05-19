package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksToScanScreen(
    onBackClick: () -> Unit,
    onScanClick: (String, String) -> Unit,
    viewModel: TasksToScanViewModel = viewModel(factory = TasksToScanViewModelFactory(Application()))
) {
    // Collect state from ViewModel
    val tasks by viewModel.filteredTasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    val tabs = listOf("All Tasks", "Urgent", "Today", "This Week")

    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Tasks to Scan",
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = CardBackground,
                contentColor = Primary,
                divider = {
                    HorizontalDivider(color = Divider, thickness = 1.dp)
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { viewModel.setSelectedTab(index) },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    )
                }
            }

            // Loading and error states
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SourceworxLoadingIndicator(message = "Loading tasks...")
                }
            } else if (error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SourceworxErrorMessage(
                        message = error ?: "Unknown error",
                        onRetry = { viewModel.refreshData() }
                    )
                }
            } else {
                // Task count summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ${tasks.size} tasks",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sort by: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )

                        TextButton(
                            onClick = { /* Change sort order */ },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Primary
                            )
                        ) {
                            Text(
                                text = "Due Date",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(color = Divider, thickness = 1.dp)

                // Tasks list
                if (tasks.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        SourceworxEmptyState(
                            message = "No tasks found for the selected filter",
                            icon = Icons.Default.Assignment
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(tasks) { task ->
                            TaskToScanItem(
                                task = task,
                                onScanClick = { onScanClick(task.id, task.title) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskToScanItem(
    task: TaskToScan,
    onScanClick: () -> Unit
) {
    SourceworxCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(getPriorityColor(task.priority).copy(alpha = 0.1f))
                            .border(
                                width = 1.dp,
                                color = getPriorityColor(task.priority).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = getPriorityColor(task.priority),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "ID: ${task.id}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }

                // Priority indicator
                SourceworxTag(
                    text = task.priority.name,
                    color = getPriorityColor(task.priority)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Task details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Document type
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Folder,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = task.documentType,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                // Due date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = task.dueDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (task.priority == TaskPriority.HIGH)
                            Error
                        else
                            TextSecondary
                    )
                }

                // Assigned by
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = task.assignedBy,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { /* View details */ },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Primary.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "View",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Button(
                    onClick = onScanClick,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonPrimary,
                        contentColor = Color.White
                    ),
                    enabled = !task.isScanned
                ) {
                    Icon(
                        imageVector = Icons.Default.Scanner,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Scan",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.LOW -> Success // Green
        TaskPriority.MEDIUM -> Warning // Amber/Yellow
        TaskPriority.HIGH -> Error // Red
    }
}

// Sample data
data class TaskToScan(
    val id: String,
    val title: String,
    val documentType: String,
    val dueDate: String,
    val assignedBy: String,
    val priority: TaskPriority,
    val isScanned: Boolean = false
)

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

val sampleTasksToScan = listOf(
    TaskToScan(
        id = "DOC-2023-101",
        title = "Client Contract - ABC Corp",
        documentType = "Contract",
        dueDate = "Today, 5:00 PM",
        assignedBy = "John Smith",
        priority = TaskPriority.HIGH
    ),
    TaskToScan(
        id = "DOC-2023-102",
        title = "Financial Report Q3",
        documentType = "Financial",
        dueDate = "Tomorrow, 12:00 PM",
        assignedBy = "Sarah Johnson",
        priority = TaskPriority.MEDIUM
    ),
    TaskToScan(
        id = "DOC-2023-103",
        title = "Employee Onboarding Forms",
        documentType = "HR",
        dueDate = "Oct 20, 2023",
        assignedBy = "Michael Brown",
        priority = TaskPriority.LOW
    ),
    TaskToScan(
        id = "DOC-2023-104",
        title = "Vendor Agreement - XYZ Inc",
        documentType = "Agreement",
        dueDate = "Oct 18, 2023",
        assignedBy = "Emily Davis",
        priority = TaskPriority.HIGH
    ),
    TaskToScan(
        id = "DOC-2023-105",
        title = "Project Proposal - New Office",
        documentType = "Proposal",
        dueDate = "Oct 22, 2023",
        assignedBy = "David Wilson",
        priority = TaskPriority.MEDIUM
    )
)

@Preview(showBackground = true)
@Composable
fun TasksToScanScreenPreview() {
    SourceworxDMSTheme {
        // Create a mock ViewModel for preview
        val mockViewModel = TasksToScanViewModel(Application())

        TasksToScanScreen(
            onBackClick = {},
            onScanClick = { _, _ -> },
            viewModel = mockViewModel
        )
    }
}
