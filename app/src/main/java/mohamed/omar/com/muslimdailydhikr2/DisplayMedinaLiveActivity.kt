package mohamed.omar.com.muslimdailydhikr2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView

class DisplayMedinaLiveActivity : AppCompatActivity() {

    private val TAG = DisplayMedinaLiveActivity::class.java.simpleName
    var ShowOrHideWebViewInitialUse = "show"

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
        setContentView(R.layout.activity_display_medina_live)

       /* val displayMetrics = DisplayMetrics()
        val windowmanager  = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowmanager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth  = displayMetrics.widthPixels
        val deviceHeight = displayMetrics.heightPixels*/

        //Log.i(TAG, "width:" + deviceWidth + ", Height: " + deviceHeight + intent.getStringExtra("HOME_LIST_NAME"))

        val medina_live_textview  = findViewById<TextView>(R.id.medina_live_textview)
        val display_youtube_video = findViewById<WebView>(R.id.webview_medina_live)
        val spinner               = findViewById<ProgressBar>(R.id.progressBar_medina_live)

        // URL: https://stackoverflow.com/questions/39204757/youtube-live-streaming-embed-code-keeps-changing
        // URL: https://commentpicker.com/youtube-channel-id.php
        // https://gaming.youtube.com/embed/live_stream?channel=[channel ID]
        //val src        = "https://www.youtube.com/embed/live_stream?channel=UCTY1OtnpVLUJGpY1PoiE5WA"
        val src        = "https://www.youtube.com/embed/live_stream?channel=UC_ANPr8IkWibKlKhmi_-H1g"
        val frameVideo = "<html><body><iframe src=" + src + " height=90% width=100% " +
                        "frameborder=0 allow=accelerometer; autoplay; encrypted-media; gyroscope; " +
                        "picture-in-picture allowfullscreen allowtransparency=true" +
                        "</iframe></body></html>"

        // Set the item text gravity to right/end and vertical center
        //medina_live_textview.setGravity(Gravity.RIGHT or Gravity.END or Gravity.CENTER_VERTICAL)

        // Webview client
        display_youtube_video.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            // This allows for a splash screen and hide elements once the page loads
            override fun onPageStarted(webview: WebView, url: String, favicon: Bitmap?) {

                // only make it invisible the FIRST time the app is run
                if (ShowOrHideWebViewInitialUse.equals("show")) {
                    webview.setVisibility(View.INVISIBLE)
                }
            }

            override fun onPageFinished(webview: WebView, url: String) {
                ShowOrHideWebViewInitialUse = "hide"
                spinner.setVisibility(View.GONE)
                medina_live_textview.setVisibility(View.GONE)
                webview.setVisibility(View.VISIBLE)
                super.onPageFinished(webview, url)
            }
        }

        val webSettings = display_youtube_video.settings
        webSettings.javaScriptEnabled = true
        display_youtube_video.loadData(frameVideo, "text/html", "utf-8")

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
