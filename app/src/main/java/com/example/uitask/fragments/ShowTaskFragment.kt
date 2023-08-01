package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.uitask.R
import com.example.uitask.databinding.FragmentShowTaskBinding
import com.example.uitask.viewModel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowTaskFragment : Fragment(R.layout.fragment_show_task) {
    private lateinit var binding: FragmentShowTaskBinding

    private val taskArgs: ShowTaskFragmentArgs by navArgs()
    private val viewModel by viewModels<TasksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowTaskBinding.inflate(layoutInflater)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getTaskByID(taskArgs.taskId.toLong())
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentTaskTitle.text = taskArgs.taskId.toString()
    }
}