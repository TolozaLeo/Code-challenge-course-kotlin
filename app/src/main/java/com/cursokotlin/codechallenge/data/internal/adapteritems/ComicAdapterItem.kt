package com.cursokotlin.codechallenge.data.internal.adapteritems

data class ComicAdapterItem(
    val headerModel: ComicAdapterHeaderItem,
    val comics: List<ComicItem>,
)

data class ComicAdapterHeaderItem(
    val description: String,
    val imageUrl: String,
)