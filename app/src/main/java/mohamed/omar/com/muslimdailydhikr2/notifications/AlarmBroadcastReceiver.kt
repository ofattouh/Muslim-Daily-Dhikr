package mohamed.omar.com.muslimdailydhikr2.notifications

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import mohamed.omar.com.muslimdailydhikr2.DisplayPrayerActivity
import mohamed.omar.com.muslimdailydhikr2.MainActivity
import mohamed.omar.com.muslimdailydhikr2.R
import java.util.*
import android.app.AlarmManager
import android.media.MediaPlayer
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat

class AlarmBroadcastReceiver : BroadcastReceiver() {

    private val TAG                  = AlarmBroadcastReceiver::class.java.simpleName
    private val DEFAULT_CHANNEL_ID   = "prayers_athan_channel"
    private val DEFAULT_CHANNEL_NAME = "Prayers Athan Channel"
    internal var ctx: Context?       = null

    override fun onReceive(context: Context?, intent: Intent?) {
        ctx = context

        var prayerFajr        = intent!!.getStringExtra("prayerFajr")
        var prayerFajrTime    = intent!!.getStringExtra("prayerFajrTime")
        var prayerSunrise     = intent!!.getStringExtra("prayerSunrise")
        var prayerSunriseTime = intent!!.getStringExtra("prayerSunriseTime")
        var prayerDhuhr       = intent!!.getStringExtra("prayerDhuhr")
        var prayerDhuhrTime   = intent!!.getStringExtra("prayerDhuhrTime")
        var prayerAsr         = intent!!.getStringExtra("prayerAsr")
        var prayerAsrTime     = intent!!.getStringExtra("prayerAsrTime")
        var prayerMaghrib     = intent!!.getStringExtra("prayerMaghrib")
        var prayerMaghribTime = intent!!.getStringExtra("prayerMaghribTime")
        var prayerIsha        = intent!!.getStringExtra("prayerIsha")
        var prayerIshaTime    = intent!!.getStringExtra("prayerIshaTime")
        var prayerMorning     = intent!!.getStringExtra("prayerMorning")
        var prayerMorningTime = intent!!.getStringExtra("prayerMorningTime")
        var prayerEvening     = intent!!.getStringExtra("prayerEvening")
        var prayerEveningTime = intent!!.getStringExtra("prayerEveningTime")

        var fajrAthanNotificationSetting    = intent!!.getBooleanExtra("fajrAthanNotificationSetting", false)
        var sunriseAthanNotificationSetting = intent!!.getBooleanExtra("sunriseAthanNotificationSetting", false)
        var dhuhrAthanNotificationSetting   = intent!!.getBooleanExtra("dhuhrAthanNotificationSetting", false)
        var asrAthanNotificationSetting     = intent!!.getBooleanExtra("asrAthanNotificationSetting", false)
        var maghribAthanNotificationSetting = intent!!.getBooleanExtra("maghribAthanNotificationSetting", false)
        var ishaAthanNotificationSetting    = intent!!.getBooleanExtra("ishaAthanNotificationSetting", false)
        var fajrAthanSoundSetting           = intent!!.getBooleanExtra("fajrAthanSoundSetting", false)
        var sunriseAthanSoundSetting        = intent!!.getBooleanExtra("sunriseAthanSoundSetting", false)
        var dhuhrAthanSoundSetting          = intent!!.getBooleanExtra("dhuhrAthanSoundSetting", false)
        var asrAthanSoundSetting            = intent!!.getBooleanExtra("asrAthanSoundSetting", false)
        var maghribAthanSoundSetting        = intent!!.getBooleanExtra("maghribAthanSoundSetting", false)
        var ishaAthanSoundSetting           = intent!!.getBooleanExtra("ishaAthanSoundSetting", false)

        var morningAzkarNotificationSetting = intent!!.getBooleanExtra("morningAzkarNotificationSetting", false)
        var eveningAzkarNotificationSetting = intent!!.getBooleanExtra("eveningAzkarNotificationSetting", false)
        var morningAzkarSoundSetting        = intent!!.getBooleanExtra("morningAzkarSoundSetting", false)
        var eveningAzkarSoundSetting        = intent!!.getBooleanExtra("eveningAzkarSoundSetting", false)

        var timeFormat  = SimpleDateFormat("HH:mm", Locale.US)  // 24 Hrs (API)
        var timeFormat2 = SimpleDateFormat("h:mm a", Locale.US) // AM/PM

     /*  Log.i(TAG, " onReceive: " + prayerFajr + ": " + prayerFajrTime + ": " +
               prayerSunrise + ": " + prayerSunriseTime + ": " +
               prayerDhuhr + ": " + prayerDhuhrTime + ": " +
               prayerAsr + ": " + prayerAsrTime + ": " +
               prayerMaghrib + ": " + prayerMaghribTime + ": " +
               prayerIsha + ": " + prayerIshaTime + ": " +
               prayerMorning + ": " + prayerMorningTime + ": " +
               prayerEvening + ": " + prayerEveningTime + ": " +
               "("+ fajrAthanNotificationSetting + ", "    + fajrAthanSoundSetting + ")- " +
               "("+ sunriseAthanNotificationSetting + ","  + sunriseAthanSoundSetting + ")- " +
               "("+ dhuhrAthanNotificationSetting + ","    + dhuhrAthanSoundSetting + ")- " +
               "("+ asrAthanNotificationSetting + ","      + asrAthanSoundSetting + ")- " +
               "("+ maghribAthanNotificationSetting+ ","   +  maghribAthanSoundSetting + ")- " +
               "("+ ishaAthanNotificationSetting + ","     + ishaAthanSoundSetting + ")- " +
               "("+ morningAzkarNotificationSetting + "," + morningAzkarSoundSetting + ")- " +
               "("+ eveningAzkarNotificationSetting + "," + eveningAzkarSoundSetting + ")" )*/

        // Fajr notification
        if (prayerFajr == "Fajr" && fajrAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerFajr + " at " + timeFormat2.format(timeFormat.parse(prayerFajrTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

            // Prayer Athan
            if (fajrAthanSoundSetting) {
                val mp = MediaPlayer.create(context, R.raw.ahmadalnafees)
                mp.start()
            }
        }

        // Sunrise notification
        if (prayerSunrise == "Sunrise" && sunriseAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerSunrise + " at " + timeFormat2.format(timeFormat.parse(prayerSunriseTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

        }

        // Dhuhr notification
        if (prayerDhuhr == "Dhuhr" && dhuhrAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerDhuhr + " at " + timeFormat2.format(timeFormat.parse(prayerDhuhrTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

            // Prayer Athan
            if (dhuhrAthanSoundSetting) {
                val mp = MediaPlayer.create(context, R.raw.salahmansoorazzahrani)
                mp.start()
            }
        }

        // Asr notification
        if (prayerAsr == "Asr" && asrAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerAsr + " at " + timeFormat2.format(timeFormat.parse(prayerAsrTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

            // Prayer Athan
            if (asrAthanSoundSetting) {
                val mp = MediaPlayer.create(context, R.raw.misharyrashidalafasy)
                mp.start()
            }
        }

        // Maghrib notification
        if (prayerMaghrib == "Maghrib" && maghribAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerMaghrib + " at " + timeFormat2.format(timeFormat.parse(prayerMaghribTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

            // Prayer Athan
            if (maghribAthanSoundSetting) {
                val mp = MediaPlayer.create(context, R.raw.masjidalharammecca)
                mp.start()
            }
        }

        // Isha notification
        if (prayerIsha == "Isha" && ishaAthanNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationTextIntent(
                    context!!,
                    prayerIsha + " at " + timeFormat2.format(timeFormat.parse(prayerIshaTime)),
                    "Click here to view prayer times",
                    "Muslim Companion",
                    DisplayPrayerActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)

            // Prayer Athan
            if (ishaAthanSoundSetting) {
                val mp = MediaPlayer.create(context, R.raw.hafizmustafaozcan)
                mp.start()
            }
        }

        // Morning Azkar notification
        if (prayerMorning == "Morning" && morningAzkarNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent)

            // Create the notification to be shown
            val notification = createNotificationImageIntent(
                    context!!,
                    "Time for " + prayerMorning + " azkar",
                    "Click here to view morning azkar",
                    R.drawable.mosque,
                    R.drawable.athan_notification_big,
                    MainActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)
        }

        // Evening Azkar notification
        if (prayerEvening == "Evening" && eveningAzkarNotificationSetting) {

            // Enqueue the job
            //MyJobIntentService.enqueueWork(context, intent);

            // Create the notification to be shown
            val notification = createNotificationImageIntent(
                    context!!,
                    "Time for " + prayerEvening + " azkar",
                    "Click here to view evening azkar",
                    R.drawable.mosque,
                    R.drawable.athan_notification_big,
                    MainActivity())

            // Get the Notification manager service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000 + (Math.random() * 1000 + 1).toInt()
            notificationManager.notify(id.toInt(), notification)
        }
    }

    // Set All Alarms
    fun setAlarm(ctx: Context, prayer: String, prayerTime: String, prayerNotificationSetting: Boolean,
                 prayerSoundSetting: Boolean) {

        // Set AlarmManager
        val alarmManager    = ctx!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar        = Calendar.getInstance()
        val broadcastIntent = Intent(ctx, AlarmBroadcastReceiver::class.java)

        /*Log.i(TAG, "setAlarm - " + prayer + " at: " + prayerTime +  ",prayerSoundSetting: " +
                prayerSoundSetting + ",prayerNotificationSetting: " + prayerNotificationSetting)*/

        // Unique intent should be fired for every notification
        broadcastIntent.setAction(Random().nextInt(1000).toString() + "_" + prayer + "_action")

        // Register Fajr Alarm
        if (prayer == "Fajr" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerFajr", prayer)
            broadcastIntent.putExtra("prayerFajrTime", prayerTime)
            broadcastIntent.putExtra("fajrAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("fajrAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0

            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Register Sunrise Alarm
        if (prayer == "Sunrise" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerSunrise", prayer)
            broadcastIntent.putExtra("prayerSunriseTime", prayerTime)
            broadcastIntent.putExtra("sunriseAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("sunriseAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Register Dhuhr Alarm
        if (prayer == "Dhuhr" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerDhuhr", prayer)
            broadcastIntent.putExtra("prayerDhuhrTime", prayerTime)
            broadcastIntent.putExtra("dhuhrAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("dhuhrAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Register Asr Alarm
        if (prayer == "Asr" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerAsr", prayer)
            broadcastIntent.putExtra("prayerAsrTime", prayerTime)
            broadcastIntent.putExtra("asrAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("asrAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Register Maghrib Alarm
        if (prayer == "Maghrib" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerMaghrib", prayer)
            broadcastIntent.putExtra("prayerMaghribTime", prayerTime)
            broadcastIntent.putExtra("maghribAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("maghribAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Register Isha Alarm
        if (prayer == "Isha" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerIsha", prayer)
            broadcastIntent.putExtra("prayerIshaTime", prayerTime)
            broadcastIntent.putExtra("ishaAthanNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("ishaAthanSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            val athanPrayerTime    = ReadFromFile(ctx, "prayer_times_" + prayer + ".txt").split(":")
            val athanPrayerHour    = athanPrayerTime.get(0).removePrefix("0")
            val athanPrayerMinutes = athanPrayerTime.get(1).removePrefix("0")
            val athanPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, athanPrayerHour.toInt(), athanPrayerMinutes.toInt(), athanPrayerSeconds)
        }

        // Morning Azkar
        if (prayer == "Morning" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerMorning", prayer)
            broadcastIntent.putExtra("prayerMorningTime", prayerTime)
            broadcastIntent.putExtra("morningAzkarNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("morningAzkarSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            //val azkarPrayerHour  = 9
            val azkarPrayerHour    = prayerTime
            val azkarPrayerMinutes = 0
            val azkarPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, azkarPrayerHour.toInt(), azkarPrayerMinutes, azkarPrayerSeconds)
        }

        // Evening Azkar
        if (prayer == "Evening" && prayerNotificationSetting) {
            broadcastIntent.putExtra("prayerEvening", prayer)
            broadcastIntent.putExtra("prayerEveningTime", prayerTime)
            broadcastIntent.putExtra("eveningAzkarNotificationSetting", prayerNotificationSetting)
            broadcastIntent.putExtra("eveningAzkarSoundSetting", prayerSoundSetting)
            val pIntent = PendingIntent.getBroadcast(ctx, 0, broadcastIntent, 0)

            //val azkarPrayerHour    = 21
            val azkarPrayerHour    = prayerTime
            val azkarPrayerMinutes = 0
            val azkarPrayerSeconds = 0
            registerAlarm(alarmManager, pIntent, calendar, azkarPrayerHour.toInt(), azkarPrayerMinutes, azkarPrayerSeconds)
        }
    }

    private fun registerAlarm(alarmManager: AlarmManager, pIntent: PendingIntent, calendar: Calendar, hour: Int,
                              minutes: Int, seconds: Int) {

        cancelAlarm(alarmManager, pIntent)  // Remove old alarms if any

        // Now
        val calendarNow    = Calendar.getInstance()
        val currentHour    = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentMinutes = Calendar.getInstance().get(Calendar.MINUTE)
        val currentSeconds = Calendar.getInstance().get(Calendar.SECOND)
        calendarNow.set(Calendar.HOUR_OF_DAY, currentHour)
        calendarNow.set(Calendar.MINUTE, currentMinutes)
        calendarNow.set(Calendar.SECOND, currentSeconds)

        /*Log.i(TAG, "registerAlarm - " + hour + ":" + minutes + ":" + seconds + ",Now:" +
                currentHour + ":" + currentMinutes + ":" + currentSeconds)*/

        //calendar.timeInMillis = System.currentTimeMillis()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, seconds)

        // Only fire the notifications for future alarms
        if(calendar.after(calendarNow)){
            /*Log.i(TAG, "registerAlarm after current time: " + hour + ":" + minutes + ":" + seconds + ", Now: " +
                    currentHour + ":" + currentMinutes + ":" + currentSeconds)*/

            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent)
            } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent)
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
            }
        }
    }

    // Cancel any pending alarm
    private fun cancelAlarm(alarmManager: AlarmManager, pIntent: PendingIntent) {
        alarmManager.cancel(pIntent)
    }

    // Create NotificationChannel as required from Android 8.0 (Oreo)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(NotificationChannel(
                        DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ))
            }
        }
    }

    // Text Notification Intent
    private fun createNotificationTextIntent(context: Context, title: String, text: String,
                                             bigText: String, activityName: DisplayPrayerActivity): Notification {

        val intent        = Intent(context, activityName::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        // Big text
        val style = NotificationCompat.BigTextStyle()
        style.bigText(bigText)
        style.setBigContentTitle(title)
        style.setSummaryText(text)
        //val RGB = android.graphics.Color.rgb(255,255,255)  // color: white
        val RGB = android.graphics.Color.rgb(102,190,212)  // colorPrimaryDark: #66bed4

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.mosque_2)
                .setAutoCancel(true)  // Close notification after click
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setColor(RGB)
        return builder.build()
    }

    // Image Notification Intent
    private fun createNotificationImageIntent(context: Context, title: String, text: String, bigLargeIcon: Int,
             bigPictureResource: Int, activityName: MainActivity): Notification {

        val intent        = Intent(context, activityName::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        // Big picture
        val style = NotificationCompat.BigPictureStyle()
        style.setBigContentTitle(title)
        style.setSummaryText(text)
        style.bigPicture(BitmapFactory.decodeResource(context.resources, bigPictureResource))
        style.bigLargeIcon(BitmapFactory.decodeResource(context.resources, bigLargeIcon))

        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.mosque_2)
                .setAutoCancel(true)  // Close notification after click
                .setContentIntent(pendingIntent)
                .setStyle(style)
        return builder.build()
    }

    // Fetch each saved prayer time
    private fun ReadFromFile(ctx: Context, fileName: String) : String{
        var data = ""

        try {
            val path = ctx!!.getFilesDir().absolutePath.toString() + File.separator  + fileName
            var file = File(path);

            if(file.exists()){
                val inputStream: InputStream = file.inputStream()
                inputStream.bufferedReader().use {
                    data = it.readText()
                }
            }
            else{
                Log.i(TAG, "Error at: " + path)
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }

        return data
    }

}