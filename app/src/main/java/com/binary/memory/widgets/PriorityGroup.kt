package com.binary.memory.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.binary.memory.R
import com.binary.memory.constants.Constants
import com.binary.memory.databinding.ListItemPriorityBinding
import com.binary.memory.databinding.ViewPriorityGroupBinding

// 自定义优先级view
class PriorityGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var priorityGroupViewBinding: ViewPriorityGroupBinding

    init {
        priorityGroupViewBinding = ViewPriorityGroupBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    // 优先级点击函数
    private var onPriorityClickListener: ((Constants.Priority) -> Unit)? = null
    fun setOnPriorityClickListener(listener: (Constants.Priority) -> Unit) {
        onPriorityClickListener = listener
    }

    fun setPriority(
        priorities: List<Constants.Priority>,
        defaultPriority: Int = Constants.Priority.LOW.getOrdinal()
    ) {
        val adapter = PriorityGroupAdapter(defaultPriority)
        priorityGroupViewBinding.priorityGroup.adapter = adapter
        adapter.setPriorityList(priorities)
    }

    // adapter class
    private inner class PriorityGroupAdapter(defaultPriority: Int) :
        RecyclerView.Adapter<PriorityGroupAdapter.ViewHolder>() {

        private val priorityList = arrayListOf<Constants.Priority>()

        // 被点击的position
        private var clickPosition = defaultPriority

        fun setPriorityList(priorityList: List<Constants.Priority>) {
            this.priorityList.clear()
            this.priorityList.addAll(priorityList)
            notifyItemChanged(0, priorityList.size)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ListItemPriorityBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val priority = priorityList[position]
            holder.setPriority(priority, position)
        }

        override fun getItemCount(): Int {
            return priorityList.size
        }

        inner class ViewHolder(private val binding: ListItemPriorityBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    val tag = it.tag as Pair<*, *>
                    onPriorityClickListener?.invoke(tag.first as Constants.Priority)
                    clickPosition = tag.second as Int
                    notifyItemRangeChanged(0, priorityList.size)
                }
            }

            fun setPriority(priority: Constants.Priority, position: Int) {
                if (clickPosition == position) {
                    binding.root.setBackgroundColor(context.getColor(R.color.purple_200))
                } else {
                    binding.root.setBackgroundResource(0)
                }
                binding.priority.text = priority.getPriorityString(context)
                binding.root.tag = Pair(priority, position)
            }
        }

    }

}