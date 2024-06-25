package com.binary.memory.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.binary.memory.R
import com.binary.memory.databinding.ListItemOptionBinding
import com.binary.memory.databinding.ViewOptionGroupBinding

class ViewOptionGroup<T : ViewOptionGroup.IOption> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewOptionGroupBinding = ViewOptionGroupBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var unselectedColor = context.getColor(R.color.colorSurface)
    private var selectedColor = context.getColor(R.color.colorPrimary)
    private val adapter = OptionGroupAdapter<T>()

    init {
        binding.optionGroup.adapter = adapter
    }

    fun setUnselectedColor(color: Int) {
        unselectedColor = color
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }

    fun init(optionList: List<T>, listener: (T) -> Unit) {
        if (optionList.isEmpty()) return
        adapter.setOptionList(optionList)
        adapter.setOnItemClickListener(listener)
        listener.invoke(optionList[0])
    }

    fun selected(position: Int) {
        adapter.selected(position)
    }

    private inner class OptionGroupAdapter<T : IOption> :
        RecyclerView.Adapter<OptionGroupAdapter<T>.ViewHolder>() {

        private val optionList = arrayListOf<T>()
        private var selectedPosition = 0

        private var onItemClickListener: ((T) -> Unit)? = null

        fun setOnItemClickListener(listener: (T) -> Unit) {
            onItemClickListener = listener
        }

        fun setOptionList(optionList: List<T>) {
            this.optionList.clear()
            this.optionList.addAll(optionList)
            notifyItemRangeChanged(0, optionList.size)
        }

        fun selected(position: Int) {
            selectedPosition = position
            notifyItemRangeChanged(0, optionList.size)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ListItemOptionBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setOption(optionList[position], position)
        }

        override fun getItemCount(): Int {
            return optionList.size
        }

        inner class ViewHolder(private val binding: ListItemOptionBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun setOption(option: T, position: Int) {
                if (selectedPosition == position) {
                    binding.root.setBackgroundColor(selectedColor)
                } else {
                    binding.root.setBackgroundColor(unselectedColor)
                }
                binding.level.text = option.getOptionString()
                binding.root.setOnClickListener {
                    selectedPosition = position
                    notifyItemRangeChanged(0, optionList.size)
                    onItemClickListener?.invoke(option)
                }
            }
        }
    }

    interface IOption {
        fun getOptionString(): String
        fun getOptionIndex(): Int
    }
}