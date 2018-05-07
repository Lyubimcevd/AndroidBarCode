package com.example.ldo.barcodeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void SaveSettings(View view)
    {
        EditText sleeptime = (EditText) findViewById(R.id.sleep_time);
        CurrentSystemStatus.SleepTime = Integer.parseInt(sleeptime.getText().toString());
        RadioButton usb = (RadioButton) findViewById(R.id.usb);
        CurrentSystemStatus.IsUSB = usb.isChecked();
        finish();
    }
}
