package com.irprogram.ebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.irprogram.ebook.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomActivity extends AppCompatActivity {
    private TextView txt;
    private ImageView img1;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        txt = (TextView) findViewById(R.id.txt);
        img1 = (ImageView) findViewById(R.id.img1);
//ekhtesas dadane font
        myClass.textview_face(this, "nastaliq", txt);
//ekhtesas dadane animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.move_right);
        txt.setAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move_top);
        img1.setAnimation(animation1);


//estefade az handler baraye motevaghef kardane amaliate activitie asli ta zamane payane splash ke 8 sanie hastesh
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //startActivity(new Intent(SplashActivity.this, MainActivity.class));

                Intent intent = new Intent(WelcomActivity.this, main_page.class);
                startActivity(intent);

                finish();
            }
        }, 4000);
    }
}
