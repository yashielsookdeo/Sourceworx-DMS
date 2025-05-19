package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skyner.sourceworxdms.data.SeedDataRepository
import com.skyner.sourceworxdms.data.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SeedDataRepository(application.applicationContext)

    private val _scannedProjects = MutableStateFlow<List<Project>>(emptyList())
    val scannedProjects: StateFlow<List<Project>> = _scannedProjects.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadScanTasks()
    }

    private fun loadScanTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Use the async method to load data in the background
                val projects = repository.getScannedProjectsAsync()

                // Update UI state on the main thread
                withContext(Dispatchers.Main) {
                    _scannedProjects.value = projects
                    _error.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Failed to load scan tasks: ${e.message}"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshData() {
        loadScanTasks()
    }

    // Calculate task status counts
    fun getPendingTasksCount(): Int {
        return _scannedProjects.value.flatMap { it.Tasks }.count { !it.IsCompleted }
    }

    fun getCompletedTasksCount(): Int {
        return _scannedProjects.value.flatMap { it.Tasks }.count { it.IsCompleted }
    }
}
