package com.chase.apps.pantry.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chase.apps.pantry.R;
import com.chase.apps.pantry.activity.MainActivity;
import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;
import com.chase.apps.pantry.repository.user.UserRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewUser extends AppCompatActivity {


    ArrayAdapter adapter;
    ListView listUserView;
    String[] names;
    Set<User> userSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        UserRepository userRepository = new UserRepositoryImpl(this.getApplicationContext());

        userSet = new HashSet<>();
        userSet = userRepository.findAll();

        Iterator<User> userIterator = userSet.iterator();

        if(userSet.size()>0)
        {
            names = new String[userSet.size()];
            int i = 0;

            while(userIterator.hasNext())
            {
                names[i] = userIterator.next().toString();
                i++;
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
            listUserView = (ListView)findViewById(R.id.listUserView);
            listUserView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(ViewUser.this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
