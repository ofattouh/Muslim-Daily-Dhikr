package mohamed.omar.com.muslimdailydhikr2

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

data class QuranListAdapter(var quranList: List<String>, var activity: Activity) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return quranList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return quranList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(activity,R.layout.quran_layout_adapter,null)

        //val list_position = view.findViewById<TextView>(R.id.list_position)
        val quran_list_content = view.findViewById<TextView>(R.id.quran_list_content)
        val context            = parent!!.getContext()

        //list_position.text = position.toString()
        quran_list_content.text  = quranList.get(position)

        // Set the item text gravity to right/end and vertical center
        quran_list_content.setGravity(Gravity.RIGHT or Gravity.END or Gravity.CENTER_VERTICAL)

        if (position % 2 !== 0) {
            quran_list_content.setTextColor(ContextCompat.getColor(context, R.color.plum))
        }
        else{
            quran_list_content.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
        }

        quran_list_content.setOnClickListener { view ->

            activity.startActivity(Intent(activity, DisplaySurahActivity::class.java)
                    .putExtra("surah_list_name", quranList.get(position))
                    .putExtra("surah_list_position", position.toString())
                    /*.putExtra("image", food.image)
                    .also {
                        when (tv_lang.text == 0) {
                            a -> it.putExtra(Main2Activity.EXTRA_ADAPTER_MODE, AdapterType.ADAPTER_TYPE_1.ordinal)
                            b -> it.putExtra(Main2Activity.EXTRA_ADAPTER_MODE, AdapterType.ADAPTER_TYPE_2.ordinal)
                            else -> it
                        }
                    }*/
            ) }

        return view
    }

}