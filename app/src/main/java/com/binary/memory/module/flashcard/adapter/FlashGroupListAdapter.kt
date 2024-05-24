package com.binary.memory.module.flashcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binary.memory.databinding.ListItemFlashGroupBinding
import com.binary.memory.model.FlashGroup

class FlashGroupListAdapter : RecyclerView.Adapter<FlashGroupListAdapter.FlashGroupHolder>() {

    private val flashGroupList: MutableList<FlashGroup> = mutableListOf()

    var flashGroupItemClickListener: ((FlashGroup) -> Unit)? = null

    fun updateFlashGroups(newFlashGroups: List<FlashGroup>) {
        flashGroupList.clear()
        flashGroupList.addAll(newFlashGroups)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashGroupHolder {
        return FlashGroupHolder(
            ListItemFlashGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FlashGroupHolder, position: Int) {
        holder.bind(flashGroupList[position])
    }

    override fun getItemCount(): Int {
        return flashGroupList.size
    }

    inner class FlashGroupHolder(private val binding: ListItemFlashGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flashGroup: FlashGroup) {
            binding.flashGroup = flashGroup
            binding.executePendingBindings()

            binding.root.tag = flashGroup
            binding.root.setOnClickListener { view ->
                flashGroupItemClickListener?.invoke(view.tag as FlashGroup)
            }
        }
    }

}