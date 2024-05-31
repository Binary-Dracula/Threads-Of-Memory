package com.binary.memory.module.flashcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.binary.memory.R
import com.binary.memory.base.DraculaActivity
import com.binary.memory.databinding.ActivityFlashcardListBinding
import com.binary.memory.model.FlashGroup

class FlashcardListActivity : DraculaActivity<ActivityFlashcardListBinding>() {

    companion object {
        fun start(flashGroup: FlashGroup, context: Context) {
            val bundle = Bundle()
            bundle.putParcelable("flashGroup", flashGroup)
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
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                FlashcardListFragment(intent.extras?.getParcelable("flashGroup"))
            )
            .commitNow()
    }
}