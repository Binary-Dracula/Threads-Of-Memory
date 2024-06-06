package com.binary.memory.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class DraculaAdapter<T, VB : ViewDataBinding>(
    private var items: MutableList<T>
) : RecyclerView.Adapter<DraculaAdapter<T, VB>.BaseViewHolder>() {

    abstract fun getLayoutResId(): Int

    abstract fun onBindData(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(
            inflater,
            getLayoutResId(),
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    // 使用 DiffUtil 计算差异
    fun setItems(
        newItems: List<T>,
        areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
        areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
    ) {
        val diffCallback = DraculaDiffCallback(
            items, newItems,
            areItemsTheSame,
            areContentsTheSame
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class BaseViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            onBindData(binding, item)
            binding.executePendingBindings()
        }
    }

    var onItemClickListener: ((T) -> Unit)? = null
}