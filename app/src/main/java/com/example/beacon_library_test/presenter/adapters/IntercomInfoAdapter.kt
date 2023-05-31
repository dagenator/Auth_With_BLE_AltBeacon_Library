package com.example.beacon_library_test.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beacon_library_test.data.models.IntercomViewInfo
import com.example.beacon_library_test.databinding.IntercomInfoItemBinding

class IntercomInfoAdapter :
    ListAdapter<IntercomViewInfo, IntercomInfoAdapter.IntercomInfoViewHolder>(
        IntercomInfoDiffCallBack
    ) {

    class IntercomInfoViewHolder(var binding: IntercomInfoItemBinding) :
        RecyclerView.ViewHolder(IntercomInfoViewHolder(binding).itemView) {
        fun bind(info: IntercomViewInfo) {
            binding.intercomInfoItemTitle.text = info.title
            binding.intercomInfoItemContent.text = info.info
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IntercomInfoViewHolder {
        val binding = IntercomInfoItemBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return IntercomInfoViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: IntercomInfoViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    object IntercomInfoDiffCallBack : DiffUtil.ItemCallback<IntercomViewInfo>() {
        override fun areItemsTheSame(
            oldItem: IntercomViewInfo,
            newItem: IntercomViewInfo
        ): Boolean =
            oldItem.title == newItem.title && oldItem.info == newItem.info

        override fun areContentsTheSame(
            oldItem: IntercomViewInfo,
            newItem: IntercomViewInfo
        ): Boolean =
            oldItem == newItem
    }
}