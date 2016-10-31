package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;

public class UpdateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


    }

    public void updateUser(View v)
    {
        String employeeID = ((EditText)findViewById(R.id.editUserID)).getText().toString();
        String name = ((EditText)findViewById(R.id.editName)).getText().toString();
        String surname = ((EditText)findViewById(R.id.editSurname)).getText().toString();
        String dateOfBirth = ((EditText)findViewById(R.id.editDOB)).getText().toString();
        String email = ((EditText)findViewById(R.id.editEmail)).getText().toString();

        User user = new User.Builder()
                .userID(employeeID)
                .name(name)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .build();

        UserRepositoryImpl userRepository = new UserRepositoryImpl(this.getApplicationContext());
        userRepository.update(user);

        Intent intent = new Intent(this, UserMenu.class);
        startActivity(intent);
    }
}
