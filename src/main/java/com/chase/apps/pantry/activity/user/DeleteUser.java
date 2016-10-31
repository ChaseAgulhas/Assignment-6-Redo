package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;

public class DeleteUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
    }

    public void deleteByID(View v) {
        String userID = ((EditText) findViewById(R.id.editUserID)).getText().toString();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(this.getApplicationContext());
        User user = new User.Builder()
                .userID(userID)
                .build();
        userRepository.delete(user);
    }

    public void deleteAll(View v) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl(this.getApplicationContext());
        int deleted = userRepository.deleteAll();

        if (deleted > 0) {
            Toast.makeText(DeleteUser.this, "Users deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DeleteUser.this, "No users to delete", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, UserMenu.class);
        startActivity(intent);
    }

}
