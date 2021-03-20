package com.example.contacttracer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class userInterface extends AppCompatActivity {
    TextView details_text;
    public void logout(View view){
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(intent).startActivities();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
        ParseQuery<ParseObject>query=ParseQuery.getQuery("data");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
//        final String[] dataString = new String[1];
        String[] dataString = new String[1];
        final String[] statusData = new String[1];
        details_text=(TextView)findViewById(R.id.details);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for(ParseObject object :objects){
                    dataString[0] =object.getString("data");
                    statusData[0] =object.getString("Status");
                }
                try {
                    JSONObject data=new JSONObject(dataString[0]);
//                    JSONObject statusData=new JSONObject(statusString[0]);
//                    Log.i("mine",data.toString());

                    String room=data.getString("room");
                    String floor=data.getString("floor");
                    String block=data.getString("block");
                    String status=statusData[0];
                    String details="Room: "+room+"\n"+"floor: "+floor+"\n"+"Block: "+block +"\n"+"Status: "+status;
                    details_text.setText(details);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(userInterface.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}