package com.skatrix.android.skatrix;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.CaptureActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ProfileActivity extends AppCompatActivity  {

    //firebase auth object
   public String uniq;
    private FirebaseAuth firebaseAuth;
    AlertDialog.Builder builder;
    private Button scan_btn;
    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference mDatabase;
    private Button aboutbutton;
    private Button feedbutton;

// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
//        aboutbutton = (Button) findViewById(R.id.nav_aboutus);
//        feedbutton = (Button) findViewById(R.id.nav_feedback);
//        aboutbutton.setOnClickListener((View.OnClickListener) this);
//        feedbutton.setOnClickListener((View.OnClickListener) this);
        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                SkateboardKey post = dataSnapshot.getValue(SkateboardKey.class);
                uniq=post.Skateboard1;
                Log.w("Helloooooo", String.valueOf(uniq));
                Log.w("fuck u",String.valueOf(uniq));
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("fuck u" ,"loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference("");
        mDatabase.addValueEventListener(postListener);
//        //initializing views
//        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
//        buttonLogout = (Button) findViewById(R.id.buttonLogout);
//
//        //displaying logged in user name
//        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        //buttonLogout.setOnClickListener(this);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan the Skateboard QR-Code For Ride");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

                if(new String(uniq).equals(result.getContents()) ) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Feedback.class));
                }
                else
                {
                    Log.w("hiiiiiiiiiii",String.valueOf(uniq));
                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick(View view) {
//        if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()) {

                        case R.id.nav_feedback:
                            finish();
                            //starting login activity
                            startActivity(new Intent(getApplicationContext(), Feedback.class));
                            break;
                        case R.id.nav_aboutus:
                            finish();
                            //starting login activity
                            startActivity(new Intent(getApplicationContext(), AboutUs.class));
                            break;

                        case R.id.nav_logout:
                            builder = new AlertDialog.Builder(ProfileActivity.this);


                            //Setting message manually and performing action on button click
                            builder.setMessage("Do you want to LogOut ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            firebaseAuth.signOut();
                                            //closing activity
                                            finish();
                                            //starting login activity
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            Toast.makeText(getApplicationContext(),"Successfully loggedOut",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("AlertDialogExample");
                            alert.show();
                    }



                    return true;
                }
            };




}