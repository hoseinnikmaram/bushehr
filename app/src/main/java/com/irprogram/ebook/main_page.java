package com.irprogram.ebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class main_page extends Activity
{
    ImageButton btn_list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        TextView txt_search = (TextView) findViewById(R.id.txt_search);
        TextView txt_fav = (TextView) findViewById(R.id.txt_fav);
        TextView txt_content = (TextView) findViewById(R.id.txt_content);
        TextView txt_exit = (TextView) findViewById(R.id.txt_exit);
        TextView txt_setting = (TextView) findViewById(R.id.txt_setting);
        TextView txt_me = (TextView) findViewById(R.id.txt_me);




        myClass.textview_face(this,"IRANSans",txt_fav,txt_search,txt_content,txt_exit,txt_setting,txt_me);


       getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);



    }


    public void onBtnTblofContentClick( View v )
    {
        Intent i = new Intent(this , list_activity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBtnFavoriteClick( View v )
    {
        Intent i = new Intent(this , tblOfFavoriteBooks.class);
        startActivity(i);
        overridePendingTransition( R.anim.fade_in , R.anim.fade_out );
    }

    public void onBtnSearchClick( View v )
    {
        Intent i = new Intent(this , search.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBtnSettingsClick( View v )
    {
        Intent i = new Intent(this , settings.class);
        startActivity(i);
        overridePendingTransition( R.anim.fade_in , R.anim.fade_out );
    }

    public void onBtnAboutMeClick( View v )
    {
        alert_me(
                getString(R.string.about_me_title) ,
                getString(R.string.about_me_message) ,
                true
        );
    }

    public void onBtnWebsiteClick( View v )
    {
        Uri uri = Uri.parse( "http://www.irprogram.com" );
        Intent i = new Intent( Intent.ACTION_VIEW , uri);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBtnContactMeClick( View v )
    {
        alert_me(
                getString(R.string.contact_me_title) ,
                getString(R.string.contact_me_message) ,
                true
        );
    }

    public void onBtnExitClick( View v )
    {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void alert_me( String title , String message , boolean cancelable )
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable( cancelable );
        alert.setTitle(title);
        alert.setMessage(message);
        alert.create();
        alert.show();
    }
}
