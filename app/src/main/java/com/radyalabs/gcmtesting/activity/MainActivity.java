package com.radyalabs.gcmtesting.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.radyalabs.gcmtesting.R;
import com.radyalabs.gcmtesting.app.api.APIRegisterNotification;
import com.radyalabs.gcmtesting.app.api.APIUnRegisterNotification;
import com.radyalabs.gcmtesting.app.gcm.QuickstartPreferences;
import com.radyalabs.gcmtesting.app.gcm.RegistrationIntentService;
import com.radyalabs.gcmtesting.app.util.GlobalVariable;
import com.radyalabs.irfan.util.AppUtility;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isRegistered;
    private String deviceId;

    private SwitchCompat switch_push_notification;
    private boolean isNotificationOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch_push_notification = (SwitchCompat) findViewById(R.id.switch_push_notification);

        isNotificationOn = GlobalVariable.getIsRegisterPush(this);

        if (isNotificationOn){
            switch_push_notification.setChecked(true);

            isRegistered = GlobalVariable.getIsRegisterPush(getApplicationContext());
            if (!isRegistered){
                registerToken();
            }

        }else {
            switch_push_notification.setChecked(false);
        }

        switch_push_notification.setOnCheckedChangeListener(this);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        GlobalVariable.saveDeviceId(this, deviceId);
        AppUtility.logD("MainActivity", "device id : " + deviceId);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void registerToken(){
        APIRegisterNotification apiRegisterNotification = new APIRegisterNotification(getApplicationContext(),
                GlobalVariable.getGCMToken(getApplicationContext())) {
            @Override
            public void onFinishRequest(boolean success, String returnItem) {
                if (success){
                    GlobalVariable.saveIsRegisterPush(getApplicationContext(), true);
                    switch_push_notification.setChecked(true);
                }
            }
        };
        apiRegisterNotification.executeAjax();
    }

    private void unRegisterToken(){
        APIUnRegisterNotification apiRegisterNotification = new APIUnRegisterNotification(getApplicationContext(),
                GlobalVariable.getGCMToken(getApplicationContext())) {
            @Override
            public void onFinishRequest(boolean success, String returnItem) {
                if (success){
                    GlobalVariable.saveIsRegisterPush(getApplicationContext(), false);
                    switch_push_notification.setChecked(false);
                }
            }
        };
        apiRegisterNotification.executeAjax();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_push_notification:
                if (isChecked) {
                    registerToken();
                } else {
                    unRegisterToken();
                }
                break;
        }
    }
}
