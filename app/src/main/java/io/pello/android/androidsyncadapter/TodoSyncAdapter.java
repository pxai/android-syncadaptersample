package io.pello.android.androidsyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;


/**
 * This is the actual Sync Adapter
 * Created by PELLO_ALTADILL on 06/01/2017.
 */

public class TodoSyncAdapter  extends AbstractThreadedSyncAdapter {
    private final AccountManager mAccountManager;
    private ContentResolver contentResolver;
    private BackendAccess backendAccess;
    private String contentUri = "content://io.pello.android.androidsyncadapter.sqlprovider.Todo";

    public TodoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        backendAccess = new BackendAccess();
        mAccountManager = AccountManager.get(context);
        contentResolver = context.getContentResolver();
    }

    public TodoSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mAccountManager = AccountManager.get(context);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("PELLODEBUG", "SyncAdapter working for: " + account.name );
        int lastLocalId = 0;
        int lastBackendId = 0;
        try {
            // Get the auth token for the current account

            //  String authToken = mAccountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
          //  Cursor cursor = provider.query("",null,null);
           Cursor cursor = provider.query(
                    Uri.parse(contentUri + "/tasks/last/local"),   // The content URI of the words table
                    new String[]{"_id","task","id_backend","is_read"},
                       "",                        // The columns to return for each row
                    new String[]{""},                     // Selection criteria
                    "");
            if (cursor.getCount() > 0) {
                lastLocalId = cursor.getInt(0);
                Log.d("PELLODEBUG","Last local Id: " + cursor.getString(0));
            }
            Log.d("PELLODEBU'", "Last local Id: " + lastLocalId);

            /////////////////// UPDATE FROM BACKEND /////////////////////
            // Get Last backend_id locally
            cursor = provider.query(
                    Uri.parse(contentUri + "/tasks/last/backend"),   // The content URI of the words table
                    new String[]{"_id","task","id_backend","is_read"},
                    "",                        // The columns to return for each row
                    new String[]{""},                     // Selection criteria
                    "");
            if (cursor.getCount() > 0) {
                lastBackendId = cursor.getInt(2);
                Log.d("PELLODEBUG", "backend_id:" + cursor.getString(2));
            }
            Log.d("PELLODEBU'", "Last backend Id: " + lastBackendId);

            // Get ToDo from the remote server
            List<Task> tasks =  backendAccess.getLast(lastBackendId);
            Log.d("PELLODEBUG", tasks.toString());
            for (Task task : tasks) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("task",task.getTask());
                contentValues.put("backend_id",task.getBackendId());

                // We finally make the request to the content provider
                Uri resultUri = provider.insert(
                        Uri.parse(contentUri),   // The content URI
                        contentValues
                );
                Log.d("PELLODEBUG","Inserted in local db: " + task.getTask());
            }
            // Get ToDo list from the local storage

            // TODO See what Local ToDo tasks are missing on Remote

            // TODO See what Remote ToDo tasks are missing on Local

            // TODO Updating remote ToDo tasks

            // TODO Updating local ToDo tasks

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
