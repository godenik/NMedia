package ru.netology.nmedia.utils

import kotlin.math.floor

fun Int.formatCount(): String {
    return when {
        this < 1000 -> "$this"
        this <10_000 -> "%.1fK".format(floor(this / 100.0)/10)
        this < 1_000_000 -> "${this / 1_000}K"
        else -> "%.1fM".format(floor(this / 100_000.0)/10)
        }.replace(",0", "").replace(".0", "")

    }