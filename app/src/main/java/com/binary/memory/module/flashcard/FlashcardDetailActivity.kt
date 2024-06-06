package com.binary.memory.module.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.databinding.ActivityFlashcardDetailBinding
import com.binary.memory.entity.DifficultyLevel
import com.binary.memory.model.Flashcard
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory

class FlashcardDetailActivity : DraculaActivity<ActivityFlashcardDetailBinding>() {

    companion object {
        fun start(flashcard: Flashcard, context: Context) {
            val bundle = Bundle().apply {
                putParcelable("flashcard", flashcard)
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

        val flashcard = intent.getParcelableExtra<Flashcard>("flashcard")
        viewBinding.flashcard = flashcard
        viewBinding.executePendingBindings()

        initToolbar(findViewById(R.id.toolbar), true, getString(R.string.flashcard))

        viewBinding.btnShowBack.setOnClickListener(this)

        viewBinding.viewOptionGroup.init(viewModel.difficultyLevel, ::onDifficultyLevelChanged)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            viewBinding.btnShowBack -> {
                viewBinding.tvFlashcardBack.isVisible = true
            }
        }
    }

    private fun onDifficultyLevelChanged(difficultyLevel: DifficultyLevel) {
        if (!viewBinding.tvFlashcardBack.isVisible) return
    }
}