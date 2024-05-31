package com.binary.memory.module.flashcard.adapter

import com.binary.memory.R
import com.binary.memory.base.DraculaAdapter
import com.binary.memory.databinding.ListItemFlashcardBinding
import com.binary.memory.model.Flashcard

class FlashcardListAdapter(
    flashcardList: MutableList<Flashcard>
) : DraculaAdapter<Flashcard, ListItemFlashcardBinding>(flashcardList) {
    override fun getLayoutResId(): Int {
        return R.layout.list_item_flashcard
    }

    override fun onBindData(binding: ListItemFlashcardBinding, item: Flashcard) {
        binding.flashcard = item
    }

}