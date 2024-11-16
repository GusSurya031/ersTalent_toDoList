package com.example.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.icu.text.DateFormat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("NewApi")
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @SuppressLint("NewApi")
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            if (taskItem.isCompleted()) {
                // Jika tugas sudah selesai, ubah menjadi tidak selesai (uncomplete)
                clickListener.uncompleteTaskItem(taskItem)
            } else {
                // Jika tugas belum selesai, tandai sebagai selesai (complete)
                clickListener.completeTaskItem(taskItem)
            }
        }


        binding.deleteButton.setOnClickListener{
            clickListener.deleteTaskItem(taskItem)
        }
        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.dueTime != null) {
            binding.dueTime.text = timeFormat.format(taskItem.dueTime)
        } else {
            binding.dueTime.text = ""
        }
    }
}