package com.example.testtmdb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.testtmdb.R;

public class VideoWebViewActivity extends AppCompatActivity {

    private WebView webView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web_view);
        webView = findViewById(R.id.video_web_view);
        intent = getIntent();
        if (intent != null)
        {
            String key = intent.getStringExtra("VIDEO_KEY");
            key = "https://www.youtube.com/watch?v=" + key;
            webView.loadUrl(key);
            finish();
        }
    }
}