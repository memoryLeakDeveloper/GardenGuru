package com.entexy.gardenguru.ui.fragments.support.adapter

import androidx.recyclerview.widget.DiffUtil

class AttachmentsDiffCallback(private val oldList: List<FileModel>, private val newList: List<FileModel>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].file == newList[newItemPosition].file

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].file == newList[newItemPosition].file

}