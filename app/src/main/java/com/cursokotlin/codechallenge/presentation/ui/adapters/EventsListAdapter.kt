package com.cursokotlin.codechallenge.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.EventAdapterItem
import com.cursokotlin.codechallenge.databinding.ItemCharacterBinding

//TODO ADD LISTENER
class EventsListAdapter : ListAdapter<EventAdapterItem, EventsListAdapter.EventViewHolder>(diffCallbackEvents){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.onBind(currentList[holder.adapterPosition])
    }

    class EventViewHolder(private val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: EventAdapterItem){
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            Glide.with(binding.root.context).load(item.imageUrl).into(binding.imgCharacter)
        }
    }
}

private val diffCallbackEvents = object : DiffUtil.ItemCallback<EventAdapterItem>() {
    override fun areItemsTheSame(oldItem: EventAdapterItem, newItem: EventAdapterItem): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: EventAdapterItem, newItem: EventAdapterItem): Boolean {
        return (oldItem.id == newItem.id)
    }
}