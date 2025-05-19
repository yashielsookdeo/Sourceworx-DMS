package com.skyner.sourceworxdms.ui.screens.dashboard

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

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SeedDataRepository(application.applicationContext)

    private val _allocatedProjects = MutableStateFlow<List<Project>>(emptyList())
    val allocatedProjects: StateFlow<List<Project>> = _allocatedProjects.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Use the async method to load data in the background
                val projects = repository.getAllocatedProjectsAsync()

                // Update UI state on the main thread
                withContext(Dispatchers.Main) {
                    _allocatedProjects.value = projects
                    _error.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Failed to load projects: ${e.message}"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshData() {
        loadProjects()
    }
}
