package com.dnc.crawler.ui.home.webview;

import android.util.Log;
import android.webkit.WebView;

import com.dnc.crawler.ui.home.MyThread;

import java.util.Random;



public class WebviewContext {
    static WebviewContext wc = null;
    private static WebView webView;
    private static boolean senarioOneEnded = false;
    private static boolean senarioTwoEnded = false;
    private static int cnt = 0;

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

    public static void setSenarioOneState(boolean isEnded) {
        senarioOneEnded = isEnded;
    }
    public static void setSenarioTwoState(boolean isEnded) {
        senarioTwoEnded = isEnded;
    }
    public static boolean isSenarioOneEnded() {
        return senarioOneEnded;
    }
    public static boolean isSenarioTwoEnded() {
        return senarioTwoEnded;
    }

    public static int getCnt() {
        return cnt;
    }

    public static void cntUp(){
        cnt++;
    }



    public static void goHome(int delay_sec){
        for(int i=0; i<delay_sec/2; i++){
            scrollMove();
            try {
                Thread.sleep((int)(Math.random()*2000) + 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        webView.post(new Runnable() {
            @Override
            public void run() {
                WebviewContext.getWebview().loadUrl("https://www.naver.com");
            }
        });
    }


    public static void callJs(String jsCode){
        new Thread(() -> {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
                }
            });
        }).start();
    }
//    public static void callJs(String jsCode, int time_s){
//        webView.post(new Runnable() {
//            @Override
//            public void run() {
//                WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
//            }
//        });
//
////        int delay_time = (int) (Math.random() * 10 * 1000) + 1000;
////        for(int i=0; i<time_s/2; i++){
////            scrollMove();
////            try {
////                Thread.sleep((int)(Math.random()*2000) + 1000);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//
//    }
    public static synchronized void moveNdelay(int time_s) {
        for(int i=0; i<time_s/2; i++){

            new Thread(() -> {
                try {
                    Thread.sleep((int)(Math.random()*2000) + 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    public static synchronized void delay(int time_s) {
//        for(int i=0; i<time_s/2; i++){
//            new Thread(() -> {
//                try {
//                    Thread.sleep((int)(Math.random()*2000) + 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
        try {
//            Thread.sleep((int)(Math.random()*2000) + 1000);
            Thread.sleep((int)(time_s*1000));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void scrollMove(){
        webView.post(new Runnable() {
            @Override
            public void run() {
                Random randomGenerator = new Random();
                double upOrDown = randomGenerator.nextDouble();
                if(upOrDown < 0.5) {
                    WebviewContext.getWebview().flingScroll(0, (int)(500 * randomGenerator.nextDouble()+1000));
                }
                else{
                    WebviewContext.getWebview().flingScroll(0, (int)(200 * randomGenerator.nextDouble()+1000));
                }
            }
        });
    }

    public static void callJs(String jsCode, int time_s, int touchdown){
        webView.post(new Runnable() {
            @Override
            public void run() {
                int y=0;

                for(int i=0; i<touchdown; i++) {
                    WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
                    try {
                        Thread.sleep((int) (Math.random() * 10 * 1000) + 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    double upOrDown = Math.random();

                    if(upOrDown >0.8) {
                        y = y + (int)(500 * Math.random());
                    }
                    else{
                        y = y + (int)(200 * Math.random());
                    }
                    WebviewContext.getWebview().flingScroll(0,y);
                }
            }
        });
    }
}
