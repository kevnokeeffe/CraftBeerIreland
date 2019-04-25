package ie.craftbeerireland.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ie.craftbeerireland.R;

public class Splash extends Activity {



        // Splash screen timer
        private static int SPLASH_TIME_OUT = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    overridePendingTransition(R.layout.fade_in, R.layout.fade_out);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }
