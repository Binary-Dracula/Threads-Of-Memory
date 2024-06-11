package com.binary.memory.module.flashcard

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentFlashGroupListBinding
import com.binary.memory.model.FlashGroup
import com.binary.memory.module.flashcard.adapter.FlashGroupListAdapter
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory
import kotlinx.coroutines.launch

class FlashGroupListFragment private constructor(): DraculaFragment<FragmentFlashGroupListBinding>(),
    View.OnClickListener {

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
        viewBinding.addFlashGroup.setOnClickListener(this)
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
            viewBinding.flashGroupName.editText?.text = null
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            viewBinding.addFlashGroup -> {
                viewModel.insertFlashGroup(viewBinding.flashGroupName.editText?.text.toString())
            }
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