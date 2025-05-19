package com.skyner.sourceworxdms.ui.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skyner.sourceworxdms.data.SeedDataRepository
import com.skyner.sourceworxdms.data.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SeedDataRepository(application.applicationContext)

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadProject(projectId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Use the repository to get the project by ID
                val projectData = repository.getProjectByIdAsync(projectId)

                withContext(Dispatchers.Main) {
                    _project.value = projectData
                    if (projectData == null) {
                        _error.value = "Project not found"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message ?: "Unknown error"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }
}

/**
 * Factory for creating a [TaskDetailViewModel] with a constructor that takes an [Application].
 */
class TaskDetailViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            return TaskDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
