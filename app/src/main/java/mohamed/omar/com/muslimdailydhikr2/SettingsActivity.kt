package mohamed.omar.com.muslimdailydhikr2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SettingsActivity : AppCompatActivity() {

    // GPS - Manual detection
    val KEY_PREF_AUTO_DETECT_SWITCH      = "auto_detect_switch"
    val KEY_PREF_MANUAL_ENTRY_CITY       = "manual_entry_city_edittext"
    val KEY_PREF_MANUAL_ENTRY_COUNTRY    = "manual_entry_country_edittext"
    val KEY_PREF_CALCULATION_METHOD_LIST = "calculation_method_list"

    // Prayer Notifications
    val KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH    = "enable_fajr_athan_notification_switch"
    val KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH = "enable_sunrise_athan_notification_switch"
    val KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH   = "enable_dhuhr_athan_notification_switch"
    val KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH     = "enable_asr_athan_notification_switch"
    val KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH = "enable_maghrib_athan_notification_switch"
    val KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH    = "enable_isha_athan_notification_switch"
    val KEY_MORNING_AZKAR_NOTIFICATION_SWITCH      = "enable_morning_azkar_notification_switch"
    val KEY_EVENING_AZKAR_NOTIFICATION_SWITCH      = "enable_evening_azkar_notification_switch"

    // Athans Sound Prayers
    val KEY_PREF_FAJR_ATHAN_SOUND_SWITCH    = "enable_fajr_athan_sound_switch"
    val KEY_PREF_SUNRISE_ATHAN_SOUND_SWITCH = "enable_sunrise_athan_sound_switch"
    val KEY_PREF_DHUHR_ATHAN_SOUND_SWITCH   = "enable_dhuhr_athan_sound_switch"
    val KEY_PREF_ASR_ATHAN_SOUND_SWITCH     = "enable_asr_athan_sound_switch"
    val KEY_PREF_MAGHRIB_ATHAN_SOUND_SWITCH = "enable_maghrib_athan_sound_switch"
    val KEY_PREF_ISHA_ATHAN_SOUND_SWITCH    = "enable_isha_athan_sound_switch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

}