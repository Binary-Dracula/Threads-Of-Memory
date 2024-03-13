package com.binary.memory.repository

import com.binary.memory.database.dao.FlashcardDao
import com.binary.memory.model.Flashcard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FlashcardRepository(private val flashcardDao: FlashcardDao) {

    suspend fun insertFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashcardDao.insertFlashcard(flashcard)
        }
    }

    suspend fun getAllFlashcards(): Flow<List<Flashcard>> {
        return withContext(Dispatchers.IO) {
            flashcardDao.getAllFlashcards()
        }
    }

    suspend fun getFlashcardById(id: Int): Flow<Flashcard> {
        return withContext(Dispatchers.IO) {
            flashcardDao.getFlashcardById(id)
        }
    }

    suspend fun updateFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashcardDao.updateFlashcard(flashcard)
        }
    }

    suspend fun deleteFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            flashcardDao.deleteFlashcard(flashcard)
        }
    }
}
