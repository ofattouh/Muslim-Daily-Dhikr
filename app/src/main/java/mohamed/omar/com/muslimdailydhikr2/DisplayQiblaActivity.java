package mohamed.omar.com.muslimdailydhikr2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static android.view.View.INVISIBLE;

public class DisplayQiblaActivity extends AppCompatActivity {

    private static final String TAG = "DisplayQiblaActivity";

    private Compass compass;
    private ImageView imageQiblaDial;
    private ImageView arrowQiblaView;
    private TextView qiblaLabel;
    private float currentAzimuth;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        context = getApplicationContext();

        imageQiblaDial = findViewById(R.id.main_qibla_dial);
        arrowQiblaView = findViewById(R.id.main_qibla_hands);
        qiblaLabel     = findViewById(R.id.qibla_label);
        setupCompass();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }

    private void setupCompass() {
        compass = new Compass(this);
        Compass.CompassListener cl = getCompassListener();
        compass.setListener(cl);
    }

    public void adjustQiblaDial(float azimuth) {
        //Log.d(TAG, "adjustQiblaDial - will set rotation from " + currentAzimuth + " to "  + azimuth);

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        imageQiblaDial.startAnimation(an);
    }

    private void adjustQiblaArrow(float azimuth) {
        //Log.d(TAG, "adjustQiblaArrow - will set rotation from " + currentAzimuth + " to " + azimuth);

        // Always fetch Qibla Direction using GPS
        String[] settings  = ReadFromFile("settings.txt").split(",");
        //String latitude    = settings[0];
        //String longitude   = settings[1];
        float qiblaDegree = Float.parseFloat(settings[2]);
        String cityName   = settings[3];

        //float qiblaDegree = (float) 54.55;

        //Log.i(TAG, "adjustQiblaArrow: " + latitude + "-" + longitude + "-" + qiblaDegree);

        Animation an = new RotateAnimation(-(currentAzimuth) + qiblaDegree, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = (azimuth);

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        arrowQiblaView.startAnimation(an);

        if(qiblaDegree > 0){
            arrowQiblaView.setVisibility(View.VISIBLE);
        }
        else{
            arrowQiblaView.setVisibility(INVISIBLE);
            arrowQiblaView.setVisibility(View.GONE);
        }

        String qiblaLabelText = cityName + "\nQibla Direction: " + qiblaDegree + " " + (char) 0x00B0;
        qiblaLabel.setText(qiblaLabelText);
        qiblaLabel.setTextColor(ContextCompat.getColor(context, R.color.plum));
    }

    private Compass.CompassListener getCompassListener() {
        return new Compass.CompassListener() {
            @Override
            public void onNewAzimuth(final float azimuth) {
                // UI updates only in UI thread
                // https://stackoverflow.com/q/11140285/444966
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adjustQiblaDial(azimuth);
                        adjustQiblaArrow(azimuth);
                        //adjustArrow(azimuth);
                        //adjustQiblaLabel(azimuth);
                    }
                });
            }
        };
    }

    private String ReadFromFile(String fileName) {

        String data = "";

        String path = getApplicationContext().getFilesDir().getAbsolutePath() + File.separator  + fileName;
        File file = new File(path);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                data = line + '\n';
                //Log.i(TAG, "ReadFromFile" + line + path);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            Log.d(TAG, "ReadFromFile err " + e);
        }

        return data;
    }

   /* private void adjustArrow(float azimuth) {
        Log.d(TAG, "adjustArrow - will set rotation from " + currentAzimuth + " to " + azimuth);

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        arrowQiblaView.startAnimation(an);
    }

    private void adjustQiblaLabel(float azimuth) {
        qiblaLabel.setText("Qibla Direction: " + azimuth);
        //Log.i(TAG, "Qibla Direction: " + azimuth);
    }*/

}
