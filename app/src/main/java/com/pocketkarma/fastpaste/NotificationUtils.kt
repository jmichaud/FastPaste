package com.pocketkarma.fastpaste

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.GROUP_ALERT_SUMMARY

private const val NOTIFICATION_ID = 0
private const val NOTIFICATION_GROUP = "com.pocketkarma.fastpaste.notificationgroup"

const val CLIPBOARD_LABEL = "CLIPBOARD_LABEL"
const val CLIPBOARD_PAYLOAD = "CLIPBOARD_PAYLOAD"


fun NotificationManager.sendNotification(items: List<PasteItem>, appContext: Context) {
    //val summaryBuilder = pendingIntentCreator(pair.index + 1,  item.value, appContext)

    val summaryBuilder = NotificationCompat.Builder(appContext,
        appContext.getString(R.string.fastpaste_channel_id))
        .setSmallIcon(R.drawable.ic_baseline_assignment_turned_in_24)
        .setContentTitle("SummaryTitle")
        .setContentText("SummaryText")
        .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
        .setGroupSummary(true)
        .setSortKey("000")
        .setGroup(NOTIFICATION_GROUP)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notify(0, summaryBuilder.build())

    for(pair in items.withIndex()) {
        val builder = pendingIntentCreator(pair.value, pair.index+1, appContext)
        notify(pair.index+1, builder.build())
    }
}

private fun pendingIntentCreator(item: PasteItem, requestCode: Int, appContext: Context):NotificationCompat.Builder {

    val contentIntent = Intent(appContext, CopyToClipboardReceiver::class.java)
    contentIntent.putExtra(CLIPBOARD_LABEL, item.label)
    contentIntent.putExtra(CLIPBOARD_PAYLOAD, item.payload)

    val contentPendingIntent = PendingIntent.getBroadcast(
        appContext, requestCode,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(appContext,
        appContext.getString(R.string.fastpaste_channel_id))
        .setSmallIcon(R.drawable.ic_baseline_assignment_turned_in_24)
        .setContentIntent(contentPendingIntent)
        .setContentTitle(item.label)
        .setContentText(item.payload)
        .setSortKey(requestCode.toString().padStart(3, '0'))
        .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
        .setGroup(NOTIFICATION_GROUP)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    return builder
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}