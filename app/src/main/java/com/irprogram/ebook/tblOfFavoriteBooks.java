package com.irprogram.ebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class tblOfFavoriteBooks extends Activity
{
    private ListView favoriteListView;

    private List<HashMap<String , Object>> favorite_list;

    private DatabaseHandler db;
    private TextView tx;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbl_of_favorite_books);
        tx = (TextView) findViewById(R.id.txt_empty);


        myClass.textview_face(this,"IRANSans",tx);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title    =(TextView)toolbar.findViewById(R.id.title);
        ImageView ic_back = (ImageView) toolbar.findViewById(R.id.ic_back);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        title.setText("علاقه ها");
        myClass.textview_face(this,"IRANSans",title);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        favoriteListView = (ListView) findViewById(R.id.tblOfFavoriteBookListView);

        get_book_list();

        favoriteListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id)
                    {
                        Intent i = new Intent(getBaseContext(), book_content.class);

                        String my_id = favorite_list.get(position).get("id").toString();

                        i.putExtra("id", my_id);

                       // startActivity(i);
                        startActivityForResult(i, 5);

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
        );


        if (favorite_list.isEmpty()){
            tx.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 5 && resultCode == RESULT_OK) {
            get_book_list();
        }
        if (favorite_list.isEmpty()){
            tx.setVisibility(View.VISIBLE);
        }
    }

    private void get_book_list() {

        db = new DatabaseHandler( getBaseContext() );

        db.open();

        favorite_list = db.getTableOfFavoriteContent();

        String[] from = { "title" , "author" , "fav_flag" , "see_flag" };

        int[] to = { R.id.txtTitle , R.id.txtAuthor , R.id.setFav , R.id.setSee };

        SimpleAdapter adb = new SimpleAdapter(
                getBaseContext() , favorite_list , R.layout.tbl_content_list_row , from , to
        );

        favoriteListView.setAdapter(adb);

        db.close();
    }

    public void onBtnBackClick( View v )
    {
        finish();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
