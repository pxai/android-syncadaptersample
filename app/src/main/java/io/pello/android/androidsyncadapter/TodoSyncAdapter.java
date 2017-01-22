package io.pello.android.androidsyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
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
        try {
            // Get the auth token for the current account

            //  String authToken = mAccountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);

            // Get ToDo from the remote server
            List<Task> tasks =  backendAccess.getLast(0);
            Log.d("PELLODEBUG", tasks.toString());
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
