package mohamed.omar.com.muslimdailydhikr2.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import mohamed.omar.com.muslimdailydhikr2.DisplayPrayerActivity

object NotificationUtil {

    private val DEFAULT_CHANNEL_ID   = "prayers_athan_channel"
    private val DEFAULT_CHANNEL_NAME = "Prayers Athan Channel"
    var NOTIFICATION_WITH_INTENT_ID  = 1

    /*
    var SIMPLE_NOTIFICATION_ID            = 2
    var BIG_TEXT_STYLE_NOTIFICATION_ID    = 3
    var INBOX_STYLE_NOTIFICATION_ID       = 4
    var BIG_PICTURE_STYLE_NOTIFICATION_ID = 5
    var MESSAGING_STYLE_NOTIFICATION_ID   = 6
    */

    /*
     * Create NotificationChannel as required from Android 8.0 (Oreo)
     * */
    fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(NotificationChannel(
                        DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ))
            }
        }
    }

    fun createNotificationWithContentIntent(context: Context, title: String, text: String, bigLargeIcon: Int, bigPictureResource: Int): Notification {
        val intent        = Intent(context, DisplayPrayerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Big picture
        val style = NotificationCompat.BigPictureStyle()
        style.setBigContentTitle(title)
        style.setSummaryText(text)
        style.bigPicture(BitmapFactory.decodeResource(context.resources, bigPictureResource))
        style.bigLargeIcon(BitmapFactory.decodeResource(context.resources, bigLargeIcon))

        /*
        // Big text
        val style = NotificationCompat.BigTextStyle()
        style.bigText(bigText)
        style.setBigContentTitle(title)
        style.setSummaryText(text)
        //val RGB = android.graphics.Color.rgb(14, 140, 5)  // colorPrimaryDark: #0e8c05
        */

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_menu_send)
            .setAutoCancel(true)  // Close notification after click
            .setContentIntent(pendingIntent)
            .setStyle(style)
            //.setColor(RGB)
        return builder.build()
    }

    /*
    fun createSimpleNotification(context: Context, title: String, text: String): Notification {
        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setVibrate(longArrayOf(250, 250, 250, 250))

        return builder.build()
    }

    fun createBigTextStyleNotification(context: Context, title: String, text: String, bigText: String): Notification {
        val style = NotificationCompat.BigTextStyle()
        style.bigText(bigText)
        style.setBigContentTitle(title)
        style.setSummaryText(text)

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style)

        return builder.build()
    }

    fun createInboxStyleNotification(context: Context, title: String, text: String, vararg lines: String): Notification {
        val style = NotificationCompat.InboxStyle()
        style.setSummaryText(text)
        style.setBigContentTitle("Big Content title - $title")
        for (line in lines) {
            style.addLine(line)
        }

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style)

        return builder.build()
    }

    fun createBigPictureStyleNotification(context: Context, title: String, text: String, bigPictureResource: Int, bigLargeIcon: Int): Notification {
        val style = NotificationCompat.BigPictureStyle()
        style.setBigContentTitle(title)
        style.setSummaryText(text)
        style.bigPicture(BitmapFactory.decodeResource(context.resources, bigPictureResource))
        style.bigLargeIcon(BitmapFactory.decodeResource(context.resources, bigLargeIcon))

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style)

        return builder.build()
    }

    fun createMessagingStyleNotification(context: Context, title: String, text: String): Notification {
        val style = NotificationCompat.MessagingStyle("Gurleen Sethi")
        style.addMessage(NotificationCompat.MessagingStyle.Message("Testing", Date().time, "Saru Sethi"))
        style.addMessage(NotificationCompat.MessagingStyle.Message("Testing 123", Date().time, "Saru Sethi"))

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style)

        return builder.build()
    }
    */

}