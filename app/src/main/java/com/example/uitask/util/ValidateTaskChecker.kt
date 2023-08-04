package com.example.uitask.util

import com.example.uitask.data.local.Task

fun checkTask(task: Task): RegisterValidation {

    if (task.subjectString!!.isEmpty()) {
        return RegisterValidation.Failed("Subject field can't be empty!!")
    }

    if (task.assignees!!.isEmpty()) {
        return RegisterValidation.Failed("Assignees field can't be empty!!")
    }


    if (task.startDate!!.isEmpty()) {
        return RegisterValidation.Failed("Date range can't be empty!!")
    }

    if (task.expectedWorkingHorus!!.isEmpty()) {
        return RegisterValidation.Failed("Expected working hours can't be empty!!")
    }

    if (task.repeated!!.isEmpty()) {
        return RegisterValidation.Failed("repeated option  can't be empty!!")
    }

    if (task.priority!!.isEmpty()) {
        return RegisterValidation.Failed("Priority field can't be empty!!")
    }

    return RegisterValidation.Success
}