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

        val task1 = Task(
            0, "SujectString1",
            null,
            "DescriptionString",
            null,
            "Definition1",
            null,
            null,
            null,
            null,
            "25:10:2020",
            "25:10:2023",
            1,
            false,
            "High",
            null,
            null,
            null
        )
        val task2 = Task(
            1, "SujectString1",
            null,
            "DescriptionString",
            null,
            "Definition1",
            null,
            null,
            null,
            null,
            "25:10:2020",
            "25:10:2023",
            1,
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