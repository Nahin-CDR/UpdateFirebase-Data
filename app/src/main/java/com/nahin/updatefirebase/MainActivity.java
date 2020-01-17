package com.nahin.updatefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    //code for time starts
    TextView myTime;
    //code for time ends
    FirebaseAuth mAuth;

    //code for date starts
    DatePickerDialog picker;
    EditText dateInput;
    //code for date ends

    Button submitButton,nextButton;
    int checkStatus;



    // private ValueEventListener eventListener; //new
    private FirebaseDatabase database = FirebaseDatabase.getInstance(); //new



    EditText phoneNumber,date,name;
    String dateTime;

    ListView listView;
    DatabaseReference databaseReference;
    List<SendData> studentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        listView = findViewById(R.id.studentListId);


        studentDataList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentList");





        codeForTime();

        codeForDatePicker();

        submitInformation();

        goNext();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        dateTime = dateformat.format(c.getTime());
       // currentDate.setText(dateTime);




    }

    private void goNext() {

        nextButton = (Button)findViewById(R.id.next_button_id);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  date = (EditText)findViewById( R.id.date_id );
                //                String getDate = date.getText().toString();
                //
                //                phoneNumber = (EditText)findViewById( R.id.phone_numberID );
                //                String getPhoneNumber = phoneNumber.getText().toString();
                //
                //
                //                DatabaseReference requestRf =  database.getReference("StudentList");
                //
                //
                //                requestRf.child(getDate).child(getPhoneNumber ).child("mystatus").setValue(1); //update data

                Intent intent = new Intent(getApplicationContext(),Update.class);
                startActivity(intent);
            }
        });



    }


    private void codeForDatePicker() {

        //Code for date starts


        dateInput =(EditText) findViewById(R.id.date_id);
        dateInput.setInputType(InputType.TYPE_NULL);

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //Code for date ends
    }

    private void codeForTime() {

        myTime = (TextView)findViewById( R.id.timeID );
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy \n hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        myTime.setText( datetime );


    }





    private void submitInformation() {

        submitButton = (Button)findViewById( R.id.submit_button_id );
        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkStatus = 0;

                phoneNumber = (EditText)findViewById( R.id.phone_numberID );
                String getPhoneNumber = phoneNumber.getText().toString();

                date = (EditText)findViewById( R.id.date_id );
                String getDate = date.getText().toString();

                name = (EditText)findViewById( R.id.nameID );
                String getName = name.getText().toString();

                myTime = (TextView)findViewById( R.id.timeID );
                String inTime = myTime.getText().toString();

                if(getPhoneNumber.length()!=0 && getDate.length()!=0 && getName.length()!=0){

                    DatabaseReference requestRf =  database.getReference("StudentList");

                    SendData sendData = new SendData(getName,getDate, getPhoneNumber,inTime,checkStatus);


                    requestRf.child(getDate).child( getPhoneNumber ).setValue(sendData); //update data


                    Toast.makeText(MainActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    date.setText("");
                    phoneNumber.setText("");
                    name.setText("");

                }
                else{

                    phoneNumber.setError("Phone Number field Empty !");
                    name.setError( "Name Field Empty !" );
                }
            }
        });

    }



}



