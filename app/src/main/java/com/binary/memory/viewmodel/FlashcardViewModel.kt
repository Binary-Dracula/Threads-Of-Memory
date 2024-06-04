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
import com.binary.memory.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

    fun getFlashcardList(flashGroupId: Int): StateFlow<List<Flashcard>> {
        return repository.getAllFlashcards(flashGroupId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }


    private val _flashcard = MutableStateFlow<Flashcard?>(null)
    val flashcard: StateFlow<Flashcard?>
        get() = _flashcard

    val insertFlashcardSuccess = MutableLiveData<Boolean>()

    fun insertFlashcard(question: String, answer: String, flashGroupId: Int) {
        viewModelScope.launch {
            repository.insertFlashcard(
                Flashcard(
                    front = question,
                    back = answer,
                    createdTime = DateUtils.getCurrentTimestamp(),
                    flashGroupId = flashGroupId
                )
            )
            insertFlashcardSuccess.value = true
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