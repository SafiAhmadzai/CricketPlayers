package app.com.besten.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.com.besten.R;

public class ActivityWeb extends AppCompatActivity {

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView myWebView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        int rank = intent.getIntExtra("rank", 0);
        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String title = "<div style=\"padding: 5%;\"><h2>" + name.toUpperCase() + "</h2>";
        String body = "<hr><small>Ranking: " + rank + "</small><p>" + content + "</p></div>";


        myWebView.loadDataWithBaseURL("", title + body, mimeType, encoding, "");


    }

}
