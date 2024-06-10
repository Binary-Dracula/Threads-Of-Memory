package com.binary.memory.module.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ActivityFlashcardDetailBinding
import com.binary.memory.entity.DifficultyLevel
import com.binary.memory.model.Flashcard
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory

class FlashcardDetailActivity : DraculaActivity<ActivityFlashcardDetailBinding>() {

    companion object {
        fun start(flashcardGroupId: Int, flashcardId: Int, context: Context) {
            val bundle = Bundle().apply {
                putInt(Constants.KEY_FLASHCARD_GROUP_ID, flashcardGroupId)
                putInt(Constants.KEY_FLASHCARD_ID, flashcardId)
            }
            val intent = Intent(context, FlashcardDetailActivity::class.java).apply {
                putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }

    private val viewModel by viewModels<FlashcardViewModel> {
        FlashcardViewModelFactory(
            application,
            (application as DraculaApplication).flashcardRepository
        )
    }

    override fun layoutId(): Int {
        return R.layout.activity_flashcard_detail
    }

    override fun initView() {
        super.initView()
        initToolbar(findViewById(R.id.toolbar), true, getString(R.string.flashcard))
        viewBinding.viewOptionGroup.init(viewModel.difficultyLevel, ::onDifficultyLevelChanged)
        viewBinding.btnShowBack.setOnClickListener(this)
        viewBinding.previousFlashcard.setOnClickListener(this)
        viewBinding.nextFlashcard.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        viewModel.loadCurrentFlashcard(
            flashcardGroupId = intent.extras?.getInt(Constants.KEY_FLASHCARD_GROUP_ID, -1) ?: -1,
            flashcardId = intent.extras?.getInt(Constants.KEY_FLASHCARD_ID, -1) ?: -1
        )
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.currentFlashcard.observe(this) {
            setFlashcard(it)
        }
    }

    private fun setFlashcard(flashcard: Flashcard?) {
        viewBinding.tvFlashcardBack.visibility = View.INVISIBLE

        if (flashcard == null) {
            //error
            Toast.makeText(this, R.string.no_more, Toast.LENGTH_SHORT).show()
            return
        }
        viewBinding.flashcard = flashcard
        viewBinding.executePendingBindings()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            viewBinding.btnShowBack -> {
                viewBinding.tvFlashcardBack.isVisible = true
            }

            viewBinding.previousFlashcard -> {
                viewModel.loadPreviousFlashcard()
            }

            viewBinding.nextFlashcard -> {
                viewModel.loadNextFlashcard()
            }
        }
    }

    private fun onDifficultyLevelChanged(difficultyLevel: DifficultyLevel) {
        if (!viewBinding.tvFlashcardBack.isVisible) return
    }
}