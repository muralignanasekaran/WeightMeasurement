package com.example.dell.smartbasket;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    public TextView textView2;

    private ListView mUserList;

    private ArrayList<Integer> mUserNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("sensor data");
        //textView2= (TextView) findViewById(R.id.textView2);
        mUserList = (ListView) findViewById(R.id.user_list);

        final ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,mUserNames);
        mUserList.setAdapter(arrayAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getValue().toString();
                //textView2.setText("Name: "+name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //String value = dataSnapshot.getValue(String.class);

                int a = dataSnapshot.getValue(Integer.class);
                //int a = Integer.parseInt(value);
                //String z=textView2.getText().toString();
                //z=z+a;
                mUserNames.add(a);
                arrayAdapter.notifyDataSetChanged();

                //Toast.makeText(getApplicationContext(),"a = "+a,Toast.LENGTH_LONG).show();
                //textView2.setText(textView2.getText()+"\n"+a);
                if(a<200)
                {
                    addNotification();
                    //Toast.makeText(getApplicationContext(),"value < 10 ",Toast.LENGTH_LONG).show();
                }
                /*else if (a>=10)
                    {
                        addNotification();
                        Toast.makeText(getApplicationContext(),"value > 10",Toast.LENGTH_LONG).show();}*/




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void addNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Smart Basket")
        .setContentText("Order Soon, the stock is gonna to run out!");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());


    }
}
