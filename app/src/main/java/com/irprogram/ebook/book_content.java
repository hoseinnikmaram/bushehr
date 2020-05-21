package com.irprogram.ebook;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class book_content extends Activity
{
    private DatabaseHandler db;

    private HashMap<String , Object> book;

    private TextView title , author , date;
    private WebView content;
    private ImageView fav , visit;

    private MediaPlayer player;
    private boolean player_state = false;



  //  public void play_music_start()
  //  {
    //    try
      //  {
        //    player = MediaPlayer.create(this , R.raw.sila);
          //  player.setLooping( true );
         //   player.start();
         //   player_state = true;
       // }
      //  catch( Exception e )
      //  {
            /* do nothing */
       // }
   // }

    public void play_music_stop()
    {
      //  if( player_state == true )
       //     player.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        setResult(RESULT_OK);

        db = new DatabaseHandler( getBaseContext() );

        Bundle data = getIntent().getExtras();

        db.open();

        if( db.getScreenState() == 1 ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if( db.getSoundState() == 1 ) {
         //   play_music_start();
        }

        book = db.getBookContent( data.getString("id") );

        title = (TextView) findViewById(R.id.txtBookTitle);
        author = (TextView) findViewById(R.id.txtBookAuthor);
        date = (TextView) findViewById(R.id.txtBookDate);
        content = (WebView) findViewById(R.id.WebViewBookContent);
        fav = (ImageView) findViewById(R.id.imgFavorite);
        visit = (ImageView) findViewById(R.id.imgSee);

        title.setText( book.get("title").toString() );
        author.setText( book.get("author").toString() );
        date.setText( book.get("date").toString() );

        String font_size = String.valueOf( db.getFontSize() );

        String main_txt =
                "<html>" +
                    "<head></head>" +
                    "<body dir='rtl' style='font-size: " +
                        font_size + "px; text-align: justify;' >" +
                        book.get("content").toString() +
                    "</body>" +
                "</html>";

        content.loadDataWithBaseURL(
                null , main_txt , "text/html; charset=utf-8" , "UTF-8" , null
        );
        content.getSettings().setJavaScriptEnabled(true);
        content.setBackgroundColor(0x00000000);

        fav.setImageResource(
                Integer.parseInt(book.get("fav_flag").toString())
        );

        visit.setImageResource(
                Integer.parseInt( book.get("see_flag").toString() )
        );

        db.close();

        myClass.textview_face(this,"IRANSans",title);

    }

    public void onImgSeeClick( View v )
    {
        db.open();

        int id = Integer.parseInt( book.get("id").toString() );

        if( db.getBookVisitState(id) == 1 )
        {
            db.setBookVisitState( id , 0 );

            visit.setImageResource( R.drawable.not_see );
        }
        else
        {
            db.setBookVisitState( id , 1 );

            visit.setImageResource(R.drawable.see);
        }

        db.close();
    }

    public void onImgFavoriteClick( View v )
    {
        db.open();

        int id = Integer.parseInt( book.get("id").toString() );

        if( db.getBookFavoriteState(id) == 1 )
        {
            db.setBookFavoriteState( id , 0 );

            fav.setImageResource( R.drawable.not_favorite );
        }
        else
        {
            db.setBookFavoriteState( id , 1 );

            fav.setImageResource(R.drawable.is_favorite);
        }

        db.close();
    }

    public void onShareTextClick( View v )
    {
        Intent i = new Intent( Intent.ACTION_SEND );

        i.setType( "text/plain" );

        i.putExtra(Intent.EXTRA_SUBJECT, "subject");

        i.putExtra( Intent.EXTRA_TEXT , book.get("content").toString() );

        startActivity(Intent.createChooser(i, "sending"));

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBtnBackClick( View v )
    {

        finish();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        play_music_stop();
        setResult(RESULT_OK);

    }
}
