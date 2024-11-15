package com.example.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel: ViewModel () {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value ?: mutableListOf()
        list.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, name: String, dueTime: LocalTime?) {
        val list = taskItems.value ?: return
        val task = list.find { it.id == id } ?: return
        task.name = name
        task.dueTime = dueTime
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value ?: return
        val task = list.find { it.id == taskItem.id } ?: return
        task.completedDate = LocalDate.now()
        taskItems.postValue(list)
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        val list = taskItems.value ?: return
        list.remove(taskItem)
        taskItems.postValue(list)
    }
}