package com.irprogram.ebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private Context main_context;

    private static String DB_PATH;
    private static String DB_NAME = "ebook_db.db";
    private static int DB_VERSION = 1;
    private static String DB_TBL_BOOKS = "books";
    private static String DB_TBL_SETTINGS = "settings";

    private SQLiteDatabase db;

    public DatabaseHandler( Context con )
    {
        super(con, DB_NAME, null, DB_VERSION);

        main_context = con;

        DB_PATH = con.getCacheDir().getPath() + "/" + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* do nothing */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /* do nothing */
    }

    private boolean dbExists()
    {
        File f = new File( DB_PATH );
        if( f.exists() )
            return true;
        else
            return false;
    }

    private boolean copyDB()
    {
        try
        {
            FileOutputStream out = new FileOutputStream( DB_PATH );

            InputStream in = main_context.getAssets().open( DB_NAME );

            byte[] buffer = new byte[1024];
            int ch;

            while( ( ch = in.read( buffer) ) > 0 )
            {
                out.write( buffer , 0 , ch);
            }

            out.flush();
            out.close();
            in.close();

            return true;
        }
        catch( Exception e )
        {
            /* do nothing */
            return false;
        }
    }

    public void open()
    {
        if( dbExists() )
        {
            try
            {
                File temp = new File( DB_PATH );

                db = SQLiteDatabase.openDatabase(
                        temp.getAbsolutePath() , null , SQLiteDatabase.OPEN_READWRITE
                );
            }
            catch(Exception e)
            {
                /* do nothing */
            }
        }
        else
        {
            if( copyDB() )
                open();
        }
    }

    @Override
    public synchronized void close()
    {
        db.close();
    }

    public boolean setBookVisitState( int id , int state )
    {
        ContentValues cv = new ContentValues();
        cv.put("see_flag", state);

        long result = db.update(
                DB_TBL_BOOKS , cv , "id = ?" , new String[] { String.valueOf(id) }
        );

        if( result < 1 )
            return false;
        else
            return true;
    }

    public boolean setBookFavoriteState( int id , int state )
    {
        ContentValues cv = new ContentValues();
        cv.put("fav_flag", state);

        long result = db.update(
                DB_TBL_BOOKS , cv , "id = ?" , new String[] { String.valueOf(id) }
        );

        if( result < 1 )
            return false;
        else
            return true;
    }

    public int getBookFavoriteState( int id )
    {
        Cursor result = db.rawQuery(
            "SELECT * FROM " + DB_TBL_BOOKS + " WHERE id = '" + id + "'", null
        );

        result.moveToFirst();

        return Integer.parseInt( result.getString( 5 ) );
    }

    public int getBookVisitState( int id )
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_BOOKS + " WHERE id = '" + id + "'", null
        );

        result.moveToFirst();

        return Integer.parseInt( result.getString( 6 ) );
    }

    public List<HashMap<String , Object>> getTableOfContent( String author)
    {
        Cursor result = db.rawQuery( "SELECT * FROM " + DB_TBL_BOOKS + " WHERE author = '" + author + "' " , null );

        List<HashMap<String , Object>> all_data = new ArrayList<>();

        while( result.moveToNext() )
        {
            HashMap<String , Object> temp = new HashMap<>();

            temp.put("id", result.getString(0));
            temp.put( "title" , result.getString( 1 ) );
            temp.put("author", result.getString(3));

            if( result.getString( 5 ).equals("1") ) {
                temp.put( "fav_flag" , R.drawable.is_favorite );
            }
            else {
                temp.put( "fav_flag" , R.drawable.not_favorite );
            }

            if( result.getString( 6 ).equals("1") ) {
                temp.put( "see_flag" , R.drawable.see );
            }
            else {
                temp.put( "see_flag" , R.drawable.not_see );
            }

            all_data.add( temp );
        }

        return all_data;
    }


    public HashMap<String , Object> getBookContent( String id )
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_BOOKS + " WHERE id = '" + id + "' " , null
        );

        result.moveToFirst();

        HashMap<String , Object> all_data = new HashMap<>();

        all_data.put( "id" , result.getString( 0 ) );
        all_data.put( "title" , result.getString( 1 ) );
        all_data.put( "content" , result.getString( 2 ) );
        all_data.put( "author" , result.getString( 3 ) );
        all_data.put( "date" , result.getString( 4 ) );

        if( result.getString( 5 ).equals("1") ) {
            all_data.put( "fav_flag" , R.drawable.is_favorite );
        }
        else {
            all_data.put( "fav_flag" , R.drawable.not_favorite );
        }

        if( result.getString( 6 ).equals("1") ) {
            all_data.put( "see_flag" , R.drawable.see );
        }
        else {
            if( setBookVisitState( Integer.parseInt( id ) , 1 ) )
            {
                all_data.put( "see_flag" , R.drawable.see );
            }
            else
            {
                all_data.put( "see_flag" , R.drawable.not_see );
            }
        }

        return all_data;
    }

    public List<HashMap<String , Object>> getTableOfFavoriteContent()
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_BOOKS + " WHERE fav_flag='1' " , null
        );

        List<HashMap<String , Object>> all_data = new ArrayList<>();

        while( result.moveToNext() )
        {
            HashMap<String , Object> temp = new HashMap<>();

            temp.put("id", result.getString(0));
            temp.put("title" , result.getString( 1 ));
            temp.put("author", result.getString(3));
            temp.put( "fav_flag" , R.drawable.is_favorite );

            if( result.getString( 6 ).equals("1") ) {
                temp.put( "see_flag" , R.drawable.see );
            }
            else {
                temp.put("see_flag", R.drawable.not_see);
            }

            all_data.add( temp );
        }

        return all_data;
    }

    public List<HashMap<String , Object>> getTableOfResultsOfSearch( String q )
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_BOOKS + " WHERE " + q , null
        );

        List<HashMap<String , Object>> all_data = new ArrayList<>();

        while( result.moveToNext() )
        {
            HashMap<String , Object> temp = new HashMap<>();

            temp.put("id", result.getString(0));
            temp.put("title" , result.getString( 1 ));
            temp.put("author", result.getString(3));

            if( result.getString( 5 ).equals("1") ) {
                temp.put( "fav_flag" , R.drawable.is_favorite );
            }
            else {
                temp.put( "fav_flag" , R.drawable.not_favorite );
            }

            if( result.getString( 6 ).equals("1") ) {
                temp.put( "see_flag" , R.drawable.see );
            }
            else {
                temp.put( "see_flag" , R.drawable.not_see );
            }

            all_data.add( temp );
        }

        return all_data;
    }

    public int getSoundState()
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_SETTINGS + " WHERE key='sound' ", null
        );

        result.moveToFirst();

        return Integer.parseInt( result.getString( 2 ) );
    }

    public boolean setSoundState( int state )
    {
        ContentValues cv = new ContentValues();
        cv.put("value", state);

        long result = db.update(
                DB_TBL_SETTINGS , cv , "key = ?" , new String[] {"sound"}
        );

        if( result < 1 )
            return false;
        else
            return true;
    }

    public int getScreenState()
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_SETTINGS + " WHERE key='screen' ", null
        );

        result.moveToFirst();

        return Integer.parseInt( result.getString( 2 ) );
    }

    public boolean setScreenState( int state )
    {
        ContentValues cv = new ContentValues();
        cv.put("value", state);

        long result = db.update(
                DB_TBL_SETTINGS , cv , "key = ?" , new String[] {"screen"}
        );

        if( result < 1 )
            return false;
        else
            return true;
    }

    public int getFontSize()
    {
        Cursor result = db.rawQuery(
                "SELECT * FROM " + DB_TBL_SETTINGS + " WHERE key='font_size' ", null
        );

        result.moveToFirst();

        return Integer.parseInt( result.getString( 2 ) );
    }

    public boolean setFontSize( int size )
    {
        ContentValues cv = new ContentValues();
        cv.put("value", size);

        long result = db.update(
                DB_TBL_SETTINGS , cv , "key = ?" , new String[] {"font_size"}
        );

        if( result < 1 )
            return false;
        else
            return true;
    }
}
