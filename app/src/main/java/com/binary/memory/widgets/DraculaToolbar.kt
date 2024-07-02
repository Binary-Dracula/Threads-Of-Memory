package com.binary.memory.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.MenuRes
import com.binary.memory.databinding.DraculaToolbarBinding
import com.google.android.material.appbar.AppBarLayout

class DraculaToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr) {

    private val vb: DraculaToolbarBinding =
        DraculaToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(title: String) {
        vb.draculaToolbar.title = title
    }

    fun setTitle(title: Int) {
        vb.draculaToolbar.setTitle(title)
    }

    fun setSubtitle(subtitle: String) {
        vb.draculaToolbar.subtitle = subtitle
    }

    fun setNavigationIcon(icon: Int) {
        vb.draculaToolbar.setNavigationIcon(icon)
    }

    fun setNavigationOnClickListener(listener: () -> Unit) {
        vb.draculaToolbar.setNavigationOnClickListener { listener.invoke() }
    }

    fun setOnMenuItemClickListener(listener: (Int) -> Boolean) {
        vb.draculaToolbar.setOnMenuItemClickListener { listener.invoke(it.itemId) }
    }

    fun setMenu(@MenuRes menu: Int) {
        vb.draculaToolbar.menu.clear()
        vb.draculaToolbar.inflateMenu(menu)
    }


}