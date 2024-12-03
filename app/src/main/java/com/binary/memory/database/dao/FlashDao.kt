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
    /**
     * 插入一个Flashcard
     */
    @Insert
    suspend fun insertFlashcard(flashcard: Flashcard)

    /**
     * 删除一个Flashcard
     */
    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    /**
     * 删除所有Flashcard
     */
    @Query("DELETE FROM flashcards")
    suspend fun deleteAllFlashcards()

    /**
     * 更新一个Flashcard
     */
    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    /**
     * 根据flashGroupId获取所有的Flashcard
     */
    @Query("SELECT * FROM flashcards WHERE flashGroupId = :flashGroupId")
    fun getAllFlashcards(flashGroupId: Int): Flow<List<Flashcard>>

    /**
     * 根据id获取一个Flashcard
     */
    @Query("SELECT * FROM flashcards WHERE id = :id")
    fun getFlashcardById(id: Int): Flashcard?

    /**
     * 插入一个FlashGroup
     */
    @Insert
    fun insertFlashGroup(flashGroup: FlashGroup)

    /**
     * 删除一个FlashGroup
     */
    @Delete
    fun deleteFlashGroup(flashGroup: FlashGroup)

    /**
     * 根据id删除一个FlashGroup
     */
    @Query("delete from flash_group where id = :id")
    fun deleteFlashGroupById(id: Int)

    /**
     * 更新一个FlashGroup
     */
    @Update
    fun updateFlashGroup(flashGroup: FlashGroup)

    /**
     * 根据id获取一个FlashGroup
     */
    @Query("select * from flash_group where id = :id")
    fun getFlashGroupById(id: Int): FlashGroup?

    /**
     * 获取所有的FlashGroup
     */
    @Query("select * from flash_group")
    fun getAllFlashGroups(): Flow<List<FlashGroup>>
}
