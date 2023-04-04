package com.entexy.gardenguru.ui.fragments.support.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.RvItemNewPhotoBinding
import com.entexy.gardenguru.utils.convertPxToDp
import com.entexy.gardenguru.utils.setDrawable
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import java.io.File

class AttachmentsRecyclerAdapter(
    var list: List<FileModel>,
    private val changeAdapterModeCallback: () -> Unit,
    private val selectFileToDeleteCallback: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<AttachmentsRecyclerAdapter.NewPhotoViewHolder>() {

    var mode: AdapterMode = AdapterMode.Default

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewPhotoViewHolder(
        RvItemNewPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewPhotoViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            setMargin(position)
            ivPhoto.setPhoto(item.file)
            root.setOnLongClickListener {
                changeAdapterModeCallback.invoke()
                item.isSelected = true
                true
            }
            root.setBackgroundResource(R.drawable.edit_text_background_focus)
            if (mode != AdapterMode.Default) {
                if (mode == AdapterMode.SelectException) root.setBackgroundResource(R.drawable.edit_text_background_error)
                checkbox.toVisible()
                checkbox.changeStateTo(item.isSelected, mode)
                root.setOnClickListener {
                    item.isSelected = !item.isSelected
                    selectFileToDeleteCallback.invoke(position, item.isSelected)
                    checkbox.changeStateTo(item.isSelected, mode)
                }
            } else {
                checkbox.toGone()
                root.setOnClickListener(null)
            }
        }
    }

    private fun RvItemNewPhotoBinding.setMargin(itemPosition: Int) {
        root.layoutParams = ViewGroup.MarginLayoutParams(root.context.convertPxToDp(61f), root.context.convertPxToDp(61f)).apply {
            leftMargin = root.context.convertPxToDp(if (itemPosition == 0) 35f else 10f)
        }
    }

    private fun ImageView.setPhoto(file: File) {
        Glide.with(context).load(file).fitCenter()
            .placeholder(ContextCompat.getDrawable(context, R.drawable.plant_placeholder))
            .transform(CenterCrop(), RoundedCorners(context.convertPxToDp(10F))).into(this)
    }

    private fun ImageView.changeStateTo(isSelected: Boolean, mode: AdapterMode) {
        if (mode == AdapterMode.SelectException)
            setDrawable(if (isSelected) R.drawable.ic_check_on_exc else R.drawable.ic_check_off_exc)
        else
            setDrawable(if (isSelected) R.drawable.ic_check_on else R.drawable.ic_check_off)
    }

    fun updateData(list: List<FileModel>) {
        val diffCallback = AttachmentsDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    class NewPhotoViewHolder(val binding: RvItemNewPhotoBinding) : RecyclerView.ViewHolder(binding.root)

}
