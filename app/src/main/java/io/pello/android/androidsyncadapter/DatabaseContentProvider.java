package io.pello.android.androidsyncadapter;

/**
 * Created by PELLO_ALTADILL on 13/12/2016.
 */
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

/**
 * A content provider to offer Student data to clients.
 * It extends ContentProvider class providing a common way
 * to manage data with a CRUD-like methods set.
 * @author Pello Xabier Altadill Izura
 * @greetz any
 */
public class DatabaseContentProvider extends ContentProvider {

    // We set uriMatcher to get params passed to URIs.
    // So we can give different values depending on those params
    private  UriMatcher uriMatcher;
    // Our data:
    private MatrixCursor mCursor;
    private DbAdapter dbAdapter;
    /**
     * default constructor.
     */
    public DatabaseContentProvider() {

    }

    /**
     * called when provider is started, so we use it to initialize data.
     */
    @Override
    public boolean onCreate() {
        Log.d("PELLODEBUG","CP> onCreate, init data.");
        dbAdapter = new DbAdapter(getContext());
        dbAdapter.open();
        dbAdapter.insertarTarea("STUDY");
        dbAdapter.insertarTarea("LEARN");
        initUris();
        return true;
    }
    /**
     * init content provider Uris
     * we set some kind of uri patterns to route them to different queries
     */
    private void initUris() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // This will match: content://io.pello.android.androidloaderssample.provider.Students/students/1
        uriMatcher.addURI("io.pello.android.androidloaderssample.sqlprovider.Todo", "tareas/", 1);

        // This will match: content://io.pello.android.androidloaderssample.provider.Students/students/2
        uriMatcher.addURI("io.pello.android.androidloaderssample.provider.Students", "students/*/", 2);
    }




    /**
     * we query the database, depending on uriMatcher we can execute
     * different queries.
     * The parameters of the query are the same of a SQLite helper query.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d("PELLODEBUG","CP> query " + uri+ " match:" + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case 1:
                Log.d("PELLODEBUG","query to 1. ");
                return dbAdapter.obtenerTareas();
            case 2:
                Log.d("PELLODEBUG","query to 2. " + uri.getLastPathSegment());
                break;
            default:	break;
        }


        return mCursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("PELLODEBUG","CP> " + uri);
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        Log.d("PELLODEBUG","CP> " + uri);
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("PELLODEBUG","CP> insert " + uri);

        mCursor.addRow(new Object[]{
                values.getAsLong("_id"),
                values.getAsString("name"),
                values.getAsString("description")
        });
        Uri resultUri = Uri.parse("content://io.pello.android.androidloaderssample.provider.Students/students");
        return resultUri;

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d("PELLODEBUG","CP> " + uri);


        return 0;

    }
}

