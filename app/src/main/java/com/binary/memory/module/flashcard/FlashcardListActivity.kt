package com.binary.memory.module.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ActivityFlashcardListBinding
import com.binary.memory.model.FlashGroup

class FlashcardListActivity : DraculaActivity<ActivityFlashcardListBinding>(),
    View.OnClickListener {

    companion object {
        fun start(flashGroup: FlashGroup, context: Context) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_FLASHCARD_GROUP, flashGroup)
            val intent = Intent(context, FlashcardListActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_flashcard_list
    }

    override fun initView() {
        super.initView()
        initToolbar(
            findViewById(R.id.toolbar),
            true,
            intent.extras?.getParcelable<FlashGroup>(Constants.KEY_FLASHCARD_GROUP)?.flashGroupTitle
        )

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                FlashcardListFragment(intent.extras?.getParcelable(Constants.KEY_FLASHCARD_GROUP)!!)
            )
            .commitNow()

        viewBinding.addFlashCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.add_flash_card -> {
                AddFlashcardActivity.start(
                    intent.extras?.getParcelable<FlashGroup>(Constants.KEY_FLASHCARD_GROUP)!!.id,
                    this
                )
            }
        }
    }
}