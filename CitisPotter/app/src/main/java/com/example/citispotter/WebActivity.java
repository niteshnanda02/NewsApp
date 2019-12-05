package com.example.citispotter;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity);
        final String url=getIntent().getStringExtra("url");
        WebView webView=findViewById(R.id.activty_webView);
        webView.loadUrl(url);
    }
}
