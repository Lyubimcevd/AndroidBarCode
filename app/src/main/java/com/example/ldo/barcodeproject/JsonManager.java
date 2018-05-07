package com.example.ldo.barcodeproject;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import android.content.Context;
import java.io.FileOutputStream;


public class JsonManager {

    static boolean IsUser(User p_user,Context p_con) {

        InputStreamReader ISR = null;
        FileInputStream FIS = null;
        try{
            FIS = p_con.openFileInput("users.json");
            ISR = new InputStreamReader(FIS);
            Gson gson = new Gson();
            Users users = gson.fromJson(ISR,Users.class);
            for (int i = 0; i < users.getUsers().size(); i++) {
                if (users.getUsers().get(i).getLogin().equals(p_user.getLogin())
                        &&users.getUsers().get(i).getPassword().equals(p_user.getPassword()))
                {
                    p_user.setAdmin(users.getUsers().get(i).isAdmin());
                    return true;
                }
            }
            return false;
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        finally {
            if (ISR != null) {
                try {
                    ISR.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (FIS != null) {
                try {
                    FIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    static boolean SaveUsers( List<User> p_users,Context p_con) {

        Gson gson = new Gson();
        Users users = new Users();
        users.setUsers(p_users);
        String jsonString = gson.toJson(users);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = p_con.openFileOutput("users.json", Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    static List<User> OpenUsers(Context p_con) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = p_con.openFileInput("users.json");
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            Users users = gson.fromJson(streamReader, Users.class);
            return users.getUsers();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

class Users {
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
