package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.uitask.R
import com.example.uitask.data.local.Task
import com.example.uitask.databinding.FragmentCreateClassicTaskBinding
import com.example.uitask.viewModel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateClassicTaskFragment : Fragment(R.layout.fragment_create_classic_task) {
    private lateinit var binding: FragmentCreateClassicTaskBinding
    private val viewModel by viewModels<TasksViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateClassicTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
    }

    private fun addCallbacks() {
        binding.apply {
            saveBtn.setOnClickListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insertTask(tasksComposition())
                }
            }
        }
    }

    private fun tasksComposition(): Task {
        return Task(
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


    }

}