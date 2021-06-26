package com.example.plantsproject.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.plantsproject.R;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*ПОИСК РАСТЕНИЯ В ВИКИПЕДИИ*/

public class WebInfoActivity extends AppCompatActivity {
    WebView webView;
    CollapsingToolbarLayout toolbarLayout;
    Plant plant;
    long plantID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Настройка WebView
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        plantID = getIntent().getLongExtra("plantID", 0);
        DBPlants db = new DBPlants(this);
        plant = db.select(plantID);

        //Кнопка назад
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(WebInfoActivity.this, MainActivity.class);
            //i.putExtra("plant", plant);
            startActivity(i);

        });

        //Устанавливается название растения в toolbar
        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(plant.getName());

        //Открывается ссылка
        webView.loadUrl("https://ru.wikipedia.org/wiki/"+ plant.getName());
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
