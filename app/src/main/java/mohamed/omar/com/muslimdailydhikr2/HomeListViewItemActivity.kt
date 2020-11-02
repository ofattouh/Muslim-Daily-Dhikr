package mohamed.omar.com.muslimdailydhikr2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_list_view_item_home.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import android.util.TypedValue
import android.view.Gravity
import java.io.*
import java.util.*

class HomeListViewItemActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_item_home)

        val bundle          = intent.extras
        var fileName        = ""
        var numberFragments = 0

        // listview_item_position.text = bundle.getString("list_position")

        if (bundle != null) {

            // val list_item_content = findViewById<TextView>(R.id.activity_listviewitem)
            // list_item_content.text = bundle.getString("list_name")

            // Makkah Live
            if (Integer.parseInt(bundle.getString("home_list_position")) == 0) {
                val intent = Intent(this, DisplayMakkahLiveActivity::class.java)
                intent.putExtra("HOME_LIST_POSITION", Integer.parseInt(bundle.getString("home_list_position")))
                intent.putExtra("HOME_LIST_NAME", bundle.getString("home_list_name"))
                startActivity(intent)
            }

            // Medina Live
            if (Integer.parseInt(bundle.getString("home_list_position")) == 1) {
                val intent = Intent(this, DisplayMedinaLiveActivity::class.java)
                intent.putExtra("HOME_LIST_POSITION", Integer.parseInt(bundle.getString("home_list_position")))
                intent.putExtra("HOME_LIST_NAME", bundle.getString("home_list_name"))
                startActivity(intent)
            }

            // Quran Radio
           /* if (Integer.parseInt(bundle.getString("home_list_position")) == 3) {
                val intent = Intent(this, DisplayQuranRadioLiveActivity::class.java)
                startActivity(intent)
            }*/

            // Quran offline
            if (Integer.parseInt(bundle.getString("home_list_position")) == 3) {
                val intent = Intent(this, DisplayQuranActivity::class.java)
                startActivity(intent)
            }

            // Morning prayers
            if (Integer.parseInt(bundle.getString("home_list_position")) == 4) {
                fileName        = "Morning_azkar.txt"
                numberFragments = 23
                /*list_item_content.text = application.assets.open("Morning_azkar.txt").bufferedReader().use{
                    it.readText()
                }*/
            }

            // Evening prayers
            if (Integer.parseInt(bundle.getString("home_list_position")) == 5) {
                fileName        = "Evening_azkar.txt"
                numberFragments = 23
            }

            // Dua Quran
            if (Integer.parseInt(bundle.getString("home_list_position")) == 6) {
                fileName        = "Dua_Quran.txt"
                numberFragments = 30
            }

            // Dua & recitations
            if (Integer.parseInt(bundle.getString("home_list_position")) == 7) {
                fileName        = "Dua_ibtihalat.txt"
                numberFragments = 249
            }

            // Allah 99 names
            if (Integer.parseInt(bundle.getString("home_list_position")) == 8) {
                fileName        = "Allah_99_names.txt"
                numberFragments = 99
                // list_item_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30.0F);
            }

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, fileName, numberFragments)

            // Set up the ViewPager with the sections adapter.
            containerpager.adapter = mSectionsPagerAdapter

        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager, fileName: String, numberFragments: Int) : FragmentPagerAdapter(fm) {

        var fileName        = fileName
        var numberFragments = numberFragments

        override fun getItem(position: Int): Fragment {
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, fileName)
        }

        override fun getCount(): Int {
            return numberFragments
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        protected val TAG = PlaceholderFragment::class.java.simpleName

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val context  = container!!.getContext()

           /* rootView.activity_listviewitem.text = context?.getAssets()?.open(arguments?.getString(ARG_FILE_NAME))?.bufferedReader().use{
                it?.readText()
            }*/

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

            var numberFragments = fileLines.size - 1

            for (oneline in fileLines) {

                if (fragmentNumber == lineNumber) {
                    rootView.activity_listviewitem.text = oneline
                    rootView.viewFragmentCounter.text   = fragmentNumber.toString() + "/" + numberFragments
                }

                //Log.i(TAG, "fileLines: " + lineNumber.toString() + ": " + oneline + ": fragmentNumber: " + fragmentNumber)
                lineNumber++
            }

            if (fileName == "Allah_99_names.txt"){
                rootView.activity_listviewitem.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f);
            }

            // Set the item text gravity to right/end and vertical center
            rootView.activity_listviewitem.setGravity(Gravity.RIGHT or Gravity.END or Gravity.CENTER_VERTICAL)
            rootView.activity_listviewitem.setTextColor(ContextCompat.getColor(context, R.color.plum))
            rootView.viewFragmentCounter.setTextColor(ContextCompat.getColor(context, R.color.light_blue))

            return rootView
        }

        /*fun ReadTextFromFile(fileName: String, FragmentNumber: String) : String{
            var data = ""
            var inputStream: InputStream? = null

            try {
                inputStream = context?.getAssets()?.open(fileName)
                val fh      = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                val lines   = StringBuilder()

                while ( fh.readLine() != null ) {
                    lines.append(fh.readLine())
                }

                fh.close()
                data = lines.toString()
            }
            catch (e: IOException) {
                e.printStackTrace()
            }

            return data
        }*/

        companion object {

            // The fragment argument representing the section number for this fragment.
            private val ARG_SECTION_NUMBER = "section_number"

            // The fragment argument representing the file name for this fragment.
            private val ARG_FILE_NAME = "file_name"

            /**
             * Returns a new instance of this fragment for the given section number.
             */
            fun newInstance(sectionNumber: Int, fileName: String): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args     = Bundle()

                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                args.putString(ARG_FILE_NAME, fileName)
                fragment.arguments = args
                return fragment
            }
        }
    }

}
