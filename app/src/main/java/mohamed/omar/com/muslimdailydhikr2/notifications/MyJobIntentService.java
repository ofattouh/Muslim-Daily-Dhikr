package mohamed.omar.com.muslimdailydhikr2.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

public class MyJobIntentService extends JobIntentService {

	private static final String TAG = MyJobIntentService.class.getSimpleName();

    //private static final int JOB_ID = 17;  // Give the Job a Unique Id
    private static final int JOB_ID = (int)(Math.random() * 1000 + 1);

    AlarmBroadcastReceiver alarmBroadcastReceiver;
    static Context ctx;

	public static void enqueueWork(Context context, Intent intent) {
        ctx = context;
		enqueueWork(context, MyJobIntentService.class, JOB_ID, intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//Toast.makeText(this, "Prayer notification task: " + JOB_ID + " started", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onHandleWork(@NonNull Intent intent) {

		//Log.i(TAG, "onHandleWork, JOB_ID: " + JOB_ID + ":" + Thread.currentThread().getName());

        Boolean fajrAthanNotificationSetting = intent.getBooleanExtra("fajrAthanNotificationSetting", false);
        String fajrAthanNotificationTime     = intent.getStringExtra("fajrAthanNotificationTime");
        Boolean fajrAthanSoundSetting        = intent.getBooleanExtra("fajrAthanSoundSetting", false);

        Boolean sunriseAthanNotificationSetting = intent.getBooleanExtra("sunriseAthanNotificationSetting", false);
        String sunriseAthanNotificationTime     = intent.getStringExtra("sunriseAthanNotificationTime");
        Boolean sunriseAthanSoundSetting        = intent.getBooleanExtra("sunriseAthanSoundSetting", false);

        Boolean dhuhrAthanNotificationSetting = intent.getBooleanExtra("dhuhrAthanNotificationSetting", false);
        String dhuhrAthanNotificationTime     = intent.getStringExtra("dhuhrAthanNotificationTime");
        Boolean dhuhrAthanSoundSetting        = intent.getBooleanExtra("dhuhrAthanSoundSetting", false);

        Boolean asrAthanNotificationSetting = intent.getBooleanExtra("asrAthanNotificationSetting", false);
        String asrAthanNotificationTime     = intent.getStringExtra("asrAthanNotificationTime");
        Boolean asrAthanSoundSetting        = intent.getBooleanExtra("asrAthanSoundSetting", false);

        Boolean maghribAthanNotificationSetting = intent.getBooleanExtra("maghribAthanNotificationSetting", false);
        String maghribAthanNotificationTime     = intent.getStringExtra("maghribAthanNotificationTime");
        Boolean maghribAthanSoundSetting        = intent.getBooleanExtra("maghribAthanSoundSetting", false);

        Boolean ishaAthanNotificationSetting = intent.getBooleanExtra("ishaAthanNotificationSetting", false);
        String ishaAthanNotificationTime     = intent.getStringExtra("ishaAthanNotificationTime");
        Boolean ishaAthanSoundSetting        = intent.getBooleanExtra("ishaAthanSoundSetting", false);

        Boolean morningAzkarNotificationSetting = intent.getBooleanExtra("morningAzkarNotificationSetting", false);
        String morningAzkarNotificationTime     = intent.getStringExtra("morningAzkarNotificationTime");
        Boolean morningAzkarSoundSetting        = intent.getBooleanExtra("morningAzkarSoundSetting", false);

        Boolean eveningAzkarNotificationSetting = intent.getBooleanExtra("eveningAzkarNotificationSetting", false);
        String eveningAzkarNotificationTime     = intent.getStringExtra("eveningAzkarNotificationTime");
        Boolean eveningAzkarSoundSetting        = intent.getBooleanExtra("eveningAzkarSoundSetting", false);

        // Fajr Athan Notification
		if (fajrAthanNotificationSetting && fajrAthanNotificationTime != null){
           alarmBroadcastReceiver = new AlarmBroadcastReceiver();
           alarmBroadcastReceiver.setAlarm(ctx, "Fajr", fajrAthanNotificationTime, fajrAthanNotificationSetting, fajrAthanSoundSetting);
           //Log.i(TAG, "onHandleWork: " + "Fajr: " + fajrAthanNotificationTime + ": "  + fajrAthanNotificationSetting + ":" + fajrAthanSoundSetting);
        }

        // Sunrise Athan Notification
        if (sunriseAthanNotificationSetting && sunriseAthanNotificationTime != null){
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Sunrise", sunriseAthanNotificationTime, sunriseAthanNotificationSetting, sunriseAthanSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Sunrise: " + sunriseAthanNotificationTime + ": " + sunriseAthanNotificationSetting + ":" +  sunriseAthanSoundSetting);
        }

        // Dhuhr Athan Notification
        if (dhuhrAthanNotificationSetting && dhuhrAthanNotificationTime != null){
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Dhuhr", dhuhrAthanNotificationTime, dhuhrAthanNotificationSetting, dhuhrAthanSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Dhuhr: " + dhuhrAthanNotificationTime + ": " + dhuhrAthanNotificationSetting + ":" + dhuhrAthanSoundSetting);
        }

        // Asr Athan Notification
        if (asrAthanNotificationSetting && asrAthanNotificationTime != null){
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Asr", asrAthanNotificationTime, asrAthanNotificationSetting, asrAthanSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Asr: " + asrAthanNotificationTime + ": " + asrAthanNotificationSetting +  ":" + asrAthanSoundSetting);
        }

        // Maghrib Athan Notification
        if (maghribAthanNotificationSetting && maghribAthanNotificationTime != null){
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Maghrib", maghribAthanNotificationTime, maghribAthanNotificationSetting,  maghribAthanSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Maghrib: " + maghribAthanNotificationTime + ": " + maghribAthanNotificationSetting +  ":" + maghribAthanSoundSetting);
        }

        // Isha Athan Notification
        if (ishaAthanNotificationSetting && ishaAthanNotificationTime != null){
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx,  "Isha", ishaAthanNotificationTime, ishaAthanNotificationSetting, ishaAthanSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Isha: " + ishaAthanNotificationTime + ": " + ishaAthanNotificationSetting + ":" + ishaAthanSoundSetting);
        }

        // Morning notifications
        if (morningAzkarNotificationSetting && morningAzkarNotificationTime != null) {
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Morning", morningAzkarNotificationTime, morningAzkarNotificationSetting, morningAzkarSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Morning: " + ":" + morningAzkarNotificationTime +  morningAzkarNotificationSetting + ": " + morningAzkarSoundSetting);
        }

        // Evening notifications
        if (eveningAzkarNotificationSetting && eveningAzkarNotificationTime != null) {
            alarmBroadcastReceiver = new AlarmBroadcastReceiver();
            alarmBroadcastReceiver.setAlarm(ctx, "Evening", eveningAzkarNotificationTime, eveningAzkarNotificationSetting, eveningAzkarSoundSetting);
            //Log.i(TAG, "onHandleWork: " + " Evening: " + ":" + eveningAzkarNotificationTime + ": " + eveningAzkarNotificationSetting + ":"  + eveningAzkarSoundSetting);
        }
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//Toast.makeText(this, "Prayer notification task: " + JOB_ID + " finished", Toast.LENGTH_SHORT).show();
	}
}
