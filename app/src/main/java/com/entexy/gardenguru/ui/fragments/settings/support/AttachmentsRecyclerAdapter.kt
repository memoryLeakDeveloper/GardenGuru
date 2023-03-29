package com.entexy.gardenguru.ui.fragments.settings.support

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
import com.entexy.gardenguru.utils.*

class AttachmentsRecyclerAdapter(
    var list: List<FileModel>,
    private val changeAdapterModeCallback: () -> Unit,
    private val selectFileToDeleteCallback: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<AttachmentsRecyclerAdapter.NewPhotoViewHolder>() {

    var adapterMode: AdapterMode = AdapterMode.Default

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewPhotoViewHolder(
        RvItemNewPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewPhotoViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(root.context).load(list[position].file).fitCenter()
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.plant_placeholder))
                .transform(CenterCrop(), RoundedCorners(root.context.convertPxToDp(10F))).into(ivPhoto)
            root.setOnLongClickListener {
                changeAdapterModeCallback.invoke()
                item.isSelected = true
                true
            }
            if (adapterMode == AdapterMode.Select) {
                checkbox.toVisible()
                checkbox changeStateTo item.isSelected
                root.setOnClickListener {
                    item.isSelected = !item.isSelected
                    selectFileToDeleteCallback.invoke(position, item.isSelected)
                    checkbox changeStateTo item.isSelected
                }
            } else {
                checkbox.toGone()
                root.setOnClickListener(null)
            }
        }
    }

    private infix fun ImageView.changeStateTo(isSelected: Boolean) {
        setDrawable(if (isSelected) R.drawable.ic_checkbox_on else R.drawable.ic_checkbox_off)
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
