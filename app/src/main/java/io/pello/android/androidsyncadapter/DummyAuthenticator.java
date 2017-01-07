package io.pello.android.androidsyncadapter;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Simple dummy authenticator, needed by the sync adapter, transparent
 * for the user.
 * Created by PELLO_ALTADILL on 08/01/2017.
 */

public class DummyAuthenticator extends AbstractAccountAuthenticator {
    public static final String ACCOUNT_TYPE = "io.pello.android";
    public static final String ACCOUNT_NAME = "DUMMY ACCOUNT";
    private Activity activity;
    private IntentFilter.AuthorityEntry authority; // check type

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s1, String[] strings, Bundle bundle) throws NetworkErrorException {
        AccountManager manager = AccountManager.get(activity);
        final Account account = new Account(ACCOUNT_NAME,ACCOUNT_TYPE);
      /*  manager.addAccountExplicitly(account, null, null);
        ContentResolver.setIsSyncable(account, authority, 1);
        ContentResolver.setSyncAutomatically(account, authority, true);*/
        return null;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    public DummyAuthenticator(Context context) {
        super(context);
    }

    @Override
    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
        return super.getAccountRemovalAllowed(response, account);
    }

    @Override
    public Bundle getAccountCredentialsForCloning(AccountAuthenticatorResponse response, Account account) throws NetworkErrorException {
        return super.getAccountCredentialsForCloning(response, account);
    }

    @Override
    public Bundle addAccountFromCredentials(AccountAuthenticatorResponse response, Account account, Bundle accountCredentials) throws NetworkErrorException {
        return super.addAccountFromCredentials(response, account, accountCredentials);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
