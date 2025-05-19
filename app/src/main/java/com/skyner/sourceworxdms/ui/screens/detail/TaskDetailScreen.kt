package com.skyner.sourceworxdms.ui.screens.detail

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.data.models.Project
import com.skyner.sourceworxdms.data.models.ProjectTask
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*

@Composable
fun TaskDetailScreen(
    projectId: String,
    onBackClick: () -> Unit,
    onScanClick: (String) -> Unit,
    viewModel: TaskDetailViewModel = viewModel(factory = TaskDetailViewModelFactory(Application()))
) {
    // Collect state from ViewModel
    val project by viewModel.project.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Load project details when the screen is first displayed
    LaunchedEffect(projectId) {
        viewModel.loadProject(projectId)
    }

    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Task Details",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                SourceworxLoadingIndicator(message = "Loading task details...")
            } else if (error != null) {
                SourceworxErrorMessage(
                    message = "Failed to load task details: ${error ?: "Unknown error"}",
                    onRetry = { viewModel.loadProject(projectId) }
                )
            } else if (project == null) {
                SourceworxEmptyState(
                    message = "No task details found",
                    icon = Icons.Default.Error
                )
            } else {
                project?.let { projectData ->
                    TaskDetailContent(
                        project = projectData,
                        onScanClick = onScanClick
                    )
                }
            }
        }
    }
}

@Composable
fun TaskDetailContent(
    project: Project,
    onScanClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Project header with name and description
        ProjectHeader(project)

        Spacer(modifier = Modifier.height(24.dp))

        // Category and tags
        CategoryAndTags(project)

        Spacer(modifier = Modifier.height(24.dp))

        // Tasks section
        SourceworxSectionHeader(
            title = "Tasks",
            icon = Icons.Default.Assignment
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Task list
        project.Tasks.forEach { task ->
            TaskItem(
                task = task,
                projectId = project.Name,
                requiresScanning = task.Title.contains("Scan") || 
                                  task.Title.contains("Document") || 
                                  task.Title.contains("Record"),
                onScanClick = onScanClick
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Add some space at the bottom
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProjectHeader(project: Project) {
    Column {
        // Project name
        Text(
            text = project.Name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Project description
        Text(
            text = project.Description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary
            )
        )
    }
}

@Composable
fun CategoryAndTags(project: Project) {
    Column {
        // Category
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Category:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            val categoryColor = try {
                Color(android.graphics.Color.parseColor(project.Category.Color))
            } catch (e: Exception) {
                Primary // Fallback color
            }
            
            SourceworxTag(
                text = project.Category.Title,
                color = categoryColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tags
        if (project.Tags.isNotEmpty()) {
            Text(
                text = "Tags:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                project.Tags.forEach { tag ->
                    val tagColor = try {
                        Color(android.graphics.Color.parseColor(tag.Color))
                    } catch (e: Exception) {
                        Primary // Fallback color
                    }
                    
                    SourceworxTag(
                        text = tag.Title,
                        color = tagColor
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: ProjectTask,
    projectId: String,
    requiresScanning: Boolean,
    onScanClick: (String) -> Unit
) {
    SourceworxCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Task title and completion status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Checkbox for completion status
                    Checkbox(
                        checked = task.IsCompleted,
                        onCheckedChange = null, // Read-only for now
                        colors = CheckboxDefaults.colors(
                            checkedColor = Primary,
                            uncheckedColor = TextSecondary
                        )
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // Task title
                    Text(
                        text = task.Title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (task.IsCompleted) FontWeight.Normal else FontWeight.Medium,
                            color = if (task.IsCompleted) TextSecondary else TextPrimary
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Status indicator
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (task.IsCompleted) Success.copy(alpha = 0.1f)
                            else Primary.copy(alpha = 0.1f)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (task.IsCompleted) "Completed" else "Pending",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (task.IsCompleted) Success else Primary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            
            // If the task requires scanning and is not completed, show scan button
            if (requiresScanning && !task.IsCompleted) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Task details for scanning
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Info.copy(alpha = 0.05f))
                        .padding(12.dp)
                ) {
                    // Document type
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = Info,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Document Type: ${task.Title}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextPrimary
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Due date (simulated)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Info,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Required by: 7 days",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextPrimary
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Special instructions (simulated)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Info,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Instructions: Ensure all pages are clearly visible and properly aligned.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextPrimary
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Scan Now button
                    SourceworxPrimaryButton(
                        text = "Scan Now",
                        onClick = { onScanClick(projectId) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    SourceworxDMSTheme {
        val sampleProject = Project(
            Name = "CASE # 20250913/0045",
            Description = "Medical malpractice case involving surgical complications at City Hospital.",
            Icon = "📋",
            Category = com.skyner.sourceworxdms.data.models.Category(
                Title = "Allocated",
                Color = "#3068DF"
            ),
            Tags = listOf(
                com.skyner.sourceworxdms.data.models.Tag(
                    Title = "Medical",
                    Color = "#3068DF"
                ),
                com.skyner.sourceworxdms.data.models.Tag(
                    Title = "High Priority",
                    Color = "#FF4500"
                )
            ),
            Tasks = listOf(
                ProjectTask(
                    Title = "Summons",
                    IsCompleted = true
                ),
                ProjectTask(
                    Title = "Medical Records",
                    IsCompleted = true
                ),
                ProjectTask(
                    Title = "Scan Documents",
                    IsCompleted = false
                ),
                ProjectTask(
                    Title = "Letter of Demands",
                    IsCompleted = false
                )
            )
        )
        
        TaskDetailContent(
            project = sampleProject,
            onScanClick = {}
        )
    }
}
