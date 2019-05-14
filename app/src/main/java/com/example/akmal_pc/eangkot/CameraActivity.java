package com.example.akmal_pc.eangkot;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    DatabaseReference databaseride;
    Ride rid;
    public static int max;
    private FusedLocationProviderClient client;
    public static double Lathasil;
    public static double Longhasil;
    public static double hasilmeter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        databaseride = FirebaseDatabase.getInstance().getReference("Ride");

        client = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        client.getLastLocation().addOnSuccessListener(CameraActivity.this, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    Lathasil = location.getLatitude();
                    Longhasil = location.getLongitude();

                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        // Do something with the result here
        //Log.v("TAG", rawResult.getText()); // Prints scan results
        //Log.v("TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setMessage(rawResult.getText());
        //AlertDialog alert1 = builder.create();
        //alert1.show();

        DatabaseReference ref = databaseride.child(rawResult.getText());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rid = dataSnapshot.getValue(Ride.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        if(rid == null){


        }
        max = max+1;
        if(max == 1){
            Intent intent = new Intent(CameraActivity.this, RideInfo.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(CameraActivity.this, PaymentInfo.class);
            hasilmeter = hitungmeter(SplashScreen.getLat(),SplashScreen.getLong(),Lathasil,Longhasil);
            startActivity(intent);
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    public double hitungmeter(double lat1,double long1,double lat2,double long2){
        return Haversine.distance(lat1,long1,lat2,long2);
    }

    public static double getHasilmeter() {
        return hasilmeter;
    }
}
