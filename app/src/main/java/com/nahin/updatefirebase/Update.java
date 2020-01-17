package com.nahin.updatefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {

    DatabaseReference requestRf;
   // FirebaseDatabase database;

    TextView t1,t2,t3,t4,t5;
    Button button,update;
    EditText in;

    String text1 ;
    String text2 ;
    String text3 ;
    String text5 ;
    String text4 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();

        update = (Button)findViewById(R.id.updateID);

        requestRf = FirebaseDatabase.getInstance().getReference().child("StudentList").child("16-1-2020").child("123");

        t1 = (TextView)findViewById(R.id.text1);
        t2 = (TextView)findViewById(R.id.text2);
        t3 = (TextView)findViewById(R.id.text3);
        t4 = (TextView)findViewById(R.id.text4);
        t5 = (TextView)findViewById(R.id.text5);

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestRf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        text1 = dataSnapshot.child("date").getValue().toString();
                        text2 = dataSnapshot.child("myName").getValue().toString();
                        text3 = dataSnapshot.child("mystatus").getValue().toString();
                        text5 = dataSnapshot.child("phoneNumber").getValue().toString();
                        text4 = dataSnapshot.child("time").getValue().toString();


                        t1.setText(text1);
                        t2.setText(text2);
                        t3.setText(text3);
                        t4.setText(text4);
                        t5.setText(text5);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = (EditText)findViewById(R.id.input);

                String getInput =in.getText().toString();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference= firebaseDatabase.getReference("StudentList");

                reference.child("16-1-2020").child("123").child("mystatus").setValue( getInput);


            }
        });


    }


}
