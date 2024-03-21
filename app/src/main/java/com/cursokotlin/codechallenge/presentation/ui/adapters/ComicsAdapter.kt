package com.cursokotlin.codechallenge.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterHeaderItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterItem
import com.cursokotlin.codechallenge.databinding.HeaderCharacterDescriptionBinding
import com.cursokotlin.codechallenge.databinding.ItemComicBinding

class ComicsAdapter(
    private val comicAdapterItem: ComicAdapterItem,
) : Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicParentViewHolder {
        when (viewType) {
            HEADER_TYPE -> {
                val binding = HeaderCharacterDescriptionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ComicHeaderViewHolder(binding)
            }

            else -> {
                val binding = ItemComicBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ComicItemViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int) =
        if (position == HEADER_TYPE) HEADER_TYPE else ITEM_TYPE

    class ComicItemViewHolder(
        private val binding: ItemComicBinding,
    ) : ComicParentViewHolder(binding.root) {
        fun onBind(item: ComicItem) {
            binding.tvTitle.text = item.name
            binding.tvDescription.text = item.year
        }
    }

    class ComicHeaderViewHolder(
        private val binding: HeaderCharacterDescriptionBinding,
    ) : ComicParentViewHolder(binding.root) {
        fun onBind(header: ComicAdapterHeaderItem) {
            binding.tvDescription.text = header.description
            Glide.with(binding.root.context).load(header.imageUrl).into(binding.imgCharacter)
        }
    }

    abstract class ComicParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return comicAdapterItem.comics.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicHeaderViewHolder -> {
                holder.onBind(comicAdapterItem.headerModel)
            }

            is ComicItemViewHolder -> {
                holder.onBind(comicAdapterItem.comics[holder.adapterPosition - 1])
            }
        }
    }

    companion object {
        const val HEADER_TYPE = 0
        const val ITEM_TYPE = 1
    }
}