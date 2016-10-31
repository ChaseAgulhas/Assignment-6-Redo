package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chase.apps.pantry.R;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    public void addUser(View v){
        Intent intent = new Intent(this, AddUser.class);
        startActivity(intent);
    }

    public void updateUser(View v)
    {
        Intent intent = new Intent(this, UpdateUser.class);
        startActivity(intent);
    }

    public void viewUsers(View v){
        Intent intent = new Intent(this, ViewUser.class);
        startActivity(intent);
    }

    public void deleteUsers(View v){

        Intent intent = new Intent(this, DeleteUser.class);
        startActivity(intent);

    }

}
