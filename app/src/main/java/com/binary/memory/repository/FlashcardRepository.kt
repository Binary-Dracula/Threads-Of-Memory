package com.binary.memory.repository

import com.binary.memory.database.dao.FlashDao
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FlashcardRepository(private val flashDao: FlashDao) {

    suspend fun insertFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.insertFlashcard(flashcard)
        }
    }

    suspend fun deleteFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.deleteFlashcard(flashcard)
        }
    }

    suspend fun updateFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashDao.updateFlashcard(flashcard)
        }
    }

    suspend fun getAllFlashcards(): Flow<List<Flashcard>> {
        return withContext(Dispatchers.IO) {
            flashDao.getAllFlashcards()
        }
    }

    suspend fun getFlashcardById(id: Int): Flow<Flashcard> {
        return withContext(Dispatchers.IO) {
            flashDao.getFlashcardById(id)
        }
    }

    suspend fun insertFlashGroup(flashGroup: FlashGroup) {
        withContext(Dispatchers.IO) {
            flashDao.insertFlashGroup(flashGroup)
        }
    }

    fun getAllFlashGroups(): Flow<List<FlashGroup>> {
        return flashDao.getAllFlashGroups()
    }

}
