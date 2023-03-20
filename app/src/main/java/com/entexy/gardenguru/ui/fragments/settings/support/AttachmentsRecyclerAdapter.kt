package com.entexy.gardenguru.ui.fragments.settings.support

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.databinding.RvItemAttachmentFileBinding

class AttachmentsRecyclerAdapter(private val viewModel: SupportViewModel) :
    RecyclerView.Adapter<AttachmentsRecyclerAdapter.EventsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvItemAttachmentFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder.binding) {
            val item = viewModel.files.value!![position]

            tvFileName.text = item.name
            ivCross.setOnClickListener{
                viewModel.removeAtFile(position)
                notifyDataSetChanged()
            }
        }
    }

    class EventsViewHolder(val binding: RvItemAttachmentFileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return viewModel.files.value?.size ?: 0
    }
}
