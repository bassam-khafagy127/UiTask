package com.example.uitask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uitask.data.local.Task
import com.example.uitask.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _tasksFlow: Channel<List<Task>> = Channel()
    val tasksFlow = _tasksFlow.receiveAsFlow()
    suspend fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    suspend fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasksFlow.send(repository.getAllTasks())
        }
    }
}