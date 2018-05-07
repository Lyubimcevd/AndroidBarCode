package com.example.ldo.barcodeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class AdminWindow extends AppCompatActivity {

    static List<User> users;
    UserAdapter adapter;
    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_window);
        LV = (ListView) findViewById(R.id.user_list);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder b =  new AlertDialog.Builder(AdminWindow.this);
                b.setTitle("Удаление");
                b.setMessage("Удалить пользователя?");
                b.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        users.remove(LV.getAdapter().getItem(LV.getCheckedItemPosition()));
                        adapter.notifyDataSetChanged();
                    }
                });
                b.show();
            }
        });

        users = JsonManager.OpenUsers(this);
        if(users!=null){
            adapter = new UserAdapter(this, R.layout.list_item, users);
            LV.setAdapter(adapter);
        }
    }

    public void DoNewUser(View view)
    {
        Intent intent = new Intent(this, NewUser.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter == null) {
            if (users != null) {
                adapter = new UserAdapter(this, R.layout.list_item, users);
                LV.setAdapter(adapter);
            }
        } else adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        JsonManager.SaveUsers(AdminWindow.users,this);
    }

}



