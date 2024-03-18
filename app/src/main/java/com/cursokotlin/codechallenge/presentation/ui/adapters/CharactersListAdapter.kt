package com.cursokotlin.codechallenge.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cursokotlin.codechallenge.data.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.databinding.ItemCharacterBinding

//TODO ADD LISTENER
class CharactersListAdapter : ListAdapter<CharacterAdapterItem, CharactersListAdapter.CharacterViewHolder>(diffCallbackCharacters){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.onBind(currentList[holder.adapterPosition])
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: CharacterAdapterItem){
            binding.tvTitle.text = item.name
            binding.tvDescription.text = item.description
            Glide.with(binding.root.context).load(item.imageUrl).into(binding.imgCharacter)
        }
    }

}

private val diffCallbackCharacters = object : DiffUtil.ItemCallback<CharacterAdapterItem>() {
    override fun areItemsTheSame(oldItem: CharacterAdapterItem, newItem: CharacterAdapterItem): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: CharacterAdapterItem, newItem: CharacterAdapterItem): Boolean {
        return (oldItem.id == newItem.id)
    }
}