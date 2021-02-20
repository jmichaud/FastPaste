package com.pocketkarma.fastpaste

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChannel(
            getString(R.string.fastpaste_channel_id),
            getString(R.string.fastpaste_channel_name)
        )

        val hardCodedItems = listOf(
            PasteItem("Login1", "User1"),
            PasteItem("Pass1", "mypassword"),
            PasteItem("Login2", "User2"),
            PasteItem("Pass2", "abcd1234")
        )

        val notificationManager = ContextCompat.getSystemService(this.applicationContext,
            NotificationManager::class.java) as NotificationManager

        notificationManager.sendNotification(hardCodedItems, this.applicationContext)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.description = "Showing FastPaste"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }
}