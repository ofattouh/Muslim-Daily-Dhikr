package mohamed.omar.com.muslimdailydhikr2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_home.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Integer.parseInt

class DisplaySurahActivity : AppCompatActivity() {

    var surahList: ArrayList<String> = ArrayList()
    var adapter: SurahListAdapter?   = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
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
        setContentView(R.layout.activity_display_surah)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val list_surah = findViewById<ListView>(R.id.list_surah)
        val bundle     = intent.extras
        var fileName   = ""

        if (bundle != null) {

            if (parseInt(bundle.getString("surah_list_position")) == 0) {
                fileName = "Quran/Al-Fatihah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 1) {
                fileName = "Quran/Al-Baqarah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 2) {
                fileName = "Quran/Ali-3imran.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 3) {
                fileName = "Quran/An-Nisa.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 4) {
                fileName = "Quran/Al-Maidah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 5) {
                fileName = "Quran/Al-An3am.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 6) {
                fileName = "Quran/Al-A3raf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 7) {
                fileName = "Quran/Al-Anfal.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 8) {
                fileName = "Quran/At-Tawbah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 9) {
                fileName = "Quran/Yunus.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 10) {
                fileName = "Quran/Hud.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 11) {
                fileName = "Quran/Yusuf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 12) {
                fileName = "Quran/Ar-Ra3d.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 13) {
                fileName = "Quran/Ibrahim.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 14) {
                fileName = "Quran/Al-Hijr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 15) {
                fileName = "Quran/An-Nahl.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 16) {
                fileName = "Quran/Al-Isra.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 17) {
                fileName = "Quran/Al-Kahf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 18) {
                fileName = "Quran/Maryam.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 19) {
                fileName = "Quran/Ta-Ha.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 20) {
                fileName = "Quran/Al-Anbiya.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 21) {
                fileName = "Quran/Al-Hajj.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 22) {
                fileName = "Quran/Al-Muminun.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 23) {
                fileName = "Quran/An-Nur.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 24) {
                fileName = "Quran/Al-Furqan.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 25) {
                fileName = "Quran/Ash-Shu3ara.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 26) {
                fileName = "Quran/An-Naml.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 27) {
                fileName = "Quran/Al-Qasas.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 28) {
                fileName = "Quran/Al-3ankabut.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 29) {
                fileName = "Quran/Ar-Rum.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 30) {
                fileName = "Quran/Luqman.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 31) {
                fileName = "Quran/As-Sajdah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 32) {
                fileName = "Quran/Al-Ahzab.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 33) {
                fileName = "Quran/Saba.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 34) {
                fileName = "Quran/Fatir.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 35) {
                fileName = "Quran/Ya-Seen.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 36) {
                fileName = "Quran/As-Saffat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 37) {
                fileName = "Quran/Saad.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 38) {
                fileName = "Quran/Az-Zumar.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 39) {
                fileName = "Quran/Ghafir.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 40) {
                fileName = "Quran/Fussilat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 41) {
                fileName = "Quran/Ash-Shura.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 42) {
                fileName = "Quran/Az-Zukhruf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 43) {
                fileName = "Quran/Ad-Dukhan.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 44) {
                fileName = "Quran/Al-Jathiyah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 45) {
                fileName = "Quran/Al-Ahqaf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 46) {
                fileName = "Quran/Muhammad.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 47) {
                fileName = "Quran/Al-Fath.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 48) {
                fileName = "Quran/Al-Hujurat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 49) {
                fileName = "Quran/Qaf.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 50) {
                fileName = "Quran/Adh-Dhariyat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 51) {
                fileName = "Quran/At-Tur.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 52) {
                fileName = "Quran/An-Najm.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 53) {
                fileName = "Quran/Al-Qamar.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 54) {
                fileName = "Quran/Ar-Rahman.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 55) {
                fileName = "Quran/Al-Waqi3ah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 56) {
                fileName = "Quran/Al-Hadid.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 57) {
                fileName = "Quran/Al-Mujadilah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 58) {
                fileName = "Quran/Al-Hashr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 59) {
                fileName = "Quran/Al-Mumtahinah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 60) {
                fileName = "Quran/As-Saff.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 61) {
                fileName = "Quran/Al-Jumu3ah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 62) {
                fileName = "Quran/Al-Munafiqun.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 63) {
                fileName = "Quran/At-Taghabun.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 64) {
                fileName = "Quran/At-Talaq.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 65) {
                fileName = "Quran/At-Tahrim.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 66) {
                fileName = "Quran/Al-Mulk.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 67) {
                fileName = "Quran/Al-Qalam.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 68) {
                fileName = "Quran/Al-Haqqah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 69) {
                fileName = "Quran/Al-Ma3arij.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 70) {
                fileName = "Quran/Nuh.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 71) {
                fileName = "Quran/Al-Jinn.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 72) {
                fileName = "Quran/Al-Muzzammil.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 73) {
                fileName = "Quran/al-Muddathir.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 74) {
                fileName = "Quran/Al-Qiyamah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 75) {
                fileName = "Quran/Al-Insan.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 76) {
                fileName = "Quran/Al-Mursalat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 77) {
                fileName = "Quran/An-Naba.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 78) {
                fileName = "Quran/An-Nazi3at.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 79) {
                fileName = "Quran/3abasa.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 80) {
                fileName = "Quran/At-Takwir.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 81) {
                fileName = "Quran/Al-Infitar.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 82) {
                fileName = "Quran/Al-Mutaffifin.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 83) {
                fileName = "Quran/Al-Inshiqaq.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 84) {
                fileName = "Quran/Al-Buruj.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 85) {
                fileName = "Quran/At-Tariq.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 86) {
                fileName = "Quran/Al-A3la.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 87) {
                fileName = "Quran/Al-Ghashiyah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 88) {
                fileName = "Quran/Al-Fajr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 89) {
                fileName = "Quran/Al-Balad.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 90) {
                fileName = "Quran/Ash-Shams.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 91) {
                fileName = "Quran/Al-Layl.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 92) {
                fileName = "Quran/Ad-Duha.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 93) {
                fileName = "Quran/Ash-Sharh.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 94) {
                fileName = "Quran/At-Tin.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 95) {
                fileName = "Quran/Al-3alaq.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 96) {
                fileName = "Quran/Al-Qadr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 97) {
                fileName = "Quran/Al-Bayyinah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 98) {
                fileName = "Quran/Az-Zalzalah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 99) {
                fileName = "Quran/Al-3adiyat.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 100) {
                fileName = "Quran/Al-Qari3ah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 101) {
                fileName = "Quran/At-Takathur.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 102) {
                fileName = "Quran/Al-3asr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 103) {
                fileName = "Quran/Al-Humazah.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 104) {
                fileName = "Quran/Al-Fil.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 105) {
                fileName = "Quran/Quraysh.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 106) {
                fileName = "Quran/Al-Ma3un.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 107) {
                fileName = "Quran/Al-Kawthar.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 108) {
                fileName = "Quran/Al-Kafirun.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 109) {
                fileName = "Quran/An-Nasr.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 110) {
                fileName = "Quran/Al-Masad.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 111) {
                fileName = "Quran/Al-Ikhlas.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 112) {
                fileName = "Quran/Al-Falaq.txt"
            }
            else if (parseInt(bundle.getString("surah_list_position")) == 113) {
                fileName = "Quran/An-Nas.txt"
            }

        }

