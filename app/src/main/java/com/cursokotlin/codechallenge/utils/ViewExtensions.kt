package com.cursokotlin.codechallenge.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}