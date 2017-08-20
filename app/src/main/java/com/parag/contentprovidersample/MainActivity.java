package com.parag.contentprovidersample;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>{

    Button button;
    ListView listView;
    String[] projection = {ContactsContract.Contacts.DISPLAY_NAME};
    boolean hasLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.btn);
        listView = (ListView)findViewById(R.id.listview);
        button.setOnClickListener(this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) { // CursorLoader instance
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String orderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
        if(i == 1) {
            return new CursorLoader(this,uri ,projection,null,null,orderBy);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        ArrayList<String> contactList = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                contactList.add(cursor.getString(0));
            }
            ArrayAdapter<String> contactArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactList);
            listView.setAdapter(contactArrayAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn:
                if(!hasLoaded)
                {
                    getLoaderManager().initLoader(1,null,this);
                    hasLoaded = true;
                }
                else
                {
                    getLoaderManager().restartLoader(1,null,this);
                }
        }
    }
}
