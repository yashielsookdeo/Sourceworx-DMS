package com.skyner.sourceworxdms.ui.screens.problem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*
import com.skyner.sourceworxdms.ui.theme.SourceworxDMSTheme

// Problem category data class
data class ProblemCategory(
    val id: Int,
    val name: String,
    val icon: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportProblemScreen(
    onBackClick: () -> Unit
) {
    var selectedTask by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ProblemCategory?>(null) }
    var problemDescription by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Sample tasks
    val tasks = listOf(
        "CASE-001 - Chris Hani Baragwanath Academic Hospital",
        "CASE-002 - Charlotte Maxeke Hospital",
        "CASE-003 - Steve Biko Academic Hospital",
        "CASE-034 - Steve Biko Academic Hospital",
        "CASE-041 - Charlotte Maxeke Hospital"
    )

    // Problem categories
    val problemCategories = listOf(
        ProblemCategory(
            id = 1,
            name = "Document Quality",
            icon = { Icon(Icons.Default.Image, contentDescription = null, tint = Color.White) }
        ),
        ProblemCategory(
            id = 2,
            name = "Missing Pages",
            icon = { Icon(Icons.Default.BrokenImage, contentDescription = null, tint = Color.White) }
        ),
        ProblemCategory(
            id = 3,
            name = "Wrong Document",
            icon = { Icon(Icons.Default.Description, contentDescription = null, tint = Color.White) }
        ),
        ProblemCategory(
            id = 4,
            name = "System Error",
            icon = { Icon(Icons.Default.Error, contentDescription = null, tint = Color.White) }
        ),
        ProblemCategory(
            id = 5,
            name = "Scanner Issue",
            icon = { Icon(Icons.Default.Scanner, contentDescription = null, tint = Color.White) }
        ),
        ProblemCategory(
            id = 6,
            name = "Other",
            icon = { Icon(Icons.Default.MoreHoriz, contentDescription = null, tint = Color.White) }
        )
    )

    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Report Problem",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title
            Text(
                text = "Select Task",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Task dropdown
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                OutlinedTextField(
                    value = selectedTask,
                    onValueChange = { },
                    readOnly = true,
                    placeholder = { Text("Select a task") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    tasks.forEach { task ->
                        DropdownMenuItem(
                            text = { Text(task) },
                            onClick = {
                                selectedTask = task
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Problem category title
            Text(
                text = "Problem Category",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Problem categories grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 24.dp)
            ) {
                items(problemCategories) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Problem description
            Text(
                text = "Problem Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = problemDescription,
                onValueChange = { problemDescription = it },
                placeholder = { Text("Describe the problem in detail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Priority and due date info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Priority
                Column {
                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    )

                    Text(
                        text = "High",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE53935) // Red color
                        )
                    )
                }

                // Due date
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Due Date",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    )

                    Text(
                        text = "2025-04-20",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Log Problem button
            SourceworxPrimaryButton(
                text = "Log Problem",
                onClick = { showSuccessDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }

        // Success dialog
        if (showSuccessDialog) {
            SourceworxAlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    onBackClick()
                },
                title = "Problem Logged",
                message = "Your problem has been logged successfully. Our support team will address it as soon as possible.",
                confirmButtonText = "OK",
                onConfirmClick = {
                    showSuccessDialog = false
                    onBackClick()
                },
                icon = Icons.Default.CheckCircle,
                iconTint = Success
            )
        }
    }
}

// Category item composable
@Composable
fun CategoryItem(
    category: ProblemCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary else CardBackground
        ),
        border = BorderStroke(1.dp, if (isSelected) Primary else Divider)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon container
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color.White.copy(alpha = 0.2f) else Primary),
                contentAlignment = Alignment.Center
            ) {
                category.icon()
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Category name
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color.White else TextPrimary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportProblemScreenPreview() {
    SourceworxDMSTheme {
        ReportProblemScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    SourceworxDMSTheme {
        CategoryItem(
            category = ProblemCategory(
                id = 1,
                name = "Document Quality",
                icon = { Icon(Icons.Default.Image, contentDescription = null, tint = Color.White) }
            ),
            isSelected = true,
            onClick = {}
        )
    }
}
