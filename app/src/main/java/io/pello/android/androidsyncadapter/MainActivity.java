package io.pello.android.androidsyncadapter;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private String contentUri = "content://io.pello.android.androidloaderssample.sqlprovider.Todo/tareas";
    // This is the Adapter being used to display the list's data.
    SimpleCursorAdapter mAdapter;

    // If non-null, this is the current filter the user has provided.
    String mCurFilter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, null,
                new String[] { "_id", "tarea" },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);

        listView.setAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        Uri baseUri;

        baseUri = Uri.parse(this.contentUri);


        Log.d("PELLODEBUG", "Creating loader");
        return new CursorLoader(this, baseUri,  // The content URI of the words table
                new String[]{"_id","tarea"},               // The columns to return for each row
                "",                        // Selection criteria parameters
                new String[]{""},                     // Selection criteria values
                "");                            // The sort order for the returned rows
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(cursor);

        cursor.moveToFirst();
        String data = "";
        while (!cursor.isAfterLast()) {
            data += "\n" + cursor.getString(1);
            cursor.moveToNext();
        }
        Log.d("PELLODEBUG", "Total records: " + cursor.getCount());

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }
}
