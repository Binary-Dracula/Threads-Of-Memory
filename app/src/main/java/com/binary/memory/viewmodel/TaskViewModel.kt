package com.binary.memory.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binary.memory.base.DraculaViewModel
import com.binary.memory.constants.EnumDifficulty
import com.binary.memory.constants.EnumPriority
import com.binary.memory.constants.EnumTaskSort
import com.binary.memory.model.Task
import com.binary.memory.repository.TaskRepository
import com.binary.memory.utils.AlarmUtils
import com.binary.memory.utils.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    application: Application,
    private val repository: TaskRepository
) : DraculaViewModel(application) {

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task?> = _task

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    val priorityList: ArrayList<EnumPriority> = ArrayList()
    private var priority: EnumPriority = EnumPriority.LOW

    val difficultyLevelList: ArrayList<EnumDifficulty> = ArrayList()
    private var difficultyLevel: EnumDifficulty = EnumDifficulty.EASY

    private var notifyDate: Long = 0
    private var notifyTime: Long = 0

    val done = MutableLiveData<Boolean>()
    val complete = MutableLiveData<Boolean>()

    private val alarmUtils = AlarmUtils()

    init {
        priorityList.add(EnumPriority.LOW)
        priorityList.add(EnumPriority.MEDIUM)
        priorityList.add(EnumPriority.HIGH)

        difficultyLevelList.addAll(EnumDifficulty.entries.toTypedArray())
    }

    fun getTaskListBySort(sort: EnumTaskSort = EnumTaskSort.CREATE_DATE) {
        viewModelScope.launch {
            repository.getAllTasks()
                .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                .collect {
                    if (it.isNotEmpty()) {
                        _taskList.value = when (sort) {
                            EnumTaskSort.CREATE_DATE -> it.sortedBy { task -> task.createTimestamp }
                            EnumTaskSort.REMIND_TIME -> it.sortedBy { task -> task.remindDate }
                            EnumTaskSort.PRIORITY -> it.sortedByDescending { task -> task.priorityIndex }
                        }
                    }
                }
        }
    }

    fun getPriority(name: String): String {
        return EnumPriority.valueOf(name).getOptionString(getApplication())
    }

    fun setPriority(priority: EnumPriority) {
        this.priority = priority
    }

    fun setDifficultyLevel(difficultyLevel: EnumDifficulty) {
        this.difficultyLevel = difficultyLevel
    }

    fun setNotifyDate(date: Long) {
        notifyDate = date
    }

    fun setNotifyTime(time: Long) {
        notifyTime = time
    }

    fun insertTask(title: String?, content: String?) {
        if (title.isNullOrEmpty()) {
            return
        }
        if (content.isNullOrEmpty()) {
            return
        }
        if (notifyDate == 0L) {
            return
        }
        if (notifyTime == 0L) {
            return
        }

        val task = Task(
            title = title,
            description = content,
            createTimestamp = DateUtils.getCurrentTimestamp(),
            remindDate = notifyDate,
            remindTime = notifyTime,
            priority = priority.name,
            priorityIndex = priority.getOptionIndex(),
            isDone = false,
        )

        alarmUtils.setAlarm(notifyDate + notifyTime, task)

        viewModelScope.launch {
            repository.insertTask(task)
            done.value = true
        }
    }

    fun completeTask() {
        viewModelScope.launch {
            if (_task.value != null) {
                // 计算提醒时间并更新
                val nextDate = DateUtils.getNextRemindDateByDifficultyLevel(difficultyLevel)
                val nextTime = DateUtils.get20ClockMillis()
                val triggerTime = nextDate + nextTime

                // for test
//                triggerTime = DateUtils.getTimestampAfter5Minutes()

                // 更新任务数据
                _task.value?.remindDate = nextDate
                _task.value?.remindTime = nextTime
                // 设定提醒
                alarmUtils.setAlarm(triggerTime, _task.value!!)
                // 更新表
                repository.updateTask(_task.value!!)
            }
            complete.value = true
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

    fun getRemindDateByTimestamp(timestamp: Long): String {
        return DateUtils.getDateByTimestamp(timestamp)
    }

    fun getTimeStringByMillis(millis: Long): String {
        return DateUtils.getTimeStringByMillis(millis)
    }

}

class TaskViewModelFactory(
    private val application: Application,
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}