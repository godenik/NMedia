package ru.netology.nmedia.utils

fun Int.formatCount(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1_000_000 -> {
            val value = this / 1000.0
            "${"%.1f".format(value).removeSuffix(",0")}K"
        }

        else -> {
            val value = this / 1_000_000.0
            "${"%.1f".format(value).removeSuffix(",0")}M"
        }

    }
}