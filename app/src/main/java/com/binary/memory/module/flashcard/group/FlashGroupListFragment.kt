package com.binary.memory.module.flashcard.group

import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentFlashGroupListBinding
import com.binary.memory.model.FlashGroup
import com.binary.memory.module.flashcard.FlashcardListActivity
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory
import kotlinx.coroutines.launch

class FlashGroupListFragment private constructor() :
    DraculaFragment<FragmentFlashGroupListBinding>() {


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    private val viewModel by viewModels<FlashcardViewModel> {
        FlashcardViewModelFactory(
            requireActivity().application,
            (requireActivity().application as DraculaApplication).flashcardRepository
        )
    }

    private val adapter by lazy {
        FlashGroupListAdapter(mutableListOf())
    }

    override val layoutId: Int
        get() = R.layout.fragment_flash_group_list

    override fun initView() {
        viewBinding.flashGroupList.adapter = adapter
        adapter.onItemClickListener = ::flashGroupItemClickListener
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.getFlashGroups().collect {
                adapter.setItems(
                    it,
                    areItemsTheSame = { oldFlashGroup, newFlashGroup ->
                        oldFlashGroup.id == newFlashGroup.id
                    },
                    areContentsTheSame = { oldFlashGroup, newFlashGroup ->
                        oldFlashGroup.flashGroupTitle == newFlashGroup.flashGroupTitle &&
                                oldFlashGroup.flashGroupDescription == newFlashGroup.flashGroupDescription
                    }
                )
            }
        }
        viewModel.insertFlashGroupSuccess.observe(this) {

        }
    }

    private fun flashGroupItemClickListener(flashGroup: FlashGroup) {
        // 进入闪卡列表
        FlashcardListActivity.start(flashGroup, requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FlashGroupListFragment()
    }
}