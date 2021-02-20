package com.pocketkarma.fastpaste

import android.content.*
import androidx.core.content.ContextCompat

class CopyToClipboardReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val label = intent?.getStringExtra(CLIPBOARD_LABEL) ?: "label_error"
            val payload = intent?.getStringExtra(CLIPBOARD_PAYLOAD) ?: "payload_error"
            val clip = ClipData.newPlainText(label, payload)

            val clipboard =
                ContextCompat.getSystemService(it, ClipboardManager::class.java) as ClipboardManager
            clipboard.setPrimaryClip(clip)
        }
    }
}