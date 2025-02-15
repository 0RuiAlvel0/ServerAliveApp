package com.example.serveraliveapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.serveraliveapp.ui.theme.ServerAliveAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start PingService as a foreground service
        val serviceIntent = Intent(this, PingService::class.java)
        startForegroundService(serviceIntent)

        // Schedule the PingService to run every 30 seconds
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, PingService::class.java)
        //val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            30 * 1000,
            pendingIntent
        )

        // Start SettingsActivity directly
        val settingsIntent = Intent(this, SettingsActivity::class.java)
        startActivity(settingsIntent)

        // Finish MainActivity so it doesn't stay in the back stack
        finish()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ServerAliveAppTheme {
        Greeting("Android")
    }
}