package com.example.uitask.fragments

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.uitask.R
import com.example.uitask.data.local.Task
import com.example.uitask.databinding.FragmentCreateClassicTaskBinding
import com.example.uitask.util.RegisterValidation
import com.example.uitask.util.Resource
import com.example.uitask.util.checkTask
import com.example.uitask.util.dateConverter
import com.example.uitask.util.showSuccessToast
import com.example.uitask.viewModel.TasksViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CreateClassicTaskFragment : Fragment(R.layout.fragment_create_classic_task) {

    private lateinit var binding: FragmentCreateClassicTaskBinding

    private val viewModel by viewModels<TasksViewModel>()

    private var startDate: String = ""
    private var endDate: String = ""

    private var selectedAttachment = ""

    private var priority: String = ""

    private var assignees: String = ""

    private var ccAssignees: String = ""

    private val selectAttachmentActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data

                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount ?: 0
                    (0 until count).forEach {
                        val imageUri = data.clipData?.getItemAt(it)?.uri
                        imageUri?.let {
                            selectedAttachment = it.toString()
                        }
                    }
                } else {
                    val attachmentUri = data?.data
                    attachmentUri?.let {
                        selectedAttachment = it.toString()
                    }
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateClassicTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks(view)
        observeDateValue()
        observeInsertionState()
        observePriorityValue()
        observeAssigneesValue()
        observeCcAssigneesValue()
    }


    private fun addCallbacks(view: View) {
        binding.apply {

            saveBtn.setOnClickListener {
                taskSave(view)
            }

            clAttachment.setOnClickListener {
                selectAttachment()
            }
            clDate.setOnClickListener {
                showDateRangePickerDialog()
            }

            clPriority.setOnClickListener {
                setPriority()
            }

            subjectMicrophoneIv.setOnClickListener {
                speechToText(subjectResult)
            }
            descriptionMicrophoneIv.setOnClickListener {
                speechToText(descriptionResult)
            }
            definitionOfDoneMicrophoneIv.setOnClickListener {
                speechToText(definitionResult)
            }

            clRecordVoice.setOnClickListener {
                Toast.makeText(requireContext(), "Voice Recording Coming Soon", Toast.LENGTH_SHORT)
                    .show()
            }
            assigneesCl.setOnClickListener {
                setAssignees()
            }
            ccAssigneesCl.setOnClickListener {
                setCcAssignees()
            }
        }
    }


    private fun taskSave(view: View) {

        when (val validation = checkTask(tasksComposition())) {

            is RegisterValidation.Failed -> {

                Snackbar.make(requireContext(), view, validation.message, Snackbar.LENGTH_LONG)
                    .show()
            }

            is RegisterValidation.Success -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.insertTask(tasksComposition())
                }
            }
        }
    }


    private fun selectAttachment() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.type = "*/*"
        selectAttachmentActivityResult.launch(intent)
    }


    private fun showDateRangePickerDialog(): Pair<String, String> {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
        datePicker.show(childFragmentManager, "Date Range Picker")

        // Setting up the event for when ok is clicked
        datePicker.addOnPositiveButtonClickListener { selection ->

            startDate = dateConverter(Date(selection.first ?: 0).toString())
            endDate = dateConverter(Date(selection.second ?: 0).toString())

            Log.d("TIME_DEBUG", "StartDate: $startDate / EndDate: $endDate")

            if (startDate.isNotEmpty()) {
                viewModel.setStartEndDate(Resource.Success(Pair(startDate, endDate)))
            } else {
                viewModel.setStartEndDate(Resource.Unspecified())
            }
        }

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            Log.d("TIME_DEBUG", datePicker.headerText)
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            Log.d("TIME_DEBUG", "Date Picker Cancelled")
        }
        return Pair(startDate, endDate)
    }


    private fun setPriority() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.getPriority))
        alertDialog.setPositiveButton(getString(R.string.ok), null)
        val items = arrayOf("High", "Mid", "Low")
        val checkItem = 1
        alertDialog.setSingleChoiceItems(items, checkItem) { _: DialogInterface?, which: Int ->
            run {
                when (which) {
                    0 -> {
                        Log.d("Priority", items[0])
                        viewModel.setPriority(items[0])
                    }

                    1 -> {
                        Log.d("Priority", items[1])
                        viewModel.setPriority(items[1])
                    }

                    2 -> {
                        Log.d("Priority", items[2])
                        viewModel.setPriority(items[2])
                    }
                }
            }
        }
        alertDialog.show()
    }


    private fun speechToText(intentLauncher: ActivityResultLauncher<Intent>) {
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speechToText))
            intentLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val descriptionResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                binding.descriptionValueEd.append(" " + results[0])
            }
        }


    private val subjectResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>

                binding.subjectValueEd.append(" " + results[0])
            }
        }

    private val definitionResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>

                binding.definitionValueEd.append(" " + results[0])
            }
        }


    private fun setAssignees() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.choseAssignees))
        alertDialog.setPositiveButton(getString(R.string.ok), null)
        val items = arrayOf(
            "Ola Hassan ELrifaey",
            "Khalid abo elMagd",
            "Moiz Eldin Elbaksisy",
            "Ola Khalid ELrifaey",
        )
        val checkItem = 1
        alertDialog.setSingleChoiceItems(items, checkItem) { _: DialogInterface?, which: Int ->
            run {
                when (which) {
                    0 -> {
                        Log.d("Assignees", items[0])
                        viewModel.setAssignees(items[0])
                    }

                    1 -> {
                        Log.d("Assignees", items[1])
                        viewModel.setAssignees(items[1])
                    }

                    2 -> {
                        Log.d("Assignees", items[2])
                        viewModel.setAssignees(items[2])
                    }

                    3 -> {
                        Log.d("Assignees", items[3])
                        viewModel.setAssignees(items[3])
                    }
                }
            }
        }
        alertDialog.show()
    }

    private fun setCcAssignees() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.choseAssignees))
        alertDialog.setPositiveButton(getString(R.string.ok), null)
        val items = arrayOf(
            "Ola Hassan ELrifaey",
            "Khalid abo elMagd",
            "Moiz Eldin Elbaksisy",
            "Ola Khalid ELrifaey",
        )
        val checkItem = 1
        alertDialog.setSingleChoiceItems(items, checkItem) { _: DialogInterface?, which: Int ->
            run {
                when (which) {
                    0 -> {
                        Log.d("CcAssignees", items[0])
                        viewModel.setCcAssignees(items[0])
                    }

                    1 -> {
                        Log.d("CcAssignees", items[1])
                        viewModel.setCcAssignees(items[1])
                    }

                    2 -> {
                        Log.d("CcAssignees", items[2])
                        viewModel.setCcAssignees(items[2])
                    }

                    3 -> {
                        Log.d("CcAssignees", items[3])
                        viewModel.setCcAssignees(items[3])
                    }
                }
            }
        }
        alertDialog.show()
    }

    private fun tasksComposition(): Task {

        val subjectString = binding.subjectValueEd.text?.trim().toString()

        val descriptionString = binding.descriptionValueEd.text?.trim().toString()

        val definitionOfDoneString = binding.definitionValueEd.text?.trim().toString()

        val voiceNoteUri = null

        val assigneesString = assignees

        val ccAssigneesString = ccAssignees

        val expectedHour = binding.expectedHoursEd.text?.trim().toString()

        val repeatedBoolean = binding.repeatedHoursEd.text?.trim().toString()

        val priorityString = priority

        val toDoString = binding.todoValueEd.text?.trim().toString()

        val attachmentUris = selectedAttachment

        val selectProjects = null

        return Task(
            0,
            subjectString,
            descriptionString,
            definitionOfDoneString,
            voiceNoteUri,
            assigneesString,
            ccAssigneesString,
            startDate,
            endDate,
            expectedHour,
            repeatedBoolean,
            priorityString,
            toDoString,
            attachmentUris,
            selectProjects
        )
    }

    private fun observeDateValue() {
        lifecycleScope.launch {
            viewModel.dateLiveDate.collect {
                when (it) {
                    is Resource.Unspecified -> {
                        binding.apply {
                            startDateTv.visibility = View.GONE
                            startDateIv.visibility = View.GONE

                            verticalDotsView.visibility = View.GONE

                            endDateTv.visibility = View.GONE
                            endOval.visibility = View.GONE
                        }
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        binding.apply {
                            startDateTv.visibility = View.VISIBLE
                            startDateTv.text = it.data?.first
                            startDateIv.visibility = View.VISIBLE

                            verticalDotsView.visibility = View.VISIBLE

                            endDateTv.text = it.data?.second
                            endDateTv.visibility = View.VISIBLE
                            endOval.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }

    }

    private fun observeInsertionState() {
        lifecycleScope.launch {
            viewModel.insertionState.collect {
                when (it) {
                    is Resource.Loading -> {}

                    is Resource.Error -> {}

                    is Resource.Success -> {
                        showSuccessToast(requireContext())
                    }

                    is Resource.Unspecified -> {}
                }
            }
        }
    }

    private fun observePriorityValue() {
        lifecycleScope.launch {
            binding.apply {
                viewModel.priorityLiveDate.collect {
                    priority = it
                    priorityValueIv.visibility = View.VISIBLE
                    priorityValueTv.visibility = View.VISIBLE
                    priorityValueTv.text = it
                }
            }
        }
    }

    private fun observeAssigneesValue() {
        lifecycleScope.launch {
            binding.apply {
                viewModel.assigneesLiveDate.collect {
                    assignees = it
                    assigneesTvUser.text = it
                    assigneesValueId.visibility = View.GONE

                    assigneesIvUser.visibility = View.VISIBLE
                    assigneesClUser.visibility = View.VISIBLE
                    assigneesTvUser.visibility = View.VISIBLE

                }
            }
        }
    }

    private fun observeCcAssigneesValue() {
        lifecycleScope.launch {
            binding.apply {
                viewModel.ccAssigneesLiveDate.collect {
                    ccAssignees = it
                    ccAssigneesTvUser.text = it
                    assigneesValueId.visibility = View.GONE

                    ccAssigneesIvUser.visibility = View.VISIBLE
                    ccAssigneesClUser.visibility = View.VISIBLE
                    ccAssigneesTvUser.visibility = View.VISIBLE

                }
            }
        }
    }
}


