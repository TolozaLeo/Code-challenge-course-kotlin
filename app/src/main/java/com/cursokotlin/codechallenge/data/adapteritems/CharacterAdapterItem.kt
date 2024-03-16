package com.cursokotlin.codechallenge.data.adapteritems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterAdapterItem(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val description: String,
    val comic: List<ComicAdapterItem>,
): Parcelable
