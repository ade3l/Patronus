package com.example.contacttracer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String username,pwd;
    TextView username_Text,password_Text,create;

    public void login(View view){

        username_Text=(TextView) findViewById(R.id.username);
        password_Text=(TextView)findViewById(R.id.password);
        username=username_Text.getText().toString();
        pwd=password_Text.getText().toString();
        ParseUser.logInInBackground(username, pwd, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null ){
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    ParseQuery<ParseObject> query=ParseQuery.getQuery("data");
                    query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if(objects.size()==0 || objects==null){
                                Intent intent = new Intent(getApplicationContext(),details.class);
                                TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(intent).startActivities();
                            }
                            else{
                                Intent intent = new Intent(getApplicationContext(),userInterface.class);
                                TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(intent).startActivities();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void toSignup(){
        Intent intent = new Intent(this,signup.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if(ParseUser.getCurrentUser()!=null){
            ParseQuery<ParseObject> query=ParseQuery.getQuery("data");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()==0 || objects==null){
                        Intent intent = new Intent(getApplicationContext(),details.class);
                        TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(intent).startActivities();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),userInterface.class);
                        TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(intent).startActivities();
                    }
                }
            });
        }
        else{
            setContentView(R.layout.activity_main);
            create=findViewById(R.id.create);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toSignup();
                }
            });
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }
}