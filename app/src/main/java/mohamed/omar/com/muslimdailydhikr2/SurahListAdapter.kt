package mohamed.omar.com.muslimdailydhikr2

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.Gravity


data class SurahListAdapter(var surahList: List<String>, var activity: Activity) : BaseAdapter(){

    private var context: Context? = null

    fun SurahListAdapter(c: Context) {
        context = c
    }

    override fun getItem(position: Int): Any {
        return surahList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return surahList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(activity,R.layout.surah_layout_adapter,null)

        //val surah_list_position = view.findViewById<TextView>(R.id.list_position)
        val surah_list_content = view.findViewById<TextView>(R.id.surah_list_content)
        val context            = parent!!.getContext()

        //surah_list_position.text = position.toString()
        surah_list_content.text  = surahList.get(position)

        // Set the item text gravity to right/end and vertical center
        surah_list_content.setGravity(Gravity.RIGHT or Gravity.END or Gravity.CENTER_VERTICAL)

        if (position % 2 !== 0) {
            surah_list_content.setTextColor(ContextCompat.getColor(context, R.color.plum))
        }
        else{
            surah_list_content.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
        }

        return view
    }

}