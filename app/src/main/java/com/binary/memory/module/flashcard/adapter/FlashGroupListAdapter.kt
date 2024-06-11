package com.binary.memory.module.flashcard.adapter

import com.binary.memory.R
import com.binary.memory.base.DraculaAdapter
import com.binary.memory.databinding.ListItemFlashGroupBinding
import com.binary.memory.model.FlashGroup

class FlashGroupListAdapter(
    flashGroupList: MutableList<FlashGroup>
) : DraculaAdapter<FlashGroup, ListItemFlashGroupBinding>(flashGroupList) {

    override fun getLayoutResId(): Int {
        return R.layout.list_item_flash_group
    }

    override fun onBindData(binding: ListItemFlashGroupBinding, item: FlashGroup) {
        binding.flashGroup = item
    }
}