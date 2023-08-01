package com.example.uitask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uitask.data.local.Task
import com.example.uitask.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _taskLiveDate = MutableLiveData<Task>()
    val taskLiveDate: LiveData<Task> = _taskLiveDate
    suspend fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    suspend fun getAllTasks() = repository.getAllTasks()
    suspend fun getTaskByID(id: Long) {
        _taskLiveDate.value = repository.getTaskById(id)
    }
}