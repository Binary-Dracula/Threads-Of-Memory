package com.binary.memory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binary.memory.base.DraculaViewModel
import com.binary.memory.constants.Constants
import com.binary.memory.model.Task
import com.binary.memory.repository.TaskRepository
import com.binary.memory.utils.AlarmUtils
import com.binary.memory.utils.DateUtils
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : DraculaViewModel() {

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task?> = _task

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    private var priority = Constants.Priority.LOW

    private var notifyDate = ""
    private var notifyTime = ""

    val done = MutableLiveData<Boolean>()

    private val alarmUtils = AlarmUtils()

    fun setPriority(priority: Constants.Priority) {
        this.priority = priority
    }

    fun setNotifyDate(date: String) {
        notifyDate = date
    }

    fun setNotifyTime(time: String) {
        notifyTime = time
    }

    fun insertTask(title: String?, content: String?) {
        if (title.isNullOrEmpty()) {
            return
        }
        if (content.isNullOrEmpty()) {
            return
        }
        if (notifyDate.isEmpty()) {
            return
        }
        if (notifyTime.isEmpty()) {
            return
        }

        val task = Task(
            title = title,
            description = content,
            createDate = DateUtils.getTodayDate(),
            date = notifyDate,
            time = notifyTime,
            priority = priority.getOrdinal(),
            isDone = false,
        )

        alarmUtils.setAlarm(DateUtils.getTriggerTimeMillis(notifyDate, notifyTime), task)

        viewModelScope.launch {
            repository.insertTask(task)
            done.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            if (_task.value != null) {
                repository.deleteTask(_task.value!!)
            }
            done.value = true
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

    fun getTaskById(id: Int) {
        viewModelScope.launch {
            repository.getTaskById(id)?.collect {
                _task.value = it
            }
        }
    }

    fun getAllTasks() {
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