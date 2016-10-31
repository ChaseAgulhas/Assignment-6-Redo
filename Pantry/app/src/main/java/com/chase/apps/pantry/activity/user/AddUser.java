package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.activity.MainActivity;
import com.chase.apps.pantry.domain.user.User;

public class AddUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    public void previewUser(View v){
        Intent intent = new Intent(this, PreviewUser.class);

        String name = ((EditText) findViewById(R.id.editName)).getText().toString();
        String surname = ((EditText) findViewById(R.id.editSurname)).getText().toString();
        String dateOfBirth = ((EditText) findViewById(R.id.editDOB)).getText().toString();
        String email = ((EditText) findViewById(R.id.editEmail)).getText().toString();

        User user = new User.Builder().name(name)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .build();

        intent.putExtra("user", user);
        startActivity(intent);
    }


    public void returnHome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
