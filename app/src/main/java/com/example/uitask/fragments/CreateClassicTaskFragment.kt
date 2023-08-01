package com.example.uitask.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.uitask.R
import com.example.uitask.data.local.Task
import com.example.uitask.databinding.FragmentCreateClassicTaskBinding
import com.example.uitask.viewModel.TasksViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class CreateClassicTaskFragment : Fragment(R.layout.fragment_create_classic_task) {
    private lateinit var binding: FragmentCreateClassicTaskBinding
    private val viewModel by viewModels<TasksViewModel>()
    private var startDate: String = ""
    private var endDate: String = ""
    private var selectedAttachment = mutableListOf<Uri>()

    private val selectAttachmentActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data

                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount ?: 0
                    (0 until count).forEach {
                        val imageUri = data.clipData?.getItemAt(it)?.uri
                        imageUri?.let {
                            selectedAttachment.add(it)
                        }
                    }
                } else {
                    val attachmentUri = data?.data
                    attachmentUri?.let {
                        selectedAttachment.add(it)
                    }
                }
            }
        }

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

            clAttachment.setOnClickListener {
                selectAttachment()
            }
            clDate.setOnClickListener {
                showDateRangePickerDialog()
            }

            clPriority.setOnClickListener {

            }
        }
    }

    private fun selectAttachment() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "*/*"
        selectAttachmentActivityResult.launch(intent)
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

    private fun showDateRangePickerDialog(): Pair<String, String> {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
        datePicker.show(childFragmentManager, "DatePicker")

        // Setting up the event for when ok is clicked
        datePicker.addOnPositiveButtonClickListener { selection ->
            startDate = Date(selection.first ?: 0).toString()
            endDate = Date(selection.second ?: 0).toString()
            Log.d("TIME_DEBUG", "StartDate: $startDate / EndDate: $endDate")
        }

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            Log.d("TIME_DEBUG", datePicker.headerText)
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG)
                .show()
            Log.d("TIME_DEBUG", "Date Picker Cancelled")

        }
        return Pair(startDate, endDate)
    }

}