package com.example.akmal_pc.eangkot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentInfo extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    private static int nilaitarif;
    public static Tarif nitar = new Tarif();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.payment_info);
        try{
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Driver");
        myRef1 = mFirebaseDatabase.getReference("Tarif");
        }catch (NullPointerException e){Log.e("Membaca Database :","Database tidak ditemukan/telah dihapus");}

        Button btnmenu= findViewById(R.id.Buttonkemenu);
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView meter = findViewById(R.id.banyakmeter);
        meter.setText(String.valueOf(CameraActivity.getHasilmeter()));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try{
        TextView bayar = findViewById(R.id.nilaibayar);
        bayar.setText(String.valueOf(CameraActivity.getHasilmeter()*nitar.getNilaitarif()));

        TextView tar = findViewById(R.id.nilaitarif);
        tar.setText(String.valueOf(nitar.getNilaitarif()));
        }catch (NullPointerException e){Log.e("Membaca Database :","Database tidak ditemukan/telah dihapus");}

    }

    private void showData(DataSnapshot dataSnapshot) {
        for (int i = 0; i < 100 ; i++) {
            String userID = "driver"+ i;
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                try{
                    Driver uInfo = new Driver();
                    uInfo.setName(ds.child(userID).getValue(Driver.class).getName()); //set the name
                    for(DataSnapshot bg : dataSnapshot.getChildren()){
                        nitar.setNilaitarif(bg.child(uInfo.getName()).getValue(Tarif.class).getNilaitarif());
                    }
                }catch (NullPointerException e){Log.e("Membaca Database :","Database tidak ditemukan/telah dihapus");}

            }
        }
    }


}
