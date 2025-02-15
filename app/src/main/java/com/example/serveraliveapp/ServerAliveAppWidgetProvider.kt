package com.example.serveraliveapp
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class ServerAliveAppWidgetProvider  : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // This method is called to update all instances of the widget
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.server_alive_widget)

            // Example placeholder code to test the layout (replace with real ping results)
            val pingResults = arrayOf("Server1" to true, "Server2" to false, "Server3" to true)

            // Clear existing views
            views.removeAllViews(R.id.widget_layout)

            // Add ImageViews for each server's status
            pingResults.forEach { (serverName, isAlive) ->
                val statusView = RemoteViews(context.packageName, R.layout.status_view)
                statusView.setTextViewText(R.id.serverName, serverName)
                statusView.setImageViewResource(R.id.status_icon, if (isAlive) R.drawable.green_circle_icon else R.drawable.red_circle_icon)
                views.addView(R.id.widget_layout, statusView)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == "com.example.serveraliveapp.PING_RESULT") {
            val pingResults = intent.getSerializableExtra("pingResults") as? Array<Pair<String, Boolean>>

            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, ServerAliveAppWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

            // Update each widget
            for (appWidgetId in appWidgetIds) {
                val views = RemoteViews(context.packageName, R.layout.server_alive_widget)

                // Clear existing views
                views.removeAllViews(R.id.widget_layout)

                // Add ImageViews for each server's status
                pingResults?.forEach { (serverName, isAlive) ->
                    val statusView = RemoteViews(context.packageName, R.layout.status_view)
                    statusView.setTextViewText(R.id.serverName, serverName)
                    statusView.setImageViewResource(R.id.status_icon, if (isAlive) R.drawable.green_circle_icon else R.drawable.red_circle_icon)
                    views.addView(R.id.widget_layout, statusView)
                }

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }

}