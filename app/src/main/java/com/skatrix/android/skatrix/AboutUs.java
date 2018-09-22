package com.skatrix.android.skatrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import android.content.DialogInterface;
import android.widget.Toast;


public class AboutUs extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    AlertDialog.Builder builder;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_aboutus);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_aboutus);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            finish();
                            //starting login activity
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            break;
                        case R.id.nav_feedback:
                            finish();
                            //starting login activity
                            startActivity(new Intent(getApplicationContext(), Feedback.class));
                            break;

                        case R.id.nav_logout:
                            builder = new AlertDialog.Builder(AboutUs.this);


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