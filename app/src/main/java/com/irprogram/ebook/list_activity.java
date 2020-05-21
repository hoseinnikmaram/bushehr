package com.irprogram.ebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class list_activity extends Activity {
    LinearLayout gh;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
         gh =(LinearLayout) findViewById(R.id.ghachachagh);
       LinearLayout b =(LinearLayout) findViewById(R.id.bushehr);
        LinearLayout m =(LinearLayout) findViewById(R.id.malevan);
        LinearLayout d =(LinearLayout) findViewById(R.id.marzban);
        LinearLayout k =(LinearLayout) findViewById(R.id.mavad);

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_activity.this , tblOfContent.class);
                i.putExtra( "auther" , "D" );

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        });

        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_activity.this , tblOfContent.class);
                i.putExtra( "auther" , "K" );

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        });


        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_activity.this , tblOfContent.class);
                i.putExtra( "auther" , "g" );

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_activity.this , tblOfContent.class);
                i.putExtra( "auther" , "b" );

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_activity.this , tblOfContent.class);
                i.putExtra( "auther" , "m" );

                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        });


    }

}
