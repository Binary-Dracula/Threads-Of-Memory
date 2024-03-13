package com.binary.memory.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DraculaActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutId: Int

    lateinit var view: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        setContentView(view.root)

        initView()
        initObserver()
    }

    open fun initView() {}

    open fun initObserver() {}

}