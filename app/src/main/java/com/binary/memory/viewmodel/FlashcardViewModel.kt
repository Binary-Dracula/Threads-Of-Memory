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
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import com.binary.memory.repository.FlashcardRepository
import com.binary.memory.utils.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashcardViewModel(
    application: Application,
    private val repository: FlashcardRepository
) : DraculaViewModel(application) {

    fun getFlashGroups() = repository.getAllFlashGroups()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val insertFlashGroupSuccess = MutableLiveData<Boolean>()
    val insertFlashcardSuccess = MutableLiveData<Boolean>()

    // Difficulty Level
    val difficultyLevel = mutableListOf<DifficultyLevel>()

    private val _currentFlashcard = MutableLiveData<Flashcard?>()
    val currentFlashcard: LiveData<Flashcard?> = _currentFlashcard
    private var currentFlashcardIndex = -1
    private var flashcards: List<Flashcard> = emptyList()

    init {
        application.resources.getStringArray(R.array.difficulty_level)
            .forEachIndexed { index, string ->
                difficultyLevel.add(DifficultyLevel(index, string))
            }
    }

    fun insertFlashGroup(flashGroupName: String) {
        viewModelScope.launch {
            repository.insertFlashGroup(
                FlashGroup(
                    flashGroupTitle = flashGroupName
                )
            )
            insertFlashGroupSuccess.value = true
        }
    }

    fun insertFlashcard(question: String, answer: String, flashGroupId: Int) {
        viewModelScope.launch {
            repository.insertFlashcard(
                Flashcard(
                    front = question,
                    back = answer,
                    createdTime = DateUtils.getCurrentTimestamp(),
                    flashGroupId = flashGroupId,
                    difficultyLevel = difficultyLevel.find { it.level == 0 }?.level ?: 0
                )
            )
            insertFlashcardSuccess.value = true
        }
    }

    fun getFlashcardList(flashGroupId: Int) = repository.getAllFlashcards(flashGroupId)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadCurrentFlashcard(flashcardGroupId: Int, flashcardId: Int) {
        viewModelScope.launch {
            repository.getAllFlashcards(flashcardGroupId).collect { flashcards ->
                this@FlashcardViewModel.flashcards = flashcards
                currentFlashcardIndex = flashcards.indexOfFirst { it.id == flashcardId }
                if (currentFlashcardIndex != -1) {
                    _currentFlashcard.value = flashcards[currentFlashcardIndex]
                }
            }
        }
    }

    fun loadPreviousFlashcard() {
        if (currentFlashcardIndex > 0) {
            currentFlashcardIndex -= 1
            _currentFlashcard.value = flashcards[currentFlashcardIndex]
        } else {
            _currentFlashcard.value = null
        }
    }

    fun loadNextFlashcard() {
        if (currentFlashcardIndex < flashcards.size - 1) {
            currentFlashcardIndex += 1
            _currentFlashcard.value = flashcards[currentFlashcardIndex]
        } else {
            _currentFlashcard.value = null
        }
    }

    private fun updateFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.updateFlashcard(flashcard)
        }
    }

    fun setCurrentDifficultyLevel(difficultyLevel: DifficultyLevel) {
        _currentFlashcard.value?.let {
            it.difficultyLevel = difficultyLevel.level
            updateFlashcard(it)
        }
    }

}

class FlashcardViewModelFactory(
    val application: Application,
    private val repository: FlashcardRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}