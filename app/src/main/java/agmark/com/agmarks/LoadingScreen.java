package agmark.com.agmarks;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoadingScreen extends Activity  {

    private Handler handler = new Handler();
    Animation animFadein;
    ImageView imgLogo;
    String uname,pwd;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        //imgLogo = (ImageView) findViewById(R.id.imgLogo);
      //  Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_out);//animation initialization
      //  imgLogo.startAnimation(a);//start imageview animation

        // Toast.makeText(LoadingScreen.this, "loading please wait....", Toast.LENGTH_SHORT).show();
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2500);//splash time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                        finish();

                    }

            }
        };
        timerThread.start();//start the splash activity            }
    }
    public void onTrimMemory(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                break;
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                break;
            default:
                break;
        }
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}

