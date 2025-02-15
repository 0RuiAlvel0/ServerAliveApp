package com.example.serveraliveapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.net.InetAddress
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class PingService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        Log.d("DEBUG--", "On create of ping service")
        super.onCreate()
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Ping Service")
            .setContentText("Monitoring server status")
            .setSmallIcon(R.drawable.ic_stat_name)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d("DEBUG--", "On start of ping service")
//        // Pseudo code: Fetch the list of servers from shared preferences or database
//        val servers = listOf("192.168.0.1" to "Server1", "example.com" to "Server2", "example.com" to "Server3")
//        val pingResults = servers.map { (address, name) -> name to pingServer(address) }.toTypedArray()
//
//        // Broadcast ping results to WidgetUpdateReceiver
//        val broadcastIntent = Intent(this, ServerAliveAppWidgetProvider::class.java).apply {
//            action = "com.example.serveraliveapp.PING_RESULT"
//            putExtra("pingResults", pingResults)
//        }
//
//        sendBroadcast(broadcastIntent)
//
//        return START_STICKY
//    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DEBUG--", "On start of ping service")
        serviceScope.launch {
            pingServers()
        }
        return START_STICKY
    }

//    private fun pingServer(server: String): Boolean {
//        Log.d("DEBUG--", "On pingServer" + server)
//        return try {
//            val address = InetAddress.getByName(server)
//            address.isReachable(1000) // 1 second timeout
//        } catch (e: Exception) {
//            Log.e("DEBUG--", "PingService: pingServer failed for $server", e)
//            false
//        }
//    }
    private suspend fun pingServers() {
        val servers = listOf("https://google.com" to "Google", "https://example.com" to "Server2", "https://test.com" to "Server3")
        val pingResults = servers.map { (address, name) -> name to checkServer(address) }.toTypedArray()

        Log.d("DEBUG--", "Ping results 1: ${pingResults.joinToString()}")

        // Broadcast ping results to WidgetUpdateReceiver
        val broadcastIntent = Intent(this@PingService, ServerAliveAppWidgetProvider::class.java).apply {
            action = "com.example.serveraliveapp.PING_RESULT"
            putExtra("pingResults", pingResults)
            Log.d("DEBUG--", "Ping results 2: ${pingResults.joinToString()}")
        }

        sendBroadcast(broadcastIntent)
    }

    private suspend fun pingServer(server: String): Boolean {
        Log.d("DEBUG--", "On pingServer $server")
        return try {
            val address = InetAddress.getByName(server)
            address.isReachable(1000) // 1 second timeout
        } catch (e: Exception) {
            Log.e("DEBUG--", "PingService: pingServer failed for $server", e)
            false
        }
    }

    private suspend fun checkServer(url: String): Boolean {
        return try {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 1000 // 1 second timeout
            urlConnection.responseCode == 200
        } catch (e: Exception) {
            Log.e("DEBUG--", "PingService: checkServer failed for $url", e)
            false
        }
    }

    private fun createNotificationChannel() {
        Log.d("DEBUG--", "On creatNotificationChannel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Ping Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val CHANNEL_ID = "PingServiceChannel"
        const val NOTIFICATION_ID = 1
    }
}