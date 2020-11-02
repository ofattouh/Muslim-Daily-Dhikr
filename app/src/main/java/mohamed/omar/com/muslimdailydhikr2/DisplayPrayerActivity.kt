package mohamed.omar.com.muslimdailydhikr2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*
import mohamed.omar.com.muslimdailydhikr2.network.ApiObject
import mohamed.omar.com.muslimdailydhikr2.network.ApiUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import org.json.JSONObject
import android.widget.TextView
import java.io.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import mohamed.omar.com.muslimdailydhikr2.notifications.MyJobIntentService
import android.widget.ImageView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class DisplayPrayerActivity : AppCompatActivity() {

    //var prayerTimingsList: ArrayList<String> = ArrayList()
    //var adapter: ApiAdapter? = null
    private val TAG = DisplayPrayerActivity::class.java.simpleName

    // Save the prayers times
    //var prayerTimesArr = ArrayList<String>(6)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                //intent.putExtra("EXTRA_MESSAGE", "Today")
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DisplayPrayerActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_compass -> {
                val intent = Intent(this, DisplayQiblaActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_prayer)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Preferences
        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        // GPS - Manual detection
        val sharedPref     = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val prefAutoDetect = sharedPref.getBoolean(SettingsActivity().KEY_PREF_AUTO_DETECT_SWITCH, true)
        val prefCity       = sharedPref.getString(SettingsActivity().KEY_PREF_MANUAL_ENTRY_CITY, "")
        val prefCountry    = sharedPref.getString(SettingsActivity().KEY_PREF_MANUAL_ENTRY_COUNTRY, "")
        val prefCalcMethod = sharedPref.getString(SettingsActivity().KEY_PREF_CALCULATION_METHOD_LIST, "1")
        //val geocoder     = Geocoder(this, Locale.getDefault())

        // Always fetch Qibla Direction using GPS
        val settings       = ReadFromFile("settings.txt").split(",")
        val latitude       = settings[0]
        val longitude      = settings[1]
        val qiblaDirection = settings[2]
        //val qiblaDirection = findQiblaDirection(latitude.toDouble(), longitude.toDouble())

        // Debug
        //Toast.makeText(this, "latitude : " + latitude + "\nplongitude: " + longitude, Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "prefAutoDetect: " + prefAutoDetect.toString() + "\nprefCity: " + prefCity + "\nprefCountry: " + prefCountry + "\nprefCalcMethod: " + prefCalcMethod , Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "fajrNotification: " + fajrNotification.toString() + "\nsunriseNotification: " + sunriseNotification.toString() + "\ndhuhrNotification: " + dhuhrNotification.toString() + "\nasrNotification: " + asrNotification.toString() + "\nMaghribNotification: " + MaghribNotification.toString() + "\nishaNotification: " + ishaNotification.toString(), Toast.LENGTH_LONG).show()

        // GPS - Auto Detection
        if(prefAutoDetect == true) {

            // val settings = ReadFromFile("settings.txt").split(",")

            // Auto detection- Required
            if (prefCalcMethod != "" && settings.size > 1 && settings.get(0) != null && settings.get(1) != null) {

               /* val addresses   = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
                val cityName    = addresses[0].getLocality()
                val stateName   = addresses[0].getAdminArea()
                val countryName = addresses[0].getCountryName()*/

                /* var latitude  = 30.044420   // Cairo
                 var longitude = 31.235712
                 val cityName  = "Cairo"*/

                /*  var latitude  = "43.653225"  // Toronto
                var longitude = "-79.383186"
                val cityName  = "Toronto"*/

                // Retrofit API
                ApiUtil.getServiceClass().getLatitudeLongtitude(latitude.toDouble(), longitude.toDouble(), prefCalcMethod.toInt()).enqueue(object : Callback<ApiObject> {

                    override fun onResponse(call: Call<ApiObject>, response: Response<ApiObject>) {
                        if (response.isSuccessful) {
                            val json                         = Gson()
                            //val apiCode                    = json.toJson(response.body()?.getApiCode())
                            //val apiStatus                  = json.toJson(response.body()?.getApiStatus())
                            val apiData                      = json.toJson(response.body()?.getApiData())
                            val dataObject                   = JSONObject(apiData)
                            val PrayerTimingsObject          = dataObject.getJSONObject("timings")
                            val PrayerDateObject             = dataObject.getJSONObject("date")
                            val PrayerGregorianDateObject    = PrayerDateObject.getJSONObject("gregorian")
                            val PrayerGregorianWeekdayObject = PrayerGregorianDateObject.getJSONObject("weekday")
                            val PrayerHijriObject            = PrayerDateObject.getJSONObject("hijri")
                            val PrayerHijriMonthObject       = PrayerHijriObject.getJSONObject("month")
                            val keysTimings                  = PrayerTimingsObject.keys()
                            val keysDate                     = PrayerDateObject.keys()
                            val keysHijri                    = PrayerHijriObject.keys()
                            val keysHijriMonth               = PrayerHijriMonthObject.keys()
                            val keysGregorianWeekday         = PrayerGregorianWeekdayObject.keys()
                            var HijriYear                    = ""
                            var localCity                    = ""

                            //val prayer_timings_list = findViewById<ListView>(R.id.prayer_timings_list)

                            // Prayer Timings
                            while (keysTimings.hasNext()) {
                                val key = keysTimings.next() as String
                                //Log.d(TAG, "Retrofit: " + key + ": " + PrayerTimingsObject.get(key).toString())
                                addPrayersTimingsData(key, PrayerTimingsObject.get(key).toString())
                            }

                            // Gregorian Weekday
                            while (keysGregorianWeekday.hasNext()) {
                                val key = keysGregorianWeekday.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerDateObject.get(key).toString())

                                if (key == "en") {
                                    //localCity += cityName + "\n" + PrayerGregorianWeekdayObject.get(key).toString()
                                    localCity += PrayerGregorianWeekdayObject.get(key).toString()
                                }
                            }

                            // Gregorian Date
                            while (keysDate.hasNext()) {
                                val key = keysDate.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerDateObject.get(key).toString())

                                if (key == "readable") {
                                    localCity += ", " + PrayerDateObject.get(key).toString()
                                    // prayer_times_label.text =  PrayerDateObject.get(key).toString() + "\n" + "Prayer Times for: " + cityName
                                }
                            }

                            // Hijri Date
                            while (keysHijri.hasNext()) {
                                val key = keysHijri.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerHijriObject.get(key).toString())

                                if (key == "day") {
                                    localCity += "\n" + PrayerHijriObject.get(key).toString()
                                }

                                if (key == "year") {
                                    HijriYear = PrayerHijriObject.get(key).toString()
                                    // localCity += " " + PrayerHijriObject.get(key).toString()
                                }
                            }

                            // Hijri Date Month
                            while (keysHijriMonth.hasNext()) {
                                val key = keysHijriMonth.next() as String
                                //Log.d("Retrofit month ",key + ": " + PrayerHijriMonthObject.get(key).toString())

                                if (key == "en") {
                                    localCity += " " + PrayerHijriMonthObject.get(key).toString() + " " + HijriYear
                                }
                            }

                            addPrayersTimingsData("local", localCity)

                            //adapter = ApiAdapter(prayerTimingsList, getApplicationContext())
                            //prayer_timings_list?.adapter = adapter
                        }
                    }

                    override fun onFailure(call: Call<ApiObject>, t: Throwable) {
                        addPrayersTimingsData("no_results_found_retrofit", "no results found")
                        Log.i(TAG, "Error Retrofit auto detect: " + t.message)
                    }
                })
            }
            else{
                addPrayersTimingsData("no_autodetect_results", "no results found")
                Toast.makeText(this, "no_autodetect_results", Toast.LENGTH_LONG).show()
            }
        }
        else{

            // Manual detection - Required
            if (prefCity != "" && prefCountry != "" && prefCalcMethod != "") {

                ApiUtil.getServiceClass().getCityCountry(prefCity, prefCountry, prefCalcMethod.toInt()).enqueue(object : Callback<ApiObject> {
                    // Retrofit API
                    override fun onResponse(call: Call<ApiObject>, response: Response<ApiObject>) {

                        if (response.isSuccessful) {

                            val json                         = Gson()
                            //val apiCode                    = json.toJson(response.body()?.getApiCode())
                            //val apiStatus                  = json.toJson(response.body()?.getApiStatus())
                            val apiData                      = json.toJson(response.body()?.getApiData())
                            val dataObject                   = JSONObject(apiData)
                            val PrayerTimingsObject          = dataObject.getJSONObject("timings")
                            val PrayerDateObject             = dataObject.getJSONObject("date")
                            val PrayerGregorianDateObject    = PrayerDateObject.getJSONObject("gregorian")
                            val PrayerGregorianWeekdayObject = PrayerGregorianDateObject.getJSONObject("weekday")
                            val PrayerHijriObject            = PrayerDateObject.getJSONObject("hijri")
                            val PrayerHijriMonthObject       = PrayerHijriObject.getJSONObject("month")
                            val keysTimings                  = PrayerTimingsObject.keys()
                            val keysDate                     = PrayerDateObject.keys()
                            val keysHijri                    = PrayerHijriObject.keys()
                            val keysHijriMonth               = PrayerHijriMonthObject.keys()
                            val keysGregorianWeekday         = PrayerGregorianWeekdayObject.keys()
                            var HijriYear                    = ""
                            var localCity                    = ""
                            //val prayer_timings_list = findViewById<ListView>(R.id.prayer_timings_list)

                            /*while (keys2.hasNext()) {
                                val key = keys2.next() as String
                                //Log.d("Retrofit ", key + ": " + PrayerDateObject.get(key).toString())

                                if (key == "readable") {
                                    addPrayersTimingsData("local", prefCity
                                            + "\n" + PrayerDateObject.get(key).toString()
                                            + "\n" + qiblaDirection)
                                    // prayer_times_label.text =  PrayerDateObject.get(key).toString() + "\n" + "Prayer Times for: " + cityName
                                }
                            }

                            while (keys.hasNext()) {
                                val key = keys.next() as String
                                //Log.d("Retrofit ", key + ": " + PrayerTimingsObject.get(key).toString())
                                addPrayersTimingsData(key, PrayerTimingsObject.get(key).toString())
                            }*/

                            // Prayer Timings
                            while (keysTimings.hasNext()) {
                                val key = keysTimings.next() as String
                                //Log.d(TAG, "Retrofit: " + key + ": " + PrayerTimingsObject.get(key).toString())
                                addPrayersTimingsData(key, PrayerTimingsObject.get(key).toString())
                            }

                            // Gregorian Weekday
                            while (keysGregorianWeekday.hasNext()) {
                                val key = keysGregorianWeekday.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerDateObject.get(key).toString())

                                if (key == "en") {
                                    //localCity += PrayerGregorianWeekdayObject.get(key).toString()
                                    localCity += PrayerGregorianWeekdayObject.get(key).toString()
                                }
                            }

                            // Gregorian Date
                            while (keysDate.hasNext()) {
                                val key = keysDate.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerDateObject.get(key).toString())

                                if (key == "readable") {
                                    localCity += ", " + PrayerDateObject.get(key).toString()
                                    // prayer_times_label.text =  PrayerDateObject.get(key).toString() + "\n" + "Prayer Times for: " + cityName
                                }
                            }

                            // Hijri Date
                            while (keysHijri.hasNext()) {
                                val key = keysHijri.next() as String
                                //Log.d("Retrofit ",key + ": " + PrayerHijriObject.get(key).toString())

                                if (key == "day") {
                                    localCity += "\n" + PrayerHijriObject.get(key).toString()
                                }

                                if (key == "year") {
                                    HijriYear = PrayerHijriObject.get(key).toString()
                                    // localCity += " " + PrayerHijriObject.get(key).toString()
                                }
                            }

                            // Hijri Date Month
                            while (keysHijriMonth.hasNext()) {
                                val key = keysHijriMonth.next() as String
                                //Log.d("Retrofit month ",key + ": " + PrayerHijriMonthObject.get(key).toString())

                                if (key == "en") {
                                    localCity += " " + PrayerHijriMonthObject.get(key).toString() + " " + HijriYear
                                }
                            }

                            addPrayersTimingsData("local", localCity)

                            //adapter = ApiAdapter(prayerTimingsList, getApplicationContext())
                            //prayer_timings_list?.adapter = adapter
                        }
                    }

                    override fun onFailure(call: Call<ApiObject>, t: Throwable) {
                        addPrayersTimingsData("no_results_found_retrofit", "no results found")
                        Log.i(TAG, "Error Retrofit manual detect: " + t.message)
                    }
                })
            }
            else{
                addPrayersTimingsData("no_manual_results", "no results found")
                //val prayer_timings_list = findViewById<ListView>(R.id.prayer_timings_list)
                //adapter = ApiAdapter(addPrayersTimingsData("no_results", "no results found"), getApplicationContext())
                //prayer_timings_list?.adapter = adapter
            }
        }
    }

    // Add prayer times data
    private fun addPrayersTimingsData(name: String, value: String) {

        val textViewLocal       = findViewById<TextView>(R.id.textViewLocal)
        val textViewFajr        = findViewById<TextView>(R.id.textViewFajr)
        val textViewFajrTime    = findViewById<TextView>(R.id.textViewFajrTime)
        val imageViewFajr       = findViewById<ImageView>(R.id.imageViewFajr)
        val buttonFajrTime      = findViewById<Button>(R.id.buttonFajrTime)
        val textViewSunrise     = findViewById<TextView>(R.id.textViewSunrise)
        val textViewSunriseTime = findViewById<TextView>(R.id.textViewSunriseTime)
        val imageViewSunrise    = findViewById<ImageView>(R.id.imageViewSunrise)
        val buttonSunriseTime   = findViewById<Button>(R.id.buttonSunriseTime)
        val textViewDhuhr       = findViewById<TextView>(R.id.textViewDhuhr)
        val textViewDhuhrTime   = findViewById<TextView>(R.id.textViewDhuhrTime)
        val imageViewDhuhr      = findViewById<ImageView>(R.id.imageViewDhuhr)
        val buttonDhuhrTime     = findViewById<Button>(R.id.buttonDhuhrTime)
        val textViewAsr         = findViewById<TextView>(R.id.textViewAsr)
        val textViewAsrTime     = findViewById<TextView>(R.id.textViewAsrTime)
        val imageViewAsr        = findViewById<ImageView>(R.id.imageViewAsr)
        val buttonAsrTime       = findViewById<Button>(R.id.buttonAsrTime)
        val textViewMaghrib     = findViewById<TextView>(R.id.textViewMaghrib)
        val textViewMaghribTime = findViewById<TextView>(R.id.textViewMaghribTime)
        val imageViewMaghrib    = findViewById<ImageView>(R.id.imageViewMaghrib)
        val buttonMaghribTime   = findViewById<Button>(R.id.buttonMaghribTime)
        val textViewIsha        = findViewById<TextView>(R.id.textViewIsha)
        val textViewIshaTime    = findViewById<TextView>(R.id.textViewIshaTime)
        val imageViewIsha       = findViewById<ImageView>(R.id.imageViewIsha)
        val buttonIshaTime      = findViewById<Button>(R.id.buttonIshaTime)

        // Prayer Athan notification times
        var fajrAthanNotificationTime    = ""
        var sunriseAthanNotificationTime = ""
        var dhuhrAthanNotificationTime   = ""
        var asrAthanNotificationTime     = ""
        var MaghribAthanNotificationTime = ""
        var ishaAthanNotificationTime    = ""

        // Prayer notifications settings
        val sharedPref                      = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
        var fajrAthanNotificationSetting    = sharedPref.getBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH, false)
        var sunriseAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH, false)
        var dhuhrAthanNotificationSetting   = sharedPref.getBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH, false)
        var asrAthanNotificationSetting     = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH, false)
        var MaghribAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH, false)
        var ishaAthanNotificationSetting    = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH, false)

        // Prayers Athans settings
        var fajrAthanSoundSetting    = sharedPref.getBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_SOUND_SWITCH, false)
        var sunriseAthanSoundSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_SOUND_SWITCH, false)
        var dhuhrAthanSoundSetting   = sharedPref.getBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_SOUND_SWITCH, false)
        var asrAthanSoundSetting     = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_SOUND_SWITCH, false)
        var maghribAthanSoundSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_SOUND_SWITCH, false)
        var ishaAthanSoundSetting    = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_SOUND_SWITCH, false)

        var timeFormat  = SimpleDateFormat("HH:mm", Locale.US)  // 24 Hrs (API)
        var timeFormat2 = SimpleDateFormat("h:mm a", Locale.US) // AM/PM

        // Local
        if (name == "local") {
            textViewLocal.text = value
        }

        // Fajr
        if (name == "Fajr") {
            textViewFajr.text         = name
            textViewFajrTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewFajr.setImageResource(R.drawable.cloudfilled)
            fajrAthanNotificationTime = value

            //textViewFajrTime.text = value
            //prayerTimesArr.add(0, value)

            //Log.i(TAG, "fajrAthanNotificationSetting: " + fajrAthanNotificationSetting.toString())

            //val mp = MediaPlayer.create(this, R.raw.ahmadalnafees)
            if (fajrAthanNotificationSetting){
                buttonFajrTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonFajrTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonFajrTime.setOnClickListener {
                if (fajrAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    fajrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonFajrTime.background = getDrawable(R.drawable.ic_notifications_off)
                    //Log.i(TAG, "fajrAthanNotificationSetting2: " + fajrAthanNotificationSetting.toString())
                    //mp.pause()
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    fajrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_FAJR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonFajrTime.background = getDrawable(R.drawable.ic_notifications_on)
                    //Log.i(TAG, "fajrAthanNotificationSetting2: " + fajrAthanNotificationSetting.toString())
                    //mp.start()
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(fajrAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createFajrPrayerNotifications(fajrAthanNotificationTime, fajrAthanNotificationSetting, fajrAthanSoundSetting)
        }

        // Sunrise
        if (name == "Sunrise") {
            textViewSunrise.text         = name
            textViewSunriseTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewSunrise.setImageResource(R.drawable.suncloud)
            sunriseAthanNotificationTime = value

            if (sunriseAthanNotificationSetting){
                buttonSunriseTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonSunriseTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonSunriseTime.setOnClickListener {
                if (sunriseAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    sunriseAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonSunriseTime.background = getDrawable(R.drawable.ic_notifications_off)
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    sunriseAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_SUNRISE_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonSunriseTime.background = getDrawable(R.drawable.ic_notifications_on)
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(sunriseAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createSunrisePrayerNotifications(sunriseAthanNotificationTime, sunriseAthanNotificationSetting, sunriseAthanSoundSetting)
        }

        // Dhuhr
        if (name == "Dhuhr") {
            textViewDhuhr.text         = name
            textViewDhuhrTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewDhuhr.setImageResource(R.drawable.sunfilled)
            dhuhrAthanNotificationTime = value

            if (dhuhrAthanNotificationSetting){
                buttonDhuhrTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonDhuhrTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonDhuhrTime.setOnClickListener {
                if (dhuhrAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    dhuhrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonDhuhrTime.background = getDrawable(R.drawable.ic_notifications_off)
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    dhuhrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_DHUHR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonDhuhrTime.background = getDrawable(R.drawable.ic_notifications_on)
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(dhuhrAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createDhuhrPrayerNotifications(dhuhrAthanNotificationTime, dhuhrAthanNotificationSetting, dhuhrAthanSoundSetting)
        }

        // Asr
        if (name == "Asr") {
            textViewAsr.text         = name
            textViewAsrTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewAsr.setImageResource(R.drawable.sun)
            asrAthanNotificationTime = value

            if (asrAthanNotificationSetting){
                buttonAsrTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonAsrTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonAsrTime.setOnClickListener {
                if (asrAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    asrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonAsrTime.background = getDrawable(R.drawable.ic_notifications_off)
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    asrAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ASR_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonAsrTime.background = getDrawable(R.drawable.ic_notifications_on)
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(asrAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createAsrPrayerNotifications(asrAthanNotificationTime, asrAthanNotificationSetting, asrAthanSoundSetting)
        }

        // Maghrib
        if (name == "Maghrib") {
            textViewMaghrib.text         = name
            textViewMaghribTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewMaghrib.setImageResource(R.drawable.suncloud)
            MaghribAthanNotificationTime = value

            if (MaghribAthanNotificationSetting){
                buttonMaghribTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonMaghribTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonMaghribTime.setOnClickListener {
                if (MaghribAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    MaghribAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonMaghribTime.background = getDrawable(R.drawable.ic_notifications_off)
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    MaghribAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_MAGHRIB_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonMaghribTime.background = getDrawable(R.drawable.ic_notifications_on)
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(MaghribAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createMaghribPrayerNotifications(MaghribAthanNotificationTime, MaghribAthanNotificationSetting, maghribAthanSoundSetting)
        }

        // Isha
        if (name == "Isha") {
            textViewIsha.text         = name
            textViewIshaTime.text     = timeFormat2.format(timeFormat.parse(value))
            imageViewIsha.setImageResource(R.drawable.moon)
            ishaAthanNotificationTime = value

            if (ishaAthanNotificationSetting){
                buttonIshaTime.background = getDrawable(R.drawable.ic_notifications_on)
            }
            else{
                buttonIshaTime.background = getDrawable(R.drawable.ic_notifications_off)
            }

            buttonIshaTime.setOnClickListener {
                if (ishaAthanNotificationSetting) {
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH, false).apply()
                    ishaAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonIshaTime.background = getDrawable(R.drawable.ic_notifications_off)
                }
                else{
                    sharedPref.edit().putBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH, true).apply()
                    ishaAthanNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_PREF_ISHA_ATHAN_NOTIFICATION_SWITCH, false)
                    buttonIshaTime.background = getDrawable(R.drawable.ic_notifications_on)
                }
            }

            // Start Athan notifications Job Intent Service and save the prayer times
            writeToFileEachTime(ishaAthanNotificationTime, "prayer_times_" + name + ".txt", applicationContext)
            createIshaPrayerNotifications(ishaAthanNotificationTime, ishaAthanNotificationSetting, ishaAthanSoundSetting)
        }

        // Manual Detection error
        if (name == "no_manual_results") {
            textViewLocal.text = resources.getString(R.string.error_manual_detect_prayers)
        }

        // GPS Detection error
        if (name == "no_autodetect_results"){
            textViewLocal.text = resources.getString(R.string.error_auto_detect_prayers)
        }

        // No internet error
        if (name == "no_results_found_retrofit"){
            textViewLocal.text = resources.getString(R.string.error_no_results_found_retrofit)
        }
    }

    // Create Fajr prayer notifications
    private fun createFajrPrayerNotifications(fajrAthanNotificationTime: String, fajrAthanNotificationSetting: Boolean,
                                              fajrAthanSoundSetting: Boolean) {
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("fajrAthanNotificationTime", fajrAthanNotificationTime)
        i.putExtra("fajrAthanNotificationSetting", fajrAthanNotificationSetting)
        i.putExtra("fajrAthanSoundSetting", fajrAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        Log.i(TAG, "createFajrPrayerNotifications: " + fajrAthanNotificationTime + "," + fajrAthanSoundSetting)
    }

    // Create Sunrise prayer notifications
    private fun createSunrisePrayerNotifications(sunriseAthanNotificationTime: String, sunriseAthanNotificationSetting:  Boolean,
                                                 sunriseAthanSoundSetting: Boolean) {
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("sunriseAthanNotificationTime", sunriseAthanNotificationTime)
        i.putExtra("sunriseAthanNotificationSetting", sunriseAthanNotificationSetting)
        i.putExtra("sunriseAthanSoundSetting", sunriseAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        //Log.i(TAG, "create Prayer Notifications Sunrise - ReadFromFile: " + ReadFromFile("prayer_times_Sunrise.txt"))
    }

    // Create Dhuhr prayer notifications
    private fun createDhuhrPrayerNotifications(dhuhrAthanNotificationTime: String, dhuhrAthanNotificationSetting:  Boolean,
                                               dhuhrAthanSoundSetting: Boolean) {
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("dhuhrAthanNotificationTime", dhuhrAthanNotificationTime)
        i.putExtra("dhuhrAthanNotificationSetting", dhuhrAthanNotificationSetting)
        i.putExtra("dhuhrAthanSoundSetting", dhuhrAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        //Log.i(TAG, "create Prayer Notifications Dhuhr - ReadFromFile: " + ReadFromFile("prayer_times_Dhuhr.txt"))
    }

    // Create Asr prayer notifications
    private fun createAsrPrayerNotifications(asrAthanNotificationTime: String, asrAthanNotificationSetting:  Boolean,
                                             asrAthanSoundSetting: Boolean) {
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("asrAthanNotificationTime", asrAthanNotificationTime)
        i.putExtra("asrAthanNotificationSetting", asrAthanNotificationSetting)
        i.putExtra("asrAthanSoundSetting", asrAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        //Log.i(TAG, "create Prayer Notifications Asr - ReadFromFile: " + ReadFromFile("prayer_times_Asr.txt"))
    }

    // Create Maghrib prayer notifications
    private fun createMaghribPrayerNotifications(maghribAthanNotificationTime: String, maghribAthanNotificationSetting:  Boolean,
                                                 maghribAthanSoundSetting: Boolean) {
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("maghribAthanNotificationTime", maghribAthanNotificationTime)
        i.putExtra("maghribAthanNotificationSetting", maghribAthanNotificationSetting)
        i.putExtra("maghribAthanSoundSetting", maghribAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        //Log.i(TAG, "create Prayer Notifications Maghrib - ReadFromFile: " + ReadFromFile("prayer_times_Maghrib.txt"))
    }

    // Create Isha prayer notifications
    private fun createIshaPrayerNotifications(ishaAthanNotificationTime: String, ishaAthanNotificationSetting: Boolean,
                                              ishaAthanSoundSetting: Boolean){
        val i = Intent(this, MyJobIntentService::class.java)
        i.putExtra("ishaAthanNotificationTime", ishaAthanNotificationTime)
        i.putExtra("ishaAthanNotificationSetting", ishaAthanNotificationSetting)
        i.putExtra("ishaAthanSoundSetting", ishaAthanSoundSetting)
        MyJobIntentService.enqueueWork(this, i)
        //Log.i(TAG, "create Prayer Notifications Isha - ReadFromFile: " + ReadFromFile("prayer_times_Isha.txt"))
    }

    fun ReadFromFile(fileName: String) : String{

        var data = ""

        try {

            /* inputStream = getApplicationContext()?.getAssets()?.open(fileName)
            val fh      = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

            while ( fh.readLine() != null ) {
                line = fh.readLine()
                Log.d("Location provider pr ", line)
                data.add(line)
            }

            fh.close()*/

            val path = applicationContext.filesDir.absolutePath.toString() + File.separator  + fileName
            var file = File(path)

            if(file.exists()){
                val inputStream: InputStream = file.inputStream()
                inputStream.bufferedReader().use {
                    data = it.readText()
                }
            }
            else{
                Log.d("Location provider err ", path)
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }

        return data
    }

    // Write each prayer time to file
    fun writeToFileEachTime(prayerTime: String, fileName: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(prayerTime)
            outputStreamWriter.close()
        }
        catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    // Write all prayers times to file
    /*fun writeToFileAllTimes(data: ArrayList<String>, fileName: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            for (prayertime in data) {
                outputStreamWriter.write(prayertime + ",")
                //Log.i(TAG, "create Prayer Notifications All writeToFile:" + data.indexOf(prayertime) + ", " + prayertime)
            }
            outputStreamWriter.close()
        }
        catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }*/

    // Find Qibla Direction
    /*private fun findQiblaDirection(latitude: Double, longitude: Double) : String{
        val userLocation = Location(GPS_PROVIDER)
        userLocation.setLongitude(longitude)
        userLocation.setLatitude(latitude)

        val destinationLocation = Location(GPS_PROVIDER)
        destinationLocation.setLatitude(21.389082)  // kaaba latitude setting
        destinationLocation.setLongitude(39.857910) // kaaba longitude setting

        var bearing = userLocation.bearingTo(destinationLocation)
        if (bearing < 0) {
            bearing = bearing + 360;
            //bearTo = -100 + 360  = 260;
        }

        val df             = DecimalFormat("#.##") // Format bearing
        df.roundingMode    = RoundingMode.CEILING
        val bearingCeiling = df.format(bearing)
        var bearingMsg     = "Qibla: " + bearingCeiling.toString() + "° N"

        // Debug
        //Toast.makeText(this, "latitude: " + latitude + "\nlongitude: " + longitude, Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "Qibla Direction: " + bearingMsg, Toast.LENGTH_LONG).show()
        return bearingMsg
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // If option menu item is Settings, return true.
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
