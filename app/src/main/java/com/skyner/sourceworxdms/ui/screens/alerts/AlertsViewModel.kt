package com.skyner.sourceworxdms.ui.screens.alerts

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

class AlertsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SeedDataRepository(application.applicationContext)

    private val _outstandingProjects = MutableStateFlow<List<Project>>(emptyList())
    val outstandingProjects: StateFlow<List<Project>> = _outstandingProjects.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAlerts()
    }

    private fun loadAlerts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Use the async method to load data in the background
                val projects = repository.getOutstandingProjectsAsync()

                // Update UI state on the main thread
                withContext(Dispatchers.Main) {
                    _outstandingProjects.value = projects
                    _error.value = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Failed to load alerts: ${e.message}"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshData() {
        loadAlerts()
    }
}
