package com.bernaferrari.sdkmonitor.extensions

import java.text.DecimalFormat

internal fun Int.readableFileSize(): String {
    if (this <= 0) return "EMPTY"
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(this.toDouble()) / Math.log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(
        this / Math.pow(
            1024.0,
            digitGroups.toDouble()
        )
    ) + " " + units[digitGroups]
}
