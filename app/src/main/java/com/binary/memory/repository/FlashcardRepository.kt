package com.binary.memory.repository

import com.binary.memory.database.dao.FlashDao
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * 闪卡仓库类，负责处理与 [Flashcard] 和 [FlashGroup] 相关的数据操作。
 *
 * @param flashDao 用于与数据库交互的 DAO 实例。
 */
class FlashcardRepository(private val flashDao: FlashDao) {

    /**
     * 将新的闪卡插入到数据库中。
     *
     * @param flashcard 要插入的闪卡。
     */
    suspend fun insertFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.insertFlashcard(flashcard)
        }
    }

    /**
     * 从数据库中删除闪卡。
     *
     * @param flashcard 要删除的闪卡。
     */
    suspend fun deleteFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.deleteFlashcard(flashcard)
        }
    }

    /**
     * 更新数据库中的现有闪卡。
     *
     * @param flashcard 要更新的闪卡。
     */
    suspend fun updateFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.updateFlashcard(flashcard)
        }
    }

    /**
     * 获取特定闪卡组的所有闪卡。
     *
     * @param flashGroupId 闪卡组的ID。
     * @return 包含指定闪卡组所有闪卡的流。
     */
    fun getAllFlashcards(flashGroupId: Int): Flow<List<Flashcard>> =
        flashDao.getAllFlashcards(flashGroupId)

    /**
     * 根据ID获取特定的闪卡。
     *
     * @param id 闪卡的ID。
     * @return 包含指定ID闪卡的流。
     */
    suspend fun getFlashcardById(id: Int): Flashcard? = withContext(Dispatchers.IO) {
        flashDao.getFlashcardById(id)
    }

    /**
     * 将新的闪卡组插入到数据库中。
     *
     * @param flashGroup 要插入的闪卡组。
     */
    suspend fun insertFlashGroup(flashGroup: FlashGroup) {
        withContext(Dispatchers.IO) {
            flashDao.insertFlashGroup(flashGroup)
        }
    }

    /**
     * 从数据库中删除闪卡组。
     *
     * @param flashGroup 要删除的闪卡组。
     */
    suspend fun deleteFlashGroup(flashGroup: FlashGroup) {
        withContext(Dispatchers.IO) {
            flashDao.deleteFlashGroup(flashGroup)
        }
    }

    /**
     * 根据ID从数据库中删除闪卡组。
     *
     * @param id 闪卡组的ID。
     */
    suspend fun deleteFlashGroupById(id: Int) {
        withContext(Dispatchers.IO) {
            flashDao.deleteFlashGroupById(id)
        }
    }

    /**
     * 更新数据库中的现有闪卡组。
     *
     * @param flashGroup 要更新的闪卡组。
     */
    suspend fun updateFlashGroup(flashGroup: FlashGroup) {
        withContext(Dispatchers.IO) {
            flashDao.updateFlashGroup(flashGroup)
        }
    }

    /**
     * 根据ID获取特定的闪卡组。
     *
     * @param id 闪卡组的ID。
     * @return 包含指定ID闪卡组的流。
     */
    fun getFlashGroupById(id: Int): FlashGroup? {
        return flashDao.getFlashGroupById(id)
    }

    /**
     * 从数据库中获取所有闪卡组。
     *
     * @return 包含所有闪卡组的流。
     */
    fun getAllFlashGroups(): Flow<List<FlashGroup>> {
        return flashDao.getAllFlashGroups()
    }
}
