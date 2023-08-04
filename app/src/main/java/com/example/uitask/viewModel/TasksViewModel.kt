package com.example.uitask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uitask.data.local.Task
import com.example.uitask.repositories.TasksRepository
import com.example.uitask.util.Resource
import com.example.uitask.util.getSystemDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _taskLiveDate = MutableLiveData<Task>()
    val taskLiveDate: LiveData<Task> = _taskLiveDate

    private val _dateLiveDate =
        MutableSharedFlow<Resource<Pair<String, String>>>()
    val dateLiveDate = _dateLiveDate.asSharedFlow()

    private val _insertionState =
        MutableSharedFlow<Resource<String>>()
    val insertionState = _insertionState.asSharedFlow()

    private val _priorityLiveDate =
        MutableSharedFlow<String>()
    val priorityLiveDate = _priorityLiveDate.asSharedFlow()

    private val _assigneesLiveDate =
        MutableSharedFlow<String>()
    val assigneesLiveDate = _assigneesLiveDate.asSharedFlow()

    private val _ccAssigneesLiveDate =
        MutableSharedFlow<String>()
    val ccAssigneesLiveDate = _ccAssigneesLiveDate.asSharedFlow()

    suspend fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            _insertionState.emit(Resource.Loading())
            if (repository.insertTask(task) > 0) {
                _insertionState.emit(Resource.Success("Data inserted successfully"))
            } else {
                _insertionState.emit(Resource.Error("Data not inserted"))
            }
        }
    }

    suspend fun getAllTasks() = repository.getAllTasks()

    fun getTaskByID(id: Long) {
        _taskLiveDate.postValue(repository.getTaskById(id))
    }


    fun getSystemTime(): String {
        return getSystemDate()
    }

    fun setStartEndDate(dateRange: Resource<Pair<String, String>>) {
        viewModelScope.launch {
            _dateLiveDate.emit(dateRange)
        }
    }

    fun setPriority(priority: String) {
        viewModelScope.launch {
            _priorityLiveDate.emit(priority)
        }
    }

    fun setAssignees(assignees: String) {
        viewModelScope.launch {
            _assigneesLiveDate.emit(assignees)
        }
    }

    fun setCcAssignees(assignees: String) {
        viewModelScope.launch {
            _ccAssigneesLiveDate.emit(assignees)
        }
    }

}