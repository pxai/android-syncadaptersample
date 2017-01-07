package io.pello.android.androidsyncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Authenticator service for out dummy authenticator
 * Created by PELLO_ALTADILL on 07/01/2017.
 */

public class DummyAuthenticationService extends Service {
    private DummyAuthenticator dummyAuthenticator;
    @Override
    public void onCreate () {

    }

    @Override
    public IBinder onBind (Intent intent) {
        return dummyAuthenticator.getIBinder();
    }
}
