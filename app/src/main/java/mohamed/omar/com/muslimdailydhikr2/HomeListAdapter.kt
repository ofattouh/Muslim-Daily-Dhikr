package mohamed.omar.com.muslimdailydhikr2

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

data class HomeListAdapter(var duaList: ArrayList<Any>, var activity: Activity) : BaseAdapter(){

    private val TAG = HomeListAdapter::class.java.simpleName

    override fun getItem(position: Int): Any {
        return duaList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return duaList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(activity,R.layout.home_layout_adapter,null)

        //val list_position = view.findViewById<TextView>(R.id.list_position)
        /*val displayMetrics = DisplayMetrics()
        val windowmanager  = activity.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowmanager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth  = displayMetrics.widthPixels
        val deviceHeight = displayMetrics.heightPixels*/

        val home_list_content   = view.findViewById<TextView>(R.id.home_list_content)
        val home_list_imageView = view.findViewById<ImageView>(R.id.home_list_imageView)
        val context             = parent!!.getContext()

        //list_position.text = position.toString()

        if (position == 0) {
            //home_list_imageView.getLayoutParams().width  = 440;
            home_list_imageView.getLayoutParams().height = 400;
            home_list_imageView.setImageResource(R.drawable.makkah_live);
            home_list_content.text = duaList.get(position).toString()
            home_list_content.setTextColor(ContextCompat.getColor(context, R.color.light_blue))

            // View.setPadding(int left, int top, int right, int bottom)
            home_list_content.setPadding(0,
                    context.getResources().getDimensionPixelSize(R.dimen.text_padding_top_home_list_content),
                    0, 0)
        }
        else if (position == 1) {
            home_list_imageView.getLayoutParams().height = 400;
            home_list_imageView.setImageResource(R.drawable.medina_live);
            home_list_content.text = duaList.get(position).toString()
            home_list_content.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
            home_list_content.setPadding(0,
                    context.getResources().getDimensionPixelSize(R.dimen.text_padding_top_home_list_content),
                    0, 0)
        }
        /*else if (position == 2) {
            home_list_imageView.getLayoutParams().height = 400;
            home_list_imageView.setImageResource(R.drawable.quran);
        }
        else if (position == 3 || position == 4) {
            home_list_content.text = duaList.get(position).toString()
            home_list_content.setTextColor(ContextCompat.getColor(context, R.color.green_dark))
        }*/
        else if (position == 2) {
            home_list_imageView.getLayoutParams().height = 400;
            home_list_imageView.setImageResource(R.drawable.azkar);
        }
        else{
            home_list_content.text = duaList.get(position).toString()
            home_list_content.setTextColor(ContextCompat.getColor(context, R.color.plum))
        }

        home_list_content.setOnClickListener { view ->

            activity.startActivity(Intent(activity, HomeListViewItemActivity::class.java)
                    .putExtra("home_list_name", duaList.get(position).toString())
                    .putExtra("home_list_position", position.toString())
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