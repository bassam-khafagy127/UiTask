package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uitask.R
import com.example.uitask.adapters.TaskAdapter
import com.example.uitask.data.local.Task
import com.example.uitask.databinding.FragmentTasksListBinding
import com.example.uitask.viewModel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksListFragment : Fragment(R.layout.fragment_tasks_list) {
    private lateinit var binding: FragmentTasksListBinding
    private val viewModel by viewModels<TasksViewModel>()

    private var tasks: List<Task> = mutableListOf()

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
        lifecycleScope.launch {
            setUpRecycler(view)
        }
    }

    private suspend fun setUpRecycler(view: View) {
        lifecycleScope.async(Dispatchers.IO) {
            tasks = viewModel.getAllTasks()
        }.await()
        binding.apply {

            val adapter =
                TaskAdapter(
                    tasks,
                    object : TaskAdapter.TaskInterActionListener {
                        override fun onClickTask(task: Task) {

                            val action =
                                TasksListFragmentDirections.actionTasksListFragmentToShowTaskFragment(
                                    task.id
                                )
                            Navigation.findNavController(view).navigate(action)

                        }
                    })

            adapter.setItems(tasks)
            binding.notificationRv.layoutManager =
                LinearLayoutManager(requireActivity().applicationContext)
            binding.notificationRv.adapter = adapter
        }

    }

}