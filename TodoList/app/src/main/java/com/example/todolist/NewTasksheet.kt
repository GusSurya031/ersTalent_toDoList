package com.example.todolist

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentNewTasksheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTasksheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTasksheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null) {
            binding.taskTittle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            if (taskItem!!.dueTime != null) {
                dueTime = taskItem!!.dueTime!!
                updateTimeButtonText()
            }
        } else {
            binding.taskTittle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        binding.saveButton.setOnClickListener {
            saveAction()
            if (dueTime != null) {
                val hour = dueTime!!.hour
                val minute = dueTime!!.minute
                AlarmHelper.setTaskAlarm(requireContext(), hour, minute)
            }
        }



        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    @SuppressLint("NewApi")
    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    @SuppressLint("NewApi")
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTasksheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        if (name.isNotEmpty()) {
            if (taskItem == null) {
                val newTask = TaskItem(name, dueTime, null)
                taskViewModel.addTaskItem(newTask)
            } else {
                taskViewModel.updateTaskItem(taskItem!!.id, name, dueTime)
            }

            // Panggil NotificationHelper setelah menyimpan task
            NotificationHelper.showTaskSavedNotification(requireContext())

            // Atur Alarm menggunakan AlarmHelper
            if (dueTime != null) {
                val hour = dueTime!!.hour  // Ambil jam dari dueTime
                val minute = dueTime!!.minute  // Ambil menit dari dueTime
                AlarmHelper.setTaskAlarm(requireContext(), hour, minute)
            }

            binding.name.setText("")
            dismiss()
        } else {
            Toast.makeText(requireContext(), "Task name cannot be empty!", Toast.LENGTH_SHORT).show()
        }
    }

}
