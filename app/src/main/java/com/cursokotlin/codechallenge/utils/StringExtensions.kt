package com.cursokotlin.codechallenge.utils

fun String.replaceHttpForHttps(): String {
    return replace("http://", "https://")
}