package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyner.sourceworxdms.ui.components.*
import com.skyner.sourceworxdms.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ScanningState {
    READY, // Ready to scan
    SCANNING, // Camera is active, waiting for user to capture
    PROCESSING, // Processing the captured image
    REVIEW, // Reviewing the scanned document
    COMPLETE // Scanning complete
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScannerScreen(
    taskId: String,
    projectId: String,
    onBackClick: () -> Unit,
    onScanComplete: () -> Unit
) {
    var scanningState by remember { mutableStateOf(ScanningState.READY) }
    var capturedImageUri by remember { mutableStateOf<String?>(null) }
    var processingProgress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = processingProgress,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
        label = "Processing Progress"
    )
    val coroutineScope = rememberCoroutineScope()

    // Mock function to simulate document scanning
    fun captureDocument() {
        scanningState = ScanningState.PROCESSING
        coroutineScope.launch {
            // Simulate processing time
            for (i in 1..10) {
                processingProgress = i / 10f
                delay(300) // Simulate processing delay
            }
            // Set mock captured image (in a real app, this would be a URI to the captured image)
            capturedImageUri = "captured_document"
            scanningState = ScanningState.REVIEW
        }
    }

    // Function to complete the scanning process
    fun completeScan() {
        scanningState = ScanningState.COMPLETE
        coroutineScope.launch {
            delay(1000) // Show completion message briefly
            onScanComplete()
        }
    }

    Scaffold(
        topBar = {
            SourceworxTopAppBar(
                title = "Document Scanner",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (scanningState) {
                ScanningState.READY -> {
                    // Initial instructions screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DocumentScanner,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(80.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Ready to Scan",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Position the document within the frame and ensure good lighting for best results.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        SourceworxPrimaryButton(
                            text = "Start Scanning",
                            onClick = { scanningState = ScanningState.SCANNING }
                        )
                    }
                }

                ScanningState.SCANNING -> {
                    // Camera preview with document frame
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Mock camera preview (in a real app, this would be a CameraPreview)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        )

                        // Document frame overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .aspectRatio(0.7f) // A4 paper aspect ratio
                                .align(Alignment.Center)
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )

                        // Capture button
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 32.dp)
                        ) {
                            IconButton(
                                onClick = { captureDocument() },
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Camera,
                                    contentDescription = "Capture",
                                    tint = Primary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        // Instructions overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            Text(
                                text = "Position document within frame",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                ScanningState.PROCESSING -> {
                    // Processing screen with progress indicator
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.size(80.dp),
                            color = Primary,
                            trackColor = Primary.copy(alpha = 0.2f),
                            strokeWidth = 8.dp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Processing Document",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Enhancing image quality and preparing document...",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${(animatedProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        )
                    }
                }

                ScanningState.REVIEW -> {
                    // Document review screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Review Document",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Document preview (mock)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                                .padding(1.dp)
                        ) {
                            // In a real app, this would be an Image composable with the captured document
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Description,
                                    contentDescription = null,
                                    tint = TextSecondary.copy(alpha = 0.3f),
                                    modifier = Modifier
                                        .size(120.dp)
                                        .align(Alignment.Center)
                                )

                                Text(
                                    text = "Document Preview",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextSecondary,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Document details
                        SourceworxCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Document Details",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Folder,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Project: $projectId",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextPrimary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Assignment,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Task: $taskId",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextPrimary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "Date: ${java.util.Date()}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextPrimary
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            SourceworxSecondaryButton(
                                text = "Rescan",
                                onClick = { scanningState = ScanningState.SCANNING },
                                modifier = Modifier.weight(1f)
                            )

                            SourceworxPrimaryButton(
                                text = "Save Document",
                                onClick = { completeScan() },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                ScanningState.COMPLETE -> {
                    // Completion screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(80.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Document Saved",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your document has been successfully scanned and saved.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )

                        // This screen will automatically navigate back after a delay
                        // See the completeScan() function
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DocumentScannerScreenPreview() {
    SourceworxDMSTheme {
        DocumentScannerScreen(
            taskId = "DOC-2023-001",
            projectId = "CASE-001",
            onBackClick = {},
            onScanComplete = {}
        )
    }
}
