package com.skatrix.android.skatrix;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.CaptureActivity;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
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
    private FirebaseAuth firebaseAuth;
    private Button scan_btn;
    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

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
                mDatabase = FirebaseDatabase.getInstance().getReference("path");
                finish();
                startActivity(new Intent(this, ControlsActivity.class));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
//    @Override
//    public void onClick(View view) {
//        //if logout is pressed
////        if(view == buttonLogout){
////            //logging out the user
////            firebaseAuth.signOut();
////            //closing activity
////            finish();
////            //starting login activity
////            startActivity(new Intent(this, LoginActivity.class));



}

