package com.binary.memory.module.flashcard

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.binary.memory.R
import com.binary.memory.base.DraculaApplication
import com.binary.memory.base.DraculaFragment
import com.binary.memory.databinding.FragmentFlashcardListBinding
import com.binary.memory.model.FlashGroup
import com.binary.memory.model.Flashcard
import com.binary.memory.module.flashcard.adapter.FlashcardListAdapter
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory
import kotlinx.coroutines.launch

class FlashcardListFragment(val flashGroup: FlashGroup) :
    DraculaFragment<FragmentFlashcardListBinding>() {

    private val viewModel by viewModels<FlashcardViewModel> {
        FlashcardViewModelFactory(
            requireActivity().application,
            (requireActivity().application as DraculaApplication).flashcardRepository
        )
    }

    // adapter
    private lateinit var adapter: FlashcardListAdapter

    override val layoutId: Int
        get() = R.layout.fragment_flashcard_list

    override fun initView() {
        adapter = FlashcardListAdapter(mutableListOf())
        adapter.onItemClickListener = ::onFlashcardClickCallback
        viewBinding.flashcardList.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.flashcardList.adapter = adapter
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.getFlashcardList(flashGroup.id).collect {
                adapter.setItems(
                    it,
                    areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
                    areContentsTheSame = { oldItem, newItem ->
                        oldItem.id == newItem.id
                                && oldItem.front == newItem.front
                                && oldItem.back == newItem.back
                    }
                )
            }
        }
    }

    private fun onFlashcardClickCallback(flashcard: Flashcard) {
        FlashcardDetailActivity.start(flashGroup.id, flashcard.id, requireContext())
    }
}
