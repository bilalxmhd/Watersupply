package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity {

    private String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");


        WebView view = (WebView) findViewById(R.id.mapWebView);

        //Toast.makeText(this, latitude+"latitude\n"+longitude, Toast.LENGTH_SHORT).show();

       String url = "https://www.google.es/maps/dir//"+latitude+","+longitude;


        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setWebViewClient(new WebViewClient());
    }
}