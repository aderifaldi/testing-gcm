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
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.radyalabs.gcmtesting.R;
import com.radyalabs.gcmtesting.app.api.APIRegisterNotification;
import com.radyalabs.gcmtesting.app.api.APIUnRegisterNotification;
import com.radyalabs.gcmtesting.app.gcm.QuickstartPreferences;
import com.radyalabs.gcmtesting.app.gcm.RegistrationIntentService;
import com.radyalabs.gcmtesting.app.util.GlobalVariable;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isRegisteredToServer;
    private String deviceId;

    private TextView txt_is_registered_gcm, txt_is_registered_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        GlobalVariable.saveDeviceId(this, deviceId);

        txt_is_registered_gcm = (TextView) findViewById(R.id.txt_is_registered_gcm);
        txt_is_registered_server = (TextView) findViewById(R.id.txt_is_registered_server);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

                isRegisteredToServer = GlobalVariable.getIsRegisterPush(getApplicationContext());

                if (sentToken){

                    if (!isRegisteredToServer){
                        registerToken();
                        txt_is_registered_gcm.setText("Register to GCM Server Success");
                    }else {
                        txt_is_registered_gcm.setText("This Device Registered to GCM Server");
                        txt_is_registered_server.setText("This Device Registered to Ega Server");
                    }

                }

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
                GlobalVariable.getDeviceId(getApplicationContext()),
                GlobalVariable.getGCMToken(getApplicationContext())) {
            @Override
            public void onFinishRequest(boolean success, String returnItem) {
                if (success){

                    if (data.getStatus() == 1){
                        GlobalVariable.saveIsRegisterPush(getApplicationContext(), true);

                        txt_is_registered_server.setText(data.getMessage());

                    }

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

}
