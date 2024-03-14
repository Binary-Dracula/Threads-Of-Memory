package com.binary.memory.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DraculaActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract fun layoutId(): Int

    lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        setContentView(viewBinding.root)

        initView()
        initObserver()
    }

    open fun initView() {}

    fun initToolbar(toolbar: Toolbar, displayHomeAsUp: Boolean, @StringRes titleRes: Int?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUp)
        if (titleRes != null) {
            supportActionBar?.setTitle(titleRes)
        }
    }

    open fun initObserver() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}