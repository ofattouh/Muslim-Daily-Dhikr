package mohamed.omar.com.muslimdailydhikr2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.view.LayoutInflater

data class ApiAdapter(var PrayerTimingsList: List<String>, var activity: Context) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return PrayerTimingsList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return PrayerTimingsList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View   = LayoutInflater.from(parent!!.getContext()).inflate(R.layout.prayers_timings_adaptor, parent, false)
        val prayer_time  = view.findViewById<TextView>(R.id.prayerTime)
        prayer_time.text = PrayerTimingsList.get(position)
        return view
    }

}