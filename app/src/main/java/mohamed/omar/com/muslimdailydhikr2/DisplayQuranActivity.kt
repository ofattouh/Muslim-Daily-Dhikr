package mohamed.omar.com.muslimdailydhikr2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_home.*

class DisplayQuranActivity : AppCompatActivity() {

    var quranList: ArrayList<String> = ArrayList()
    var adapter: QuranListAdapter?   = null

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
        setContentView(R.layout.activity_display_quran)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val list_quran = findViewById<ListView>(R.id.list_quran)

        addQuranData()
        adapter = QuranListAdapter(quranList, this)
        list_quran?.adapter = adapter

    }

    // https://www.quranful.com/
    private fun addQuranData() {
        quranList.add("1. Al-Fatihah - سُورة الْفاتِحَة")
        quranList.add("2. Al-Baqarah - سُورة الْبَقَرة")
        quranList.add("3. Ali-3imran - سُورة آلِ عمران")
        quranList.add("4. An-Nisa - سُورة النساء")
        quranList.add("5. Al-Maidah - سُورة الما ئِدة")
        quranList.add("6. Al-An3am - سُورة الأ نعام")
        quranList.add("7. Al-A3raf - سُورة الأَعراف")
        quranList.add("8. Al-Anfal - سُورة ُالْأ نفال")
        quranList.add("9. At-Tawbah - سُورة التوبة")
        quranList.add("10. Yunus - سُورةُ يونس")
        quranList.add("11. Hud - سُورةُ هود")
        quranList.add("12. Yusuf - سُورة يوسف")
        quranList.add("13. Ar-Ra3d - سُورةُ الرعد")
        quranList.add("14. Ibrahim - سُورة إبراهيم")
        quranList.add("15. Al-Hijr - سُورة ُالحجر")
        quranList.add("16. An-Nahl - سُورة ُالنحل")
        quranList.add("17. Al-Isra - سُورةُ الإِ سْراء")
        quranList.add("18. Al-Kahf - سُورةُ الكهف")
        quranList.add("19. Maryam - سُورة ُمريم")
        quranList.add("20. Ta-Ha - سُورة ُطه")
        quranList.add("21. Al-Anbiya - سُورةُ الأنبياء")
        quranList.add("22. Al-Hajj - سُورة الحج")
        quranList.add("23. Al-Muminun - سُورةُ المؤمنون")
        quranList.add("24. An-Nur - سُورة النور")
        quranList.add("25. Al-Furqan - سُورةُ الفرقان")
        quranList.add("26. Ash-Shu3ara - سُورة الشعراء")
        quranList.add("27. An-Naml - سُورة النمل")
        quranList.add("28. Al-Qasas - سُورةُ القصص")
        quranList.add("29. Al-3ankabut - سُورة العنكبوت")
        quranList.add("30. Ar-Rum - سُورةُ الروم")
        quranList.add("31. Luqman - سُورة لقمان")
        quranList.add("32. As-Sajdah - سُورةُ السجدة")
        quranList.add("33. Al-Ahzab - سُورة الأحزاب")
        quranList.add("34. Saba - سُورةُ سبأ")
        quranList.add("35. Fatir - سُورة فاطر")
        quranList.add("36. Ya Seen - سُورة يس")
        quranList.add("37. As-Saffat - سُورةُ الصافات")
        quranList.add("38. Saad - سُورةُ ص")
        quranList.add("39. Az-Zumar - سُورةُ الزمر")
        quranList.add("40. Ghafir - سُورةُ غافر")
        quranList.add("41. Fussilat - سُورةُ فصلت")
        quranList.add("42. Ash-Shura - سُورةُ الشورى")
        quranList.add("43. Az-Zukhruf - سُورةُ الزخرف")
        quranList.add("44. Ad-Dukhan - سُورةُ الدخان")
        quranList.add("45. Al-Jathiyah - سُورةُ الجاثية")
        quranList.add("46. Al-Ahqaf - سُورة الأحقاف")
        quranList.add("47. Muhammad - سُورةُ محمد")
        quranList.add("48. Al-Fath - سُورة الفتح")
        quranList.add("49. Al-Hujurat - سُورة الحجرات")
        quranList.add("50. Qaf - سُورةُ ق")
        quranList.add("51. Adh-Dhariyat - سُورةُ الذاريات")
        quranList.add("52. At-Tur - سُورةُ الطور")
        quranList.add("53. An-Najm - سُورة النجم")
        quranList.add("54. Al-Qamar - سُورةُ القمر")
        quranList.add("55. Ar-Rahman - سُورةُ الرحمن")
        quranList.add("56. Al-Waqi3ah - سُورةُ الواقعة")
        quranList.add("57. Al-Hadid - سُورة الحديد")
        quranList.add("58. Al-Mujadilah - سُورةُ المجادلة")
        quranList.add("59. Al-Hashr - سُورة الحشر")
        quranList.add("60. Al-Mumtahinah - سُورةُ الممتحنة")
        quranList.add("61. As-Saff - سُورة الصف")
        quranList.add("62. Al-Jumu3ah - سُورة الجمعة")
        quranList.add("63. Al-Munafiqun- سُورة المنافقون")
        quranList.add("64. At-Taghabun - سُورة التغابن")
        quranList.add("65. At-Talaq - سُورة الطلاق")
        quranList.add("66. At-Tahrim - سُورةُ التحريم")
        quranList.add("67. Al-Mulk - سُورة الملك")
        quranList.add("68. Al-Qalam - سُورة القلم")
        quranList.add("69. Al-Haqqah - سُورةُ الحاقة")
        quranList.add("70. Al-Ma3arij - سُورةُ المعارج")
        quranList.add("71. Nuh - سُورة نوح")
        quranList.add("72. Al-Jinn - سُورةُ الجن")
        quranList.add("73. Al-Muzzammil - سُورةالمزمل")
        quranList.add("74. al-Muddathir - سُورة المدثر")
        quranList.add("75. Al-Qiyamah - سُورةُ القيامة")
        quranList.add("76. Al-Insan - سُورةُ الإنسان")
        quranList.add("77. Al-Mursalat - سُورة المرسلات")
        quranList.add("78. An-Naba - سُورة النبأ")
        quranList.add("79. An-Nazi3at - سُورةُ النازعات")
        quranList.add("80. 3abasa - سُورة عبس")
        quranList.add("81. At-Takwir - سُورةُالتكوير")
        quranList.add("82. Al-Infitar - سُورةُ الإنفطار")
        quranList.add("83. Al-Mutaffifin - سُورة المطففين")
        quranList.add("84. Al-Inshiqaq - سُورةُ الإنشقاق")
        quranList.add("85. Al-Buruj - سُورةُ البروج")
        quranList.add("86. At-Tariq - سُورةُ الطارق")
        quranList.add("87. Al-A3la - سُورةُ الأعلى")
        quranList.add("88. Al-Ghashiyah - سُورةُ الغاشية")
        quranList.add("89. Al-Fajr - سُورة الفجر")
        quranList.add("90. Al-Balad - سُورةُ البلد")
        quranList.add("91. Ash-Shams - سُورةُ الشمس")
        quranList.add("92. Al-Layl - سُورةُ الليل")
        quranList.add("93. Ad-Duha - سُورة الضحى")
        quranList.add("94. Ash-Sharh - سُورةُ الشرح")
        quranList.add("95. At-Tin - سُورةُ التين")
        quranList.add("96. Al-3alaq - سُورةُ العلق")
        quranList.add("97. Al-Qadr - سُورةُ القدر")
        quranList.add("98. Al-Bayyinah - سُورةُ البينة")
        quranList.add("99. Az-Zalzalah - سُورة الزلزلة")
        quranList.add("100. Al-3adiyat - سُورةُ العاديات")
        quranList.add("101. Al-Qari3ah - سُورةُ القارعة")
        quranList.add("102. At-Takathur - سُورة التكاثر")
        quranList.add("103. Al-3asr - سُورة العصر")
        quranList.add("104. Al-Humazah - سُورة الهمزة")
        quranList.add("105. Al-Fil - سُورة الفيل")
        quranList.add("106. Quraysh - سُورةُ قريش")
        quranList.add("107. Al-Ma3un - سُورةُ الماعون")
        quranList.add("108. Al-Kawthar - سُورةُ الكوثر")
        quranList.add("109. Al-Kafirun - سُورةُ الكافرون")
        quranList.add("110. An-Nasr - سُورة النصر")
        quranList.add("111. Al-Masad - سُورةُ المسد")
        quranList.add("112. Al-Ikhlas - سُورةُ الإخلاص")
        quranList.add("113. Al-Falaq - سُورةُ الفلق")
        quranList.add("114. An-Nas - سُورةُ الناس")
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

}
