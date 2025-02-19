![Alt text](https://github.com/0RuiAlvel0/ServerAliveApp/raw/master/ServerAliveApp.png)

This is a simple website health checker for Android. It consists of a widget and a settings page.
Although I have some experience with Android apps using java, this is my first Kotlin app.

To install/ use, you need to clone the repository, open it with Android studio (or another IDE, but I only have experience with Android Studio) and then run it on an emulator (to see it works) or on you own device (when your device is used as debug device). For any other way, you need to create your own apk. Note that you will want to go to app/src/main/java/com/example/serveraliveapp/PingService.kt and edit to include your own server names and server URL.

The widget shows a green (URL reachable) or red icon (URL not reachable).

Current version (ALPHA):

- URLS + server name on widget are hardcoded.
- Check interval is hardcoded.
- Add as many servers as can fit on the 4 horizontal spaces of the widget.

Future features (P1 - actively under implementation; P4 - later):

- P1 Add an app icon (that shows on the app drawer, currently you have the default)
- P1 URLs + server names can be added on the settings page.
- P1 Check interval can be customized on the settings page.
- P2 Widget contents dynamically adjusts to the amount of servers defined.
- P3 I defined the service icon to show at the top of the screen to indicate that the service is running. For now I can't make it show.
- P3 Notification to be issued when one of the servers is detected down.
- P3 Check ssl certificate dates and warn user when close to the expiry date.
- P4 Setup of cloud server to email a list of emails when a server goes down.
