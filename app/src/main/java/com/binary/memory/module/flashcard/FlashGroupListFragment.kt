package com.binary.memory.module.flashcard

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentFlashGroupListBinding
import com.binary.memory.model.FlashGroup
import com.binary.memory.module.flashcard.adapter.FlashGroupListAdapter
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory

class FlashGroupListFragment : DraculaFragment<FragmentFlashGroupListBinding>(),
    View.OnClickListener {

    private val viewModel by viewModels<FlashcardViewModel> {
        FlashcardViewModelFactory(
            requireActivity().application,
            (requireActivity().application as DraculaApplication).flashcardRepository
        )
    }

    private val adapter = FlashGroupListAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_flash_group_list

    override fun initView() {
        viewBinding.flashGroupList.adapter = adapter
        viewBinding.addFlashGroup.setOnClickListener(this)
        adapter.flashGroupItemClickListener = ::flashGroupItemClickListener
    }

    override fun initObserver() {
        viewModel.flashGroups.observe(this) {
            adapter.updateFlashGroups(it)
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
        Toast.makeText(requireContext(), flashGroup.flashGroupTitle, Toast.LENGTH_SHORT).show()
    }
}