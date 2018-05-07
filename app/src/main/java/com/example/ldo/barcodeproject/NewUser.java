package com.example.ldo.barcodeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class NewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
    }

    public void DoNewUser(View view)
    {
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);
        CheckBox is_admin = (CheckBox) findViewById(R.id.is_admin);
        if (AdminWindow.users == null) AdminWindow.users = new ArrayList<>();
        User new_user = new User(login.getText().toString(),password.getText().toString());
        new_user.setAdmin(is_admin.isChecked());
        AdminWindow.users.add(new_user);
        finish();
    }

}
