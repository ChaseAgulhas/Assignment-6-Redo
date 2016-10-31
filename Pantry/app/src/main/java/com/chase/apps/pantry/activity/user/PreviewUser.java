package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.activity.MainActivity;
import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;

public class PreviewUser extends AppCompatActivity {

    TextView name;
    TextView surname;
    TextView dob;
    TextView email;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_user);

        Bundle extras = getIntent().getExtras();
        user = (User)extras.getSerializable("user");

        name = ((TextView)findViewById(R.id.txtName));
        name.setText(user.getName());

        surname = ((TextView)findViewById(R.id.txtSurname));
        surname.setText(user.getSurname());

        dob = ((TextView)findViewById(R.id.txtDOB));
        dob.setText(user.getDateOfBirth());

        email = ((TextView)findViewById(R.id.txtEmail));
        email.setText(user.getUserEmail());

    }

    public void addToDatabase(View v){
        UserRepositoryImpl userRepository = new UserRepositoryImpl(this.getApplicationContext());

        User user = new User.Builder()
                .name(name.getText().toString())
                .surname(surname.getText().toString())
                .dateOfBirth(dob.getText().toString())
                .email(email.getText().toString())
                .build();
        userRepository.save(user);
        //add a toast
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
