package com.dnc.crawler.ui.home;

import android.webkit.WebView;

import com.dnc.crawler.ui.home.webview.WebviewContext;

public class MyThread implements Runnable {
//    private static WebView webView;
    String jsCode;
    public MyThread(String j){
//        this.webView = w;
        jsCode = j;
    }

    public void run()
    {
        WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
    }
}
