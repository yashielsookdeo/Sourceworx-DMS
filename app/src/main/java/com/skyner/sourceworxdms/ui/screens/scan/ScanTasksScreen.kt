package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.data.models.Project
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanTasksScreen(
    onBackClick: () -> Unit,
    onNewScanClick: () -> Unit,
    onProjectClick: (String) -> Unit,
    viewModel: ScanTasksViewModel = viewModel(factory = ScanTasksViewModelFactory(Application()))
) {
    // Collect state from ViewModel
    val projects by viewModel.scannedProjects.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Scan Tasks",
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextPrimary
                        )
                    }
                    IconButton(onClick = { /* Filter action */ }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = TextPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewScanClick() },
                containerColor = ButtonPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Scan",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (isLoading) {
                // Show loading indicator
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SourceworxLoadingIndicator(message = "Loading scanned documents...")
                }
            } else if (error != null) {
                // Show error message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SourceworxErrorMessage(
                        message = error ?: "Unknown error",
                        onRetry = { viewModel.refreshData() }
                    )
                }
            } else {
                // Status summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatusCard(
                        title = "Pending",
                        count = viewModel.getPendingTasksCount(),
                        color = Warning
                    )

                    StatusCard(
                        title = "In Progress",
                        count = projects.size,
                        color = Primary
                    )

                    StatusCard(
                        title = "Completed",
                        count = viewModel.getCompletedTasksCount(),
                        color = Success
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Tasks list
                Text(
                    text = "Scanned Documents",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (projects.isEmpty()) {
                    // Show empty state
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No scanned documents available",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                } else {
                    // Display projects from seed data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(projects) { project ->
                            ProjectScanItem(
                                project = project,
                                onProjectClick = { onProjectClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    count: Int,
    color: Color
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = color
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = color
            )
        }
    }
}

@Composable
fun ScanTaskItem(task: ScanTask) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Task icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getStatusColor(task.status).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = getStatusColor(task.status),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ID: ${task.id} • ${task.documentType}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = task.dueDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(getStatusColor(task.status).copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = task.status.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = getStatusColor(task.status)
                        )
                    }
                }
            }

            IconButton(onClick = { /* View task details */ }) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View Details",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun getStatusColor(status: TaskStatus): Color {
    return when (status) {
        TaskStatus.PENDING -> Color(0xFFFFA000) // Amber
        TaskStatus.IN_PROGRESS -> Color(0xFF1976D2) // Blue
        TaskStatus.COMPLETED -> Color(0xFF43A047) // Green
        TaskStatus.REJECTED -> Color(0xFFE53935) // Red
    }
}

// Sample data
data class ScanTask(
    val id: String,
    val title: String,
    val documentType: String,
    val dueDate: String,
    val status: TaskStatus
)

enum class TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, REJECTED
}

val sampleScanTasks = listOf(
    ScanTask(
        id = "DOC-2023-001",
        title = "Invoice #12345",
        documentType = "Invoice",
        dueDate = "Today, 5:00 PM",
        status = TaskStatus.PENDING
    ),
    ScanTask(
        id = "DOC-2023-002",
        title = "Contract Agreement",
        documentType = "Legal Document",
        dueDate = "Tomorrow, 12:00 PM",
        status = TaskStatus.IN_PROGRESS
    ),
    ScanTask(
        id = "DOC-2023-003",
        title = "Employee Records",
        documentType = "HR Document",
        dueDate = "Oct 20, 2023",
        status = TaskStatus.COMPLETED
    ),
    ScanTask(
        id = "DOC-2023-004",
        title = "Purchase Order #789",
        documentType = "Purchase Order",
        dueDate = "Oct 18, 2023",
        status = TaskStatus.REJECTED
    ),
    ScanTask(
        id = "DOC-2023-005",
        title = "Meeting Minutes",
        documentType = "Internal Document",
        dueDate = "Oct 22, 2023",
        status = TaskStatus.PENDING
    ),
    ScanTask(
        id = "DOC-2023-006",
        title = "Project Proposal",
        documentType = "Business Document",
        dueDate = "Oct 25, 2023",
        status = TaskStatus.IN_PROGRESS
    )
)

@Composable
fun ProjectScanItem(project: Project, onProjectClick: (String) -> Unit = {}) {
    SourceworxCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onProjectClick(project.Name) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Project icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(try {
                        Color(android.graphics.Color.parseColor(project.Category.Color)).copy(alpha = 0.1f)
                    } catch (e: Exception) {
                        Color.LightGray // Fallback color
                    }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = project.Icon,
                    color = try {
                        Color(android.graphics.Color.parseColor(project.Category.Color))
                    } catch (e: Exception) {
                        Color.Black // Fallback color
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.Name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = project.Description.take(50) + if (project.Description.length > 50) "..." else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(try {
                                Color(android.graphics.Color.parseColor(project.Category.Color)).copy(alpha = 0.1f)
                            } catch (e: Exception) {
                                Color.LightGray // Fallback color
                            })
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = project.Category.Title,
                            style = MaterialTheme.typography.labelSmall,
                            color = try {
                                Color(android.graphics.Color.parseColor(project.Category.Color))
                            } catch (e: Exception) {
                                Color.Black // Fallback color
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${project.Tasks.size} tasks",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            IconButton(onClick = { onProjectClick(project.Name) }) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View Details",
                    tint = Primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanTasksScreenPreview() {
    SourceworxDMSTheme {
        // Create a mock ViewModel for preview
        val mockViewModel = ScanTasksViewModel(Application())

        ScanTasksScreen(
            onBackClick = {},
            onNewScanClick = {},
            onProjectClick = {},
            viewModel = mockViewModel
        )
    }
}
