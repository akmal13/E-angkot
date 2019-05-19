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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    Ride rid;
    private FusedLocationProviderClient client;
    public static double Lathasil;
    public static double Longhasil;
    public static double hasilmeter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    private static String hasilscan;
    public static String namaDriver;
    public static String noDriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        myRef = mFirebaseDatabase.getReference("Ride");
        myRef1 = mFirebaseDatabase.getReference("Driver");
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
    public void handleResult(final Result rawResult) {


        DatabaseReference ref = myRef.child(rawResult.getText());
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

        if(hasilscan != null){
            if(hasilscan == rawResult.getText()){
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(CameraActivity.this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            hasilmeter=hitungmeter(Lathasil,Longhasil,location.getLatitude(),location.getLongitude());

                        }
                    }
                });
                CameraActivity.this.startActivity(new Intent(CameraActivity.this,PaymentInfo.class));
            }
            else {
                Log.d("Input qr2","QR berbeda");
            }
        }
        else {
            hasilscan = rawResult.getText();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    showData(dataSnapshot,rawResult.getText());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            CameraActivity.this.startActivity(new Intent(CameraActivity.this,RideInfo.class));
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

    private void showData(DataSnapshot dataSnapshot, String id) {

            String userID = id;
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Driver uInfo = new Driver();
                uInfo.setName(ds.child(userID).getValue(Driver.class).getName()); //set the name
                uInfo.setLongitude(ds.child(userID).getValue(Driver.class).getLongitude()); //set the email
                uInfo.setLatitude(ds.child(userID).getValue(Driver.class).getLatitude());
                uInfo.setNoAngkot(ds.child(userID).getValue(Driver.class).getNoAngkot());//set the phone_num

                namaDriver = uInfo.getName();
                noDriver = uInfo.getNoAngkot();

            }

    }

    public static String getNamaDriver() {
        return namaDriver;
    }

    public static String getNoDriver() {
        return noDriver;
    }
}
