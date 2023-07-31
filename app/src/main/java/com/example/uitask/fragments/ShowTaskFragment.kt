package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.uitask.R
import com.example.uitask.databinding.FragmentShowTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTaskFragment : Fragment(R.layout.fragment_show_task) {
    private lateinit var binding: FragmentShowTaskBinding
    private val taskArgs: ShowTaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentTaskTitle.text = taskArgs.taskId.toString()
    }
}