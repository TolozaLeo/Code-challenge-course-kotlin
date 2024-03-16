package com.cursokotlin.codechallenge.data.adapteritems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicAdapterItem(
    val name: String,
): Parcelable