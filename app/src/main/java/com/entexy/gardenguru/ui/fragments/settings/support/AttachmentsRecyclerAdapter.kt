package com.entexy.gardenguru.ui.fragments.settings.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.RvItemNewPhotoBinding
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.convertPxToDp
import java.io.File

class AttachmentsRecyclerAdapter(private var list: List<File>, private val addPhotoCallback: () -> Unit) :
    RecyclerView.Adapter<AttachmentsRecyclerAdapter.NewPhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewPhotoViewHolder(
        RvItemNewPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewPhotoViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(root.context).load(list[position]).fitCenter()
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.plant_placeholder))
                .transform(CenterCrop(), RoundedCorners(root.context.convertPxToDp(10F))).into(ivPhoto)
        }
    }

    fun updateData(list: List<File>) {
        val diffCallback = AttachmentsDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    class NewPhotoViewHolder(val binding: RvItemNewPhotoBinding) : RecyclerView.ViewHolder(binding.root)


}
