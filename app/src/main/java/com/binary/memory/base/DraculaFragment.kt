package com.binary.memory.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class DraculaFragment<T : ViewDataBinding> : Fragment() {


    private var _view: T? = null

    val viewBinding get() = _view!!

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initData()
    }

    abstract fun initView()

    abstract fun initObserver()

    open fun initData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _view = null
    }

}