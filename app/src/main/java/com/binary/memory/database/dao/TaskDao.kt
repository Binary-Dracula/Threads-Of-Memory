package com.binary.memory.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.binary.memory.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTaskById(id: Int)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTaskById(id: Int): Flow<Task>?

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY createTimestamp ASC")
    fun getAllTasksByCreateTimestamp(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY remindDate ASC, remindTime ASC")
    fun getAllTasksByRemind(): Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY priorityIndex DESC")
    fun getAllTasksByPriority():Flow<List<Task>>
}