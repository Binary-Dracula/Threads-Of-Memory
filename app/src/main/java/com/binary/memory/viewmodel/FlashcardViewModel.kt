package com.binary.memory.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binary.memory.base.DraculaViewModel
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import com.binary.memory.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FlashcardViewModel(
    application: Application,
    private val repository: FlashcardRepository
) : DraculaViewModel(application) {

    val flashGroups: LiveData<List<FlashGroup>> = repository.getAllFlashGroups()
        .onStart { }
        .catch { }
        .asLiveData(viewModelScope.coroutineContext)

    val insertFlashGroupSuccess = MutableLiveData<Boolean>()

    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>>
        get() = _flashcards

    private val _flashcard = MutableStateFlow<Flashcard?>(null)
    val flashcard: StateFlow<Flashcard?>
        get() = _flashcard

    fun insertFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.insertFlashcard(flashcard)
        }
    }

    fun getAllFlashcards() {
        viewModelScope.launch {
            repository.getAllFlashcards().collect {
                _flashcards.value = it
            }
        }
    }

    fun getFlashcardById(id: Int) {
        viewModelScope.launch {
            repository.getFlashcardById(id).collect {
                _flashcard.value = it
            }
        }
    }

    fun updateFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.updateFlashcard(flashcard)
        }
    }

    fun deleteFlashcard(flashcard: Flashcard) {
        viewModelScope.launch {
            repository.deleteFlashcard(flashcard)
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