package com.binary.memory.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binary.memory.R
import com.binary.memory.base.DraculaViewModel
import com.binary.memory.entity.DifficultyLevel
import com.binary.memory.entity.Priority
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

    fun getTaskList() = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val priorityList: ArrayList<Priority> = ArrayList()
    private var priority: Priority? = null

    val difficultyLevelList: ArrayList<DifficultyLevel> = ArrayList()
    private var difficultyLevel: DifficultyLevel? = null

    private var notifyDate = ""
    private var notifyTime = ""

    val done = MutableLiveData<Boolean>()
    val complete = MutableLiveData<Boolean>()

    private val alarmUtils = AlarmUtils()

    init {
        application.resources.getStringArray(R.array.priority).forEachIndexed { index, s ->
            priorityList.add(Priority(index, s))
        }
        application.resources.getStringArray(R.array.difficulty_level).forEachIndexed { index, s ->
            difficultyLevelList.add(DifficultyLevel(index, s))
        }
    }

    fun setPriority(priority: Priority) {
        this.priority = priority
    }

    fun setDifficultyLevel(difficultyLevel: DifficultyLevel) {
        this.difficultyLevel = difficultyLevel
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
            priority = priority?.getOptionIndex() ?: 0,
            priorityString = priority?.getOptionString() ?: "",
            isDone = false,
        )

        alarmUtils.setAlarm(DateUtils.getTriggerTimeMillis(notifyDate, notifyTime), task)

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
                var triggerTime = nextDate + nextTime

                // for test
//                triggerTime = DateUtils.getTimestampAfter5Minutes()

                // 更新任务数据
                _task.value!!.date = DateUtils.dateTimestampToString(triggerTime)
                _task.value!!.time = DateUtils.timeTimestampToString(triggerTime)
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