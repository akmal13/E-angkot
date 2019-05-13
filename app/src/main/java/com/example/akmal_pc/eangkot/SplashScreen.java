package com.example.akmal_pc.eangkot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashScreen extends AppCompatActivity {
    private static int Splashtime = 3300;
    private FusedLocationProviderClient client;
    private static final ActivityInfo ActivityInfo = new ActivityInfo();

    public static double Lathasil;
    public static double Longhasil;

    public static String a;
    public static String b;
    public static String c;
    public static String d;
    public static String e;

    public SplashScreen(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);


        client = LocationServices.getFusedLocationProviderClient(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Logintent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(Logintent);
                finish();
            }
        }, Splashtime);

        requestPermission();

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(SplashScreen.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    Lathasil = location.getLatitude();
                    Longhasil = location.getLongitude();
                    getUpdatedClock(Lathasil,Longhasil);
                }
            }
        });

    }
    public void requestPermission(){
        ActivityCompat.requestPermissions(SplashScreen.this,new String[]{ACCESS_FINE_LOCATION},1);
    }

    public String JamSubuh() {
        return a ;
    }

    public String JamZuhur() {
        return b ;
    }

    public String JamAshar() {
        return c ;
    }

    public String JamMaghrib() {
        return d ;
    }

    public String JamIsya() {
        return e ;
    }

    public void setA(String pengA) {
        a = pengA;
    }

    public void setB(String pengB) {
        b = pengB;
    }

    public void setC(String pengC) {
        c = pengC;
    }

    public void setD(String pengD) {
        d = pengD;
    }

    public void setE(String pengE) {
        e = pengE;
    }

    public void getUpdatedClock(double Lat,double Long){

        String Longpake = String.valueOf(Long);
        String Latpake = String.valueOf(Lat);
        String URL = "https://api.pray.zone/v2/times/today.json?longitude="+Longpake+"&latitude="+Latpake;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest JOReq = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject J0 = response;

                JSONObject resultJO = new JSONObject();


                try{

                    JSONObject items = J0.getJSONObject("results");

                    JSONArray items2 = items.getJSONArray("datetime");
                    JSONObject items3 = items2.getJSONObject(0);
                    resultJO = items3.getJSONObject("times");


                }catch (JSONException e){
                    Log.d("This",e.getMessage());}

                    if(resultJO != null){
                    try{

                        String waktusubuh = resultJO.getString("Fajr") + " AM";
                        String waktuzuhur = resultJO.getString("Dhuhr") + " AM";
                        String waktuashar = resultJO.getString("Asr") + " AM";
                        String waktumaghrib = resultJO.getString("Maghrib") + " AM";
                        String waktuisha = resultJO.getString("Isha") + " AM";

                        double akanwaktusubuh = Double.valueOf(resultJO.getString("Fajr").replace(":","."));
                        double akanwaktuzuhur = Double.valueOf(resultJO.getString("Dhuhr").replace(":","."));
                        double akanwaktuashar = Double.valueOf(resultJO.getString("Asr").replace(":","."));
                        double akanwaktumaghrib = Double.valueOf(resultJO.getString("Maghrib").replace(":","."));
                        double akanwaktuisha = Double.valueOf(resultJO.getString("Isha").replace(":","."));


                        if(akanwaktusubuh > 12){
                            akanwaktusubuh = akanwaktusubuh - 12;
                            akanwaktusubuh = Double.parseDouble(new DecimalFormat("##.##").format(akanwaktusubuh));
                            waktusubuh = String.valueOf(akanwaktusubuh).replace(".",":") + " PM";
                        }

                        if(akanwaktuzuhur > 12){
                            akanwaktuzuhur = akanwaktuzuhur - 12;
                            akanwaktuzuhur = Double.parseDouble(new DecimalFormat("##.##").format(akanwaktuzuhur));
                            waktuzuhur = String.valueOf(akanwaktuzuhur).replace(".",":") + " PM";
                        }
                        if(akanwaktuashar > 12){
                            akanwaktuashar = akanwaktuashar-12;
                            akanwaktuashar = Double.parseDouble(new DecimalFormat("##.##").format(akanwaktuashar));
                            waktuashar = String.valueOf(akanwaktuashar).replace(".",":") + " PM";
                        }

                        if(akanwaktumaghrib > 12){
                            akanwaktumaghrib = akanwaktumaghrib - 12 ;
                            akanwaktumaghrib = Double.parseDouble(new DecimalFormat("##.##").format(akanwaktumaghrib));
                            waktumaghrib = String.valueOf(akanwaktumaghrib).replace(".", ":")+ " PM";
                        }

                        if(akanwaktuisha > 12){
                            akanwaktuisha = akanwaktuisha - 12 ;
                            akanwaktuisha = Double.parseDouble(new DecimalFormat("##.##").format(akanwaktuisha));
                            waktuisha = String.valueOf(akanwaktuisha).replace(".",":")+ " PM";
                        }

                        setA(resultJO.getString("Fajr"));
                        setB(resultJO.getString("Dhuhr"));
                        setC(resultJO.getString("Asr"));
                        setD(resultJO.getString("Maghrib"));
                        setE(resultJO.getString("Isha"));

                    }catch (JSONException e){Log.e("Splashscreen",e.getMessage());}

                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(JOReq);

    }
    public static double getLat(){return Lathasil;}
    public static double getLong(){return Longhasil;}

}
