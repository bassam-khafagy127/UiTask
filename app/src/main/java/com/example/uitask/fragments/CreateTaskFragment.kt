package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uitask.R
import com.example.uitask.adapters.ViewPager2Adapter
import com.example.uitask.databinding.FragmentCreateTaskBinding
import com.google.android.material.tabs.TabLayoutMediator

class CreateTaskFragment : Fragment(R.layout.fragment_create_task) {
    private lateinit var binding: FragmentCreateTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentsCategories: List<Fragment> = arrayListOf(
            CreateClassicTaskFragment(),
            CreateClassicTaskFragment(),
            CreateClassicTaskFragment(),
            CreateClassicTaskFragment(),
            CreateClassicTaskFragment()
        )

        val viewPager2Adapter =
            ViewPager2Adapter(fragmentsCategories, childFragmentManager, lifecycle)

        binding.homeViewPager.adapter = viewPager2Adapter

        TabLayoutMediator(binding.createTaskTabLayout, binding.homeViewPager) { tap, position ->
            when (position) {
                0 -> tap.text = "Classic"
                1 -> tap.text = "Location"
                2 -> tap.text = "Meetings"
                3 -> tap.text = "KPI"
                4 -> tap.text = "Company"
            }
        }.attach()
    }
}