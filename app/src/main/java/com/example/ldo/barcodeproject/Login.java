package com.example.ldo.barcodeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import android.view.View;
import java.io.IOException;
import java.util.List;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class Login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void EnterInSystem(View view)
    {
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.password);
        User cur_user = new User(login.getText().toString(),password.getText().toString());
        if (JsonManager.IsUser(cur_user,this)||(cur_user.getLogin().equals("ldo")
                &&cur_user.getPassword().equals("1")))
        {
            if (cur_user.getLogin().equals("ldo")) cur_user.setAdmin(true);
            CurrentSystemStatus.current_user = cur_user;
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
        else
        {
            new AlertDialog.Builder(this)
            .setTitle("Ошибка!")
            .setMessage("Неверный логин или пароль")
            .setPositiveButton("ОК",null)
            .show();
        }
    }




}
