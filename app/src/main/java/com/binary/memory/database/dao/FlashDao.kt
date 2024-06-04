package com.binary.memory.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashDao {
    @Insert
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAllFlashcards()

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    @Query("SELECT * FROM flashcards WHERE flashGroupId = :flashGroupId")
    fun getAllFlashcards(flashGroupId: Int): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE id = :id")
    fun getFlashcardById(id: Int): Flow<Flashcard>


    // 增
    @Insert
    fun insertFlashGroup(flashGroup: FlashGroup)

    // 删
    @Delete
    fun deleteFlashGroup(flashGroup: FlashGroup)

    @Query("delete from flash_group where id = :id")
    fun deleteFlashGroupById(id: Int)

    // 改
    @Update
    fun updateFlashGroup(flashGroup: FlashGroup)

    // 查
    @Query("select * from flash_group where id = :id")
    fun getFlashGroupById(id: Int): Flow<FlashGroup>

    @Query("select * from flash_group")
    fun getAllFlashGroups(): Flow<List<FlashGroup>>
}
