package com.irprogram.ebook;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class settings extends Activity
{
    private DatabaseHandler db;

    private ImageView sound;
    private TextView txtSound  , txtFont;
    private TextView title;
    private ImageView ic_back;
    private SwitchCompat switchCompat;
    private AppCompatSeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

       getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        db = new DatabaseHandler( getBaseContext() );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        title    =(TextView)toolbar.findViewById(R.id.title);
        ic_back    =(ImageView)toolbar.findViewById(R.id.ic_back);
        switchCompat = (SwitchCompat) findViewById(R.id.switch_id);
        sound = (ImageView) findViewById(R.id.imgSoundState);
        seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar_id);


        txtSound = (TextView) findViewById(R.id.txtSoundState);
        txtFont = (TextView) findViewById(R.id.txtFontSize);

        title.setText("تنظیمات");
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        myClass.textview_face(this,"IRANSans",title);

        db.open();

        if( db.getSoundState() == 1 ) {
            sound.setImageResource( R.drawable.sound_play );
            txtSound.setText( R.string.sound_settings_on );
        } else {
            sound.setImageResource( R.drawable.sound_stop );
            txtSound.setText( R.string.sound_settings_off );
        }

        if( db.getScreenState() == 1 ) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);

        }

        int font_size = db.getFontSize()+20;
        txtFont.setText(
                getString(R.string.font_size_settings_sample) + " " +
                String.valueOf( font_size )
        );
        txtFont.setTextSize( font_size );
      //  rangeBar.setSeekPinByIndex(db.getFontSize());

        db.close();



        set_rangebar();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                db.open();

                if( db.getScreenState() == 1 )
                {
                    if( db.setScreenState(0) ) {

                        switchCompat.setChecked(false);
                        Toast.makeText(getApplicationContext(),R.string.screen_light_settings_general,Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    if( db.setScreenState(1) ) {
                        switchCompat.setChecked(true);
                        Toast.makeText(getApplicationContext(),R.string.screen_light_settings_on,Toast.LENGTH_LONG).show();
                    }
                }

                db.close();

            }
        });

    }

    private void set_rangebar() {

        db.open();

        seekBar.setProgress(db.getFontSize());
        seekBar.setKeyProgressIncrement(1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                txtFont.setText(  getString(R.string.font_size_settings_sample) + " " +i );
                db.setFontSize(i);
                txtFont.setTextSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });




    }
    public void onImgSoundStateClick( View v )
    {
        db.open();

        if( db.getSoundState() == 1 )
        {
            if( db.setSoundState(0) ) {
                sound.setImageResource( R.drawable.sound_stop );
                txtSound.setText( R.string.sound_settings_off );
            }
        }
        else
        {
            if( db.setSoundState(1) ) {
                sound.setImageResource( R.drawable.sound_play );
                txtSound.setText( R.string.sound_settings_on );
            }
        }

        db.close();
    }






    public void onImgFontSizeUpClick( View v )
    {
        db.open();

        int size = db.getFontSize();

        if( db.setFontSize( size + 1 ) ) {
            size ++;
            txtFont.setText(
                    getString(R.string.font_size_settings_sample) + " " +
                            String.valueOf( size )
            );
            txtFont.setTextSize( size );
        }

        db.close();
    }

    public void onImgFontSizeDownClick( View v )
    {
        db.open();

        int size = db.getFontSize();

        if( db.setFontSize( size - 1 ) ) {
            size --;
            txtFont.setText(
                    getString(R.string.font_size_settings_sample) + " " +
                            String.valueOf( size )
            );
            txtFont.setTextSize( size );
        }

        db.close();
    }

    public void onBtnBackClick( View v )
    {
        finish();

        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
    }
}
