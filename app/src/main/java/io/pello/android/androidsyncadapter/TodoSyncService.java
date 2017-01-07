package io.pello.android.androidsyncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by PELLO_ALTADILL on 06/01/2017.
 * This service is needed by the SyncAdapter
 */
public class TodoSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static TodoSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new TodoSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
