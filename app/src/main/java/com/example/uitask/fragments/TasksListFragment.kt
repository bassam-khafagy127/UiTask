package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uitask.R
import com.example.uitask.adapters.TaskAdapter
import com.example.uitask.data.local.Task
import com.example.uitask.data.local.task.DateRange
import com.example.uitask.data.local.task.DefinitionOfDone
import com.example.uitask.data.local.task.Description
import com.example.uitask.data.local.task.Subject
import com.example.uitask.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment(R.layout.fragment_tasks_list) {
    private lateinit var binding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler(view)
    }

    private fun setUpRecycler(view: View) {
        val notifications = tasksComposition()

        binding.apply {
            val adapter =
                TaskAdapter(
                    notifications,
                    object : TaskAdapter.TaskInterActionListener {
                        override fun onClickTask(task: Task) {

                            val action =
                                TasksListFragmentDirections.actionTasksListFragmentToShowTaskFragment(
                                    task.id
                                )
                            Navigation.findNavController(view).navigate(action)

                        }
                    })

            binding.notificationRv.layoutManager =
                LinearLayoutManager(requireActivity().applicationContext)
            binding.notificationRv.adapter = adapter
        }
    }


    private fun tasksComposition(): List<Task> {
        val subject1 = Subject("Test1Subject", null)
        val description1 = Description("Test1Description", null)
        val definitionOfDone1 = DefinitionOfDone("Test1definitionOfDone", null)
        val dateRange1 = DateRange("25:11:2023", "25:12:2023")

        val subject2 = Subject("Test2Subject", null)
        val description2 = Description("Test2Description", null)
        val definitionOfDone2 = DefinitionOfDone("Test2definitionOfDone", null)
        val dateRange2 = DateRange("22:11:2023", "25:12:2023")

        val task1 = Task(
            0,
            subject1,
            description1,
            definitionOfDone1,
            null,
            null,
            null,
            dateRange1,
            1,
            false,
            "High",
            null,
            null,
            null
        )
        val task2 = Task(
            1,
            subject2,
            description2,
            definitionOfDone2,
            null,
            null,
            null,
            dateRange2,
            2,
            false,
            "High",
            null,
            null,
            null
        )

        return listOf(
            task1,
            task2
        )
    }
}