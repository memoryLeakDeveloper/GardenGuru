package com.entexy.gardenguru.ui.fragments.settings.support

import androidx.recyclerview.widget.DiffUtil
import java.io.File

class AttachmentsDiffCallback(private val oldList: List<File>, private val newList: List<File>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}