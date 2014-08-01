/*
    Author:     Ryan Wahle

    Project:    Get Me Home

    Package:    com.ryanwahle.getmehome

    File:       WebAppInterface.java

    Purpose:    This is the starting class that sets up the WebView
                and loads the HTML interface into the application.

                The graphics for this app were downloaded as free
                icons from findicons.com

                The CSS framework used in this app is from purecss.io

 */

package com.ryanwahle.getmehome;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView myWebView = (WebView) findViewById(R.id.webview);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.clearCache(true);

        myWebView.loadUrl("file:///android_asset/index.html");

    }

}