        addSurahData(applicationContext, fileName)
        adapter = SurahListAdapter(surahList, this)
        list_surah?.adapter = adapter

    }

    // URL: https://www.quranful.com/
    private fun addSurahData(context: Context, fileName: String) {
        //surahList.add("Al-Faatihah - سُورة الْفاتِحَة")

        var inputStream: InputStream? = null
        var line : String             = ""
        var tempLine : String         = ""
        var lineNumber                = 0

        try {
            inputStream = context?.getAssets()?.open(fileName)
            val fh      = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

            while (fh.readLine() != null) {
                line = fh.readLine()

                //Log.d("line: ", line)

                if (lineNumber %2 == 0) {
                    tempLine = line
                }
                else{
                    line = tempLine + "\n\n" + line
                    surahList.add(line)
                    tempLine = ""
                }

                lineNumber++
            }

            fh.close()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }

        surahList.add("\n")
    }

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
    fun ReadTextFromFile(fileName: String, FragmentNumber: String) : String{

        var inputStream: InputStream? = null
            var lineNumber :Int = 0
            var line : String = ""

            var fileLines = ArrayList<String>()
            fileLines.add("dummy_firstline")

            var fileName        = arguments?.getString(ARG_FILE_NAME)
            var fragmentNumber  = arguments?.getInt(ARG_SECTION_NUMBER)
            val lines           = StringBuilder()

            try {
                inputStream = context?.getAssets()?.open(fileName.toString()!!)
                val fh = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

                while (fh.readLine() != null) {
                    line = fh.readLine()
                    lines.append(line)
                    fileLines.add(line)
                }

                fh.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

    }

    */

}
