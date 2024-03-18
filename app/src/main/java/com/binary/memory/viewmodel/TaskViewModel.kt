package com.binary.memory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binary.memory.base.DraculaViewModel
import com.binary.memory.model.Task
import com.binary.memory.repository.TaskRepository
import com.binary.memory.utils.DateUtils
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : DraculaViewModel() {


    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    fun insertTask(title: String?, content: String?) {
        if (title.isNullOrEmpty() || content.isNullOrEmpty()) return
        viewModelScope.launch {
            repository.insertTask(
                Task(
                    title = title,
                    description = content,
                    date = DateUtils.getTodayDate()
                )
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _taskList.value = it
            }
        }
    }

    fun getTasksByDate(date: String) {
        viewModelScope.launch {
            repository.getTasksByDate(date).collect {
                _taskList.value = it
            }
        }
    }

}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}