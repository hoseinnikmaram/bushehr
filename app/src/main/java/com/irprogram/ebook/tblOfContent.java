package com.irprogram.ebook;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class tblOfContent extends Activity
{
    private ListView contentListView;

    private List<HashMap<String , Object>> books_list;

    private DatabaseHandler db;
    private String auther;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbl_of_content);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        contentListView = (ListView) findViewById(R.id.tblOfContentListView);


        auther = getIntent().getExtras().getString("auther");

        get_book_list();


        contentListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id)
                    {
                        Intent i = new Intent( getBaseContext() , book_content.class );

                        String my_id = books_list.get( position ).get( "id" ).toString();

                        i.putExtra( "id" , my_id );

                        //startActivity(i);
                        startActivityForResult(i, 10);

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
        );

       // myClass.textview_face(this,"IRANSans",txtTitle);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            get_book_list();
        }
    }


    public void get_book_list(){
        db = new DatabaseHandler( getBaseContext() );

        db.open();

        books_list = db.getTableOfContent(auther);

        String[] from = { "title" , "author" , "fav_flag" , "see_flag" };

        int[] to = { R.id.txtTitle , R.id.txtAuthor , R.id.setFav , R.id.setSee };

        SimpleAdapter adb = new SimpleAdapter(
                getBaseContext() , books_list , R.layout.tbl_content_list_row , from , to
        );

        contentListView.setAdapter( adb );

        db.close();

    }


    public void onBtnBackClick( View v )
    {
        finish();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
