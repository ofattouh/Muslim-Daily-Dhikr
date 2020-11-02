package mohamed.omar.com.muslimdailydhikr2

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import java.io.*
import mohamed.omar.com.muslimdailydhikr2.network.LocationProvider
import mohamed.omar.com.muslimdailydhikr2.notifications.AlarmBroadcastReceiver
import mohamed.omar.com.muslimdailydhikr2.notifications.MyJobIntentService
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
// class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private val TAG = MainActivity::class.java.simpleName
    var todayDataList: ArrayList<Any> = ArrayList()
    var adapter: HomeListAdapter?     = null
    var alarmBroadcastReceiver: AlarmBroadcastReceiver = AlarmBroadcastReceiver()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText("Home screen2")
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_MESSAGE", "Today")
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DisplayPrayerActivity::class.java)
                intent.putExtra("EXTRA_MESSAGE", "Prayers")
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

        setContentView(R.layout.activity_home)

        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        val sharedPref     = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val prefAutoDetect = sharedPref.getBoolean(SettingsActivity().KEY_PREF_AUTO_DETECT_SWITCH, false)

        var morningAzkarNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_MORNING_AZKAR_NOTIFICATION_SWITCH, true)
        var eveningAzkarNotificationSetting = sharedPref.getBoolean(SettingsActivity().KEY_EVENING_AZKAR_NOTIFICATION_SWITCH, true)


        // Debug
        //Toast.makeText(this, prefAutoDetect.toString(), Toast.LENGTH_SHORT).show()

        // if(prefAutoDetect === true) {
            // Always subscribe to fetch Qibla Direction using GPS
            //LocationProvider.subscribe(locationListener, applicationContext)
            LocationProvider.subscribe(locationListener, this)
        //}

        val list_dua   = findViewById<ListView>(R.id.list_home)
        //val home_image = findViewById<ImageView>(R.id.image_view_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addTodayData()
        adapter = HomeListAdapter(todayDataList, this)
        list_dua?.adapter = adapter

        // Create Azkar Alarms
        registerAlarm(morningAzkarNotificationSetting, eveningAzkarNotificationSetting)

        //val pendingIntent = PendingIntent.getBroadcast(this, 100, intent, FLAG_CANCEL_CURRENT)
        //val pIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0)

        /*  fab.setOnClickListener { view ->
           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show()
       }*/

        /* val toggle = ActionBarDrawerToggle(
                 this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
         drawer_layout.addDrawerListener(toggle)
         toggle.syncState()

         nav_view.setNavigationItemSelectedListener(this)*/

        /*list_lang.setOnItemClickListener( { parent, view, position, id ->
            val intent = Intent(this, DuaListViewItemActivity::class.java)
            startActivity(intent)
            Log.d("list_lang: ", "value");
        })*/

        /* list_lang.setOnItemClickListener(object : OnItemClickListener {
             override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                 //val intent = Intent(this, DuaListViewItemActivity::class.java)
                 //startActivity(intent)
                 Log.i("Hello!", "Clicked! YAY!")
             }
         })*/


        /*    val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)*/


        /*list_lang.setOnItemClickListener( { adapter, v, position, id ->
            val value = adapter.getItemAtPosition(position) as String
            val intent = Intent(this, DuaListViewItemActivity::class.java).apply {
                putExtra("EXTRA_MESSAGE", value)
            }
            startActivity(intent)
            Log.d("list_lang: ", value);
        })*/

    }

    private fun addTodayData() {
        addHaramMedinaData()
        //addQuranData()
        addDhikrData()
    }

    private fun addHaramMedinaData(){
        todayDataList.add("البث الحي من الحرم الشريف")
        todayDataList.add("البث الحي من المسجد النبوي")
    }

    private fun addQuranData(){
        todayDataList.add("quran_image")
        todayDataList.add("إذاعة القرآن الكريم")
    }

    private fun addDhikrData() {
        todayDataList.add("azkar_image")
        todayDataList.add("القرآن الكريم (نسخة كاملة)")
        todayDataList.add("اذكار الصباح")
        todayDataList.add("اذكار المساء")
        todayDataList.add("ادعية من القرآن الكريم")
        todayDataList.add("ادعية وابتهالات")
        todayDataList.add("أسماء الله الحسنى")
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            p0?.let {
                //LocationProvider.unsubscribe(this, applicationContext)
                //Log.d("Location provider", p0.latitude.toString() + ", " + p0.longitude.toString())

                var data = p0.latitude.toString() + "," +
                           p0.longitude.toString() + "," +
                           findQiblaDirection(p0.latitude, p0.longitude) + "," +
                           findCity(p0.latitude, p0.longitude)

                writeToFile(data, "settings.txt", applicationContext)
            }
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
        override fun onProviderEnabled(p0: String?) {}
        override fun onProviderDisabled(p0: String?) {}
    }

    // Find Qibla Direction
    private fun findQiblaDirection(latitude: Double, longitude: Double) : String{
        val userLocation = Location(LocationManager.GPS_PROVIDER)
        userLocation.setLongitude(longitude)
        userLocation.setLatitude(latitude)

        val destinationLocation = Location(LocationManager.GPS_PROVIDER)
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
        var bearingMsg     = bearingCeiling.toString()

        // Debug
        //Toast.makeText(this, "latitude: " + latitude + "\nlongitude: " + longitude, Toast.LENGTH_LONG).show()
        //Toast.makeText(this, "Qibla Direction: " + bearingMsg, Toast.LENGTH_LONG).show()
        return bearingMsg
    }

    // Find City/Locality
    private fun findCity(latitude: Double, longitude: Double) : String {
        val geocoder  = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val cityName  = addresses[0].getLocality()
        return cityName;
    }

    // Create Azkar notifications
    private fun registerAlarm(morningAzkarNotificationSetting: Boolean, eveningAzkarNotificationSetting: Boolean){
        val broadcastIntent = Intent(this, MyJobIntentService::class.java)
        broadcastIntent.putExtra("morningAzkarNotificationTime", "9")  // 9 AM
        broadcastIntent.putExtra("eveningAzkarNotificationTime", "21") // 9 PM
        broadcastIntent.putExtra("morningAzkarNotificationSetting", morningAzkarNotificationSetting)
        broadcastIntent.putExtra("eveningAzkarNotificationSetting", eveningAzkarNotificationSetting)
        broadcastIntent.putExtra("morningAzkarSoundSetting", false)
        broadcastIntent.putExtra("eveningAzkarSoundSetting", false)
        MyJobIntentService.enqueueWork(this, broadcastIntent)
        //Log.i(TAG, "registerAlarm: " + morningAzkarNotificationSetting + ", " + eveningAzkarNotificationSetting)
    }

    private fun writeToFile(data: String, fileName: String, context: Context) {

        try {

            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()

            /* val path = context.getFilesDir().absolutePath.toString() + File.separator  + fileName
             var file = File(path);

             if(file.exists()){
                 val inputStream: InputStream = file.inputStream()
                 val inputString = inputStream.bufferedReader().use { it.readText() }
                 //Log.d("Location provider wr ", inputString)
             }
             else{
                 Log.d("Location provider err", data)
             }*/
        }
        catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }


    /*override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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

    /*
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {
                /* val intent = Intent(this, DisplayMessageActivity::class.java).apply {
                     putExtra(EXTRA_MESSAGE, message)
                 }
                 startActivity(intent)*/

            }
        }

        /*drawer_layout.closeDrawer(GravityCompat.START)*/
        return true
    }

    */

}
