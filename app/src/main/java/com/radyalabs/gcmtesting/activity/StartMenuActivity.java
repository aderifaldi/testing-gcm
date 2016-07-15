package com.radyalabs.gcmtesting.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.radyalabs.gcmtesting.R;
import com.radyalabs.gcmtesting.app.api.BaseApi;
import com.radyalabs.gcmtesting.app.util.GlobalVariable;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        boolean isBaseAPISetup = GlobalVariable.getIsBaseAPISetup(this);

        if (!isBaseAPISetup){
            GlobalVariable.saveBaseAPI(this, BaseApi.BASE_URL_PUBLIC);
            GlobalVariable.saveIsBaseAPISetup(this, true);
        }

    }

    public void start(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

}
