package com.dnc.crawler.ui.home.webview;

import android.webkit.WebView;

public class WebviewContext {
    static WebviewContext wc = null;
    private static WebView webView;

    static{
        wc = new WebviewContext();
    }
    private WebviewContext(){
        //do not anything
    }

    public static void setWebView(WebView wv){
        webView = wv;
    }

    public static WebView getWebview(){
        return webView;
    }

    public static void callJs(String jsCode){

        webView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((int)(Math.random()*1000)+1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
//                WebviewContext.getWebview().clearCache(true);
                WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
                try {
                    Thread.sleep((int)(Math.random()*1000)+1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
