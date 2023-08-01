package com.example.uitask.repositories

import com.example.uitask.data.database.TaskedInAppDataBase
import com.example.uitask.data.local.Task
import javax.inject.Inject

class TasksRepository @Inject constructor(private val taskedInAppDataBase: TaskedInAppDataBase) {

    suspend fun insertTask(task: Task) {
        taskedInAppDataBase.tasksDao().insertTask(task)
    }

    suspend fun getAllTasks() = taskedInAppDataBase.tasksDao().getAllTasks()

    fun getTaskById(id: Long) = taskedInAppDataBase.tasksDao().getTaskById(id)

}