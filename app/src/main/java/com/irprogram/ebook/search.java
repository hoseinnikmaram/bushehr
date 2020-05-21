package com.irprogram.ebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class search extends Activity
{
    private DatabaseHandler db;
    private SearchView etSearchKey;
    private ListView resultListView;

    private List<HashMap<String , Object>> resultBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        resultListView = (ListView) findViewById(R.id.resultListView);
        etSearchKey = (SearchView) findViewById(R.id.txtSearchKey);

        db = new DatabaseHandler( getBaseContext() );

        etSearchKey.setIconifiedByDefault(false);
        etSearchKey.isActivated();


        etSearchKey.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {

                text_search(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        etSearchKey.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text_search(etSearchKey.getQuery().toString());
        }
    });
    }

    private void text_search(String text) {
        resultListView.setAdapter(null);

        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rgItems);

        int selected_item_id = rGroup.getCheckedRadioButtonId();

        RadioButton rbTitle = (RadioButton) findViewById(R.id.rbSearchByTitle);
        RadioButton rbContent = (RadioButton) findViewById(R.id.rbSearchByContent);
        //  RadioButton rbAuthor = (RadioButton) findViewById(R.id.rbSearchByAuthor);

        String search_by = "";

        if( selected_item_id == rbTitle.getId() ) {
            search_by = "title";
        } else if( selected_item_id == rbContent.getId() ) {
            search_by = "content";
        }
        //else if( selected_item_id == rbAuthor.getId() ) {
        //   search_by = "author";
        // }


        if( text.length() < 1 )
        {
            Toast.makeText(
                    getBaseContext() , getString(R.string.search_error_enter_text) ,
                    Toast.LENGTH_SHORT ).show();

            return;
        }

        String key = text.toString().trim();

        String[] keys = key.split("\\s+");

        String query = search_by + " LIKE '%" + keys[0] + "%'";

        StringBuilder sb = new StringBuilder();

        for( int i = 1; i < keys.length; i ++ )
        {
            sb.append( " OR " + search_by + " LIKE '%" + keys[i] + "%'" );
        }

        query = query + sb.toString();

        /* title LIKE '%ali%' OR title LIKE '%reza%' OR ... */

        showResultOfSearch( query );


    }




    public void showResultOfSearch( String query )
    {
        db.open();

        resultBooks = db.getTableOfResultsOfSearch( query );

        db.close();

        if( resultBooks.size() < 1 )
        {
            Toast.makeText(
                    getBaseContext() , getString(R.string.search_error_not_found) ,
                    Toast.LENGTH_SHORT ).show();

            return;
        }

        String[] from = { "title" , "author" , "fav_flag" , "see_flag" };

        int[] to = { R.id.txtTitle , R.id.txtAuthor , R.id.setFav , R.id.setSee };

        SimpleAdapter adb = new SimpleAdapter(
                getBaseContext() , resultBooks ,
                R.layout.tbl_content_list_row , from , to
        );

        resultListView.setAdapter(adb);

        resultListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id)
                    {
                        Intent i = new Intent(getBaseContext(), book_content.class);

                        String my_id = resultBooks.get(position).get("id").toString();

                        i.putExtra("id", my_id);

                        startActivity(i);

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
        );
    }

 //   public void onBtnBackClick( View v )
 //   {
   //     finish();

     //   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    //}
}
