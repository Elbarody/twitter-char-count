package com.elbarody.base.helpers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun copyTextToClipboard(text: String,context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Tweet Text", text)
    clipboard.setPrimaryClip(clip)
}
