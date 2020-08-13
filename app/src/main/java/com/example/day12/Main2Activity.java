package com.example.day12;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    private WebView mWe;
    private long startTime;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startTime = System.currentTimeMillis();
        initView();
    }

    private void initView() {
        mWe = (WebView) findViewById(R.id.we);
        intent = getIntent();
        String name = intent.getStringExtra("name");
        mWe.loadUrl(name);
        mWe.setWebViewClient(new WebViewClient());


    }
    @Override
    public void onBackPressed() {
        long endtime = System.currentTimeMillis();
        long time = endtime -startTime;
        intent.putExtra("time",time);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}
