package com.example.pkvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;

import wendu.dsbridge.DWebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DWebView webView = findViewById(R.id.webview);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        DWebView.setWebContentsDebuggingEnabled(true);
        webView.addJavascriptObject(new VideoApi(this),"");
        webView.loadUrl("file:///android_asset/video.html");

    }

    @Override
    public void onBackPressed() {
        if(!VideoApi.onBackPressed()){
            super.onBackPressed();
        }
    }
}