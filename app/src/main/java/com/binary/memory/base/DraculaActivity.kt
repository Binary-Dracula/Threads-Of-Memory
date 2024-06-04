package com.binary.memory.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DraculaActivity<T : ViewDataBinding> : AppCompatActivity(), View.OnClickListener {

    abstract fun layoutId(): Int

    lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.inflate(layoutInflater, layoutId(), null, false)
        setContentView(viewBinding.root)

        initView()
        initObserver()
        initData()
    }

    inline fun <reified E> getExtra(key: String, onResult: (E?) -> Unit) {
        val value = intent?.extras?.get(key)
        if (value is E) {
            onResult(value)
        } else {
            onResult(null)
        }
    }

    open fun initView() {}

    fun initToolbar(
        toolbar: Toolbar,
        displayHomeAsUp: Boolean,
        title: String?
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUp)
        if (!title.isNullOrBlank()) {
            supportActionBar?.title = title
        }
    }

    open fun initObserver() {}

    open fun initData() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addClickListener(vararg view: View) {
        for (v in view) {
            v.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

}