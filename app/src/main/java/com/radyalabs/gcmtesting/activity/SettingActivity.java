package com.radyalabs.gcmtesting.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.radyalabs.gcmtesting.R;
import com.radyalabs.gcmtesting.app.util.GlobalVariable;

public class SettingActivity extends AppCompatActivity {

    private EditText edt_base_api;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        edt_base_api = (EditText) findViewById(R.id.edt_base_api);
        btn_save = (Button) findViewById(R.id.btn_save);

        edt_base_api.setText(GlobalVariable.getBaseAPI(this));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseApi = edt_base_api.getText().toString();
                if (baseApi == null || baseApi.length() == 0){
                    Toast.makeText(SettingActivity.this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    GlobalVariable.saveBaseAPI(getApplicationContext(), baseApi);
                    GlobalVariable.saveIsRegisterPush(getApplicationContext(), false);

                    Toast.makeText(SettingActivity.this, "Base API sukses diubah!", Toast.LENGTH_SHORT).show();
                    edt_base_api.setText(GlobalVariable.getBaseAPI(getApplicationContext()));
                }
            }
        });

    }

}
