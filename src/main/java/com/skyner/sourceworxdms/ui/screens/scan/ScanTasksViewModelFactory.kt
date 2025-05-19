package com.skyner.sourceworxdms.ui.screens.scan

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScanTasksViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanTasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScanTasksViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
