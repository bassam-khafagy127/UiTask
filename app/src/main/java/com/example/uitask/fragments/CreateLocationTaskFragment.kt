package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uitask.R
import com.example.uitask.databinding.FragmentCreateLocationTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateLocationTaskFragment : Fragment(R.layout.fragment_create_location_task) {
    private lateinit var binding: FragmentCreateLocationTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateLocationTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}