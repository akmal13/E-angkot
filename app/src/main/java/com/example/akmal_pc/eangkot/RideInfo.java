package com.example.akmal_pc.eangkot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class RideInfo extends AppCompatActivity {

    public RideInfo() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.ride_info);

        Button btnkonf= findViewById(R.id.Buttonkonfirmasi);
        btnkonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RideInfo.this, CameraActivity.class);

                startActivity(intent);
            }
        });
        TextView namdriv = findViewById(R.id.inputnamadriver);
        namdriv.setText(CameraActivity.getNamaDriver());

        TextView nodriv = findViewById(R.id.inputnodriver);
        nodriv.setText(CameraActivity.getNoDriver());
    }


}
