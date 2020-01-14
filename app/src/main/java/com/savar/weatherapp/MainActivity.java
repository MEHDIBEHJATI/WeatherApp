package com.savar.weatherapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


   public DrawerLayout drawer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        final TextView txtInt = findViewById(R.id.txtInt);


//        Recycler_Start
        RecyclerView recycler = findViewById(R.id.recycler);

        RecyclerAdapter adapter = new RecyclerAdapter();
        recycler.setAdapter(adapter);

        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
//        Recycler_End


//        Thread_Start

        new Thread(new Runnable() {
            @Override
            public void run() {


//        Http_Start
                try {
                    URL obj = new URL("https://weather-ydn-yql.media.yahoo.com/forecastrss?location=sunnyvale,ca&format=json");

                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = con.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));

                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }


                        System.out.println(response.toString());

                        JSONObject object = new JSONObject(response.toString());
                        String result = object.getString("location");

                        JSONObject object1 = new JSONObject(result);
                        String result1 = object.getString("country");


                        final TextView txtInt = findViewById(R.id.txtInt);
                        txtInt.setText(result1);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//        Http_End

            }
        }).start();


//        Thread_Http_End
    }

//        MENU_START
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
//    MENU_END
}

