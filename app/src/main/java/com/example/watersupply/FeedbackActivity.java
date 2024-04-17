package com.example.watersupply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FeedbackActivity extends AppCompatActivity {

    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        globalPreference = new GlobalPreference(this);

        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        String url = "http://"+ ip +"/water_supply/api/feedback.php?uid="+uid;

        WebView view = (WebView) findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setWebViewClient(new WebViewClient());


    }
}