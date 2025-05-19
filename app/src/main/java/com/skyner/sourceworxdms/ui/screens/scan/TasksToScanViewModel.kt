package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.skyner.sourceworxdms.data.SeedDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TasksToScanViewModel(application: Application) : AndroidViewModel(application) {

    private val _tasks = MutableStateFlow<List<TaskToScan>>(emptyList())
    val tasks: StateFlow<List<TaskToScan>> = _tasks.asStateFlow()

    private val _filteredTasks = MutableStateFlow<List<TaskToScan>>(emptyList())
    val filteredTasks: StateFlow<List<TaskToScan>> = _filteredTasks.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                // In a real app, this would fetch tasks from a repository
                // For now, we'll use the sample data
                _tasks.value = sampleTasksToScan
                
                // Apply initial filter (All Tasks)
                filterTasks(0)
                
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load tasks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshData() {
        loadTasks()
    }

    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
        filterTasks(tabIndex)
    }

    private fun filterTasks(tabIndex: Int) {
        val allTasks = _tasks.value
        
        _filteredTasks.value = when (tabIndex) {
            0 -> allTasks // All Tasks
            1 -> allTasks.filter { it.priority == TaskPriority.HIGH } // Urgent
            2 -> allTasks.filter { isToday(it.dueDate) } // Today
            3 -> allTasks.filter { isThisWeek(it.dueDate) } // This Week
            else -> allTasks
        }
    }

    private fun isToday(dateString: String): Boolean {
        // Simple check if the date string contains "Today"
        return dateString.contains("Today", ignoreCase = true)
    }

    private fun isThisWeek(dateString: String): Boolean {
        // Simple check if the date is within this week
        // In a real app, this would parse the date and check if it's within the current week
        return dateString.contains("Today", ignoreCase = true) || 
               dateString.contains("Tomorrow", ignoreCase = true) ||
               dateString.contains("Oct", ignoreCase = true)
    }

    fun markTaskAsScanned(taskId: String) {
        // In a real app, this would update the task status in the repository
        // For now, we'll just update the local state
        val updatedTasks = _tasks.value.map { task ->
            if (task.id == taskId) {
                // Create a copy of the task with updated status
                task.copy(isScanned = true)
            } else {
                task
            }
        }
        
        _tasks.value = updatedTasks
        filterTasks(_selectedTab.value)
    }
}

// Extension function to add isScanned property to TaskToScan
fun TaskToScan.copy(
    id: String = this.id,
    title: String = this.title,
    documentType: String = this.documentType,
    dueDate: String = this.dueDate,
    assignedBy: String = this.assignedBy,
    priority: TaskPriority = this.priority,
    isScanned: Boolean = this.isScanned
): TaskToScan {
    return TaskToScan(
        id = id,
        title = title,
        documentType = documentType,
        dueDate = dueDate,
        assignedBy = assignedBy,
        priority = priority,
        isScanned = isScanned
    )
}

class TasksToScanViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksToScanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksToScanViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
