package com.chase.apps.pantry.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.activity.user.UserMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewUserMenu(View v)
    {
        Intent intent = new Intent(this, UserMenu.class);
        startActivity(intent);
    }
}
