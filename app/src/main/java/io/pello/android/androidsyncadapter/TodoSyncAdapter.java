package io.pello.android.androidsyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the actual Sync Adapter
 * Created by PELLO_ALTADILL on 06/01/2017.
 */

public class TodoSyncAdapter  extends AbstractThreadedSyncAdapter {
    private final AccountManager mAccountManager;


    public TodoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("PELLODEBUG", "SyncAdapter working for: " + account.name );
        try {
            // Get the auth token for the current account


          //  String authToken = mAccountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
          //  ParseComServerAccessor parseComService = new ParseComServerAccessor();

            // Get shows from the remote server
            //List remoteTvShows = parseComService.getShows(authToken);

            // Get shows from the local storage
            ArrayList localTvShows = new ArrayList();
          /*  Cursor curTvShows = provider.query(TvShowsContract.CONTENT_URI, null, null, null, null);
            if (curTvShows != null) {
                while (curTvShows.moveToNext()) {
                    localTvShows.add(TvShow.fromCursor(curTvShows));
                }
                curTvShows.close();
            }*/
            // TODO See what Local shows are missing on Remote

            // TODO See what Remote shows are missing on Local

            // TODO Updating remote tv shows

            // TODO Updating local tv shows

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
