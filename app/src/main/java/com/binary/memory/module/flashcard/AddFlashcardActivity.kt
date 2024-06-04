package com.binary.memory.module.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.base.DraculaApplication
import com.binary.memory.databinding.ActivityAddFlashcardBinding
import com.binary.memory.model.FlashGroup
import com.binary.memory.viewmodel.FlashcardViewModel
import com.binary.memory.viewmodel.FlashcardViewModelFactory

class AddFlashcardActivity : DraculaActivity<ActivityAddFlashcardBinding>(), View.OnClickListener {

    private val viewModel by viewModels<FlashcardViewModel> {
        FlashcardViewModelFactory(
            application,
            (application as DraculaApplication).flashcardRepository
        )
    }

    companion object {
        fun start(flashGroup: FlashGroup, context: Context) {
            val bundle = Bundle()
            bundle.putParcelable("flashGroup", flashGroup)
            val intent = Intent(context, AddFlashcardActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private fun getFlashGroup(): FlashGroup {
        return intent.extras?.getParcelable("flashGroup")!!
    }

    override fun layoutId(): Int {
        return R.layout.activity_add_flashcard
    }

    override fun initView() {
        super.initView()
        initToolbar(findViewById(R.id.toolbar), true, getString(R.string.add_flash_card))

        viewBinding.btnSave.setOnClickListener(this)
    }

    override fun initObserver() {
        viewModel.insertFlashcardSuccess.observe(this) {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> {
                viewModel.insertFlashcard(
                    viewBinding.etQuestion.text.toString(),
                    viewBinding.etAnswer.text.toString(),
                    getFlashGroup().id
                )
            }
        }
    }

}