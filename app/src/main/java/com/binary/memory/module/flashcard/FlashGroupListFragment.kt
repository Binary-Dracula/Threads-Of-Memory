package com.binary.memory.module.flashcard

import android.view.View
import com.binary.memory.R
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentFlashGroupListBinding

class FlashGroupListFragment : DraculaFragment<FragmentFlashGroupListBinding>(),
    View.OnClickListener {

//    private val adapter = FlashGroupListAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_flash_group_list

    override fun initView() {
        viewBinding.addFlashGroup.setOnClickListener(this)
//        viewBinding.flashGroupList.adapter = adapter
    }

    override fun initObserver() {

    }

    override fun onClick(v: View?) {
        when (v) {
            viewBinding.addFlashGroup -> {}
        }
    }
}