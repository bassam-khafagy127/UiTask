package com.example.uitask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uitask.data.local.Task

@Dao
interface TasksDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT*FROM `Tasks Table` ORDER BY id")
    fun getAllTasks(): List<Task>

}