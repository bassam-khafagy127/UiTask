package com.example.uitask.adapters

import com.example.uitask.R
import com.example.uitask.base.BaseAdapter
import com.example.uitask.data.local.Task

class TaskAdapter<T>(
    mList: List<T>, listener: TaskInterActionListener,
    override val layoutId: Int = R.layout.rv_item_notification
) : BaseAdapter<T>(mList, listener) {


    interface TaskInterActionListener : BaseInterActionListener {
        fun onClickTask(task: Task)
    }
}