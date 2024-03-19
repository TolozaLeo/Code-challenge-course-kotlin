package com.cursokotlin.codechallenge.data.internal.adapteritems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicItem(
    val name: String,
    val year: String,
): Parcelable