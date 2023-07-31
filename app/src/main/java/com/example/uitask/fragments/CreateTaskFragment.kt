package com.example.uitask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.uitask.R
import com.example.uitask.adapters.ViewPager2Adapter
import com.example.uitask.databinding.FragmentCreateTaskBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        tabLayoutHandle()
        setUpCallBacks(view)
    }

    private fun setUpCallBacks(view: View) {
        binding.apply {
            backBtn.setOnClickListener {
                val action =
                    CreateTaskFragmentDirections.actionCreateTaskFragmentToNotificationListFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    private fun tabLayoutHandle() {
        val fragmentsCategories: List<Fragment> = arrayListOf(
            CreateClassicTaskFragment(),
            CreateLocationTaskFragment(),
            CreateMeetingsTaskFragment(),
            CreateKPITaskFragment(),
            CreateCompanyTaskFragment()
        )

        val viewPager2Adapter =
            ViewPager2Adapter(fragmentsCategories, childFragmentManager, lifecycle)

        binding.homeViewPager.adapter = viewPager2Adapter

        TabLayoutMediator(binding.createTaskTabLayout, binding.homeViewPager) { tap, position ->
            when (position) {
                0 -> tap.text = this.getString(R.string.classic)
                1 -> tap.text = this.getString(R.string.location)
                2 -> tap.text = this.getString(R.string.meetings)
                3 -> tap.text = this.getString(R.string.kpi)
                4 -> tap.text = this.getString(R.string.company)
            }
        }.attach()
    }
}