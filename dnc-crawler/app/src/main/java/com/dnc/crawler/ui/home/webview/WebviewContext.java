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
    public static void setSenarioTwoEnded(boolean isEnded) {
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
        cnt = cnt+1;
    }



    public static void goHome(){
        new Thread(() -> {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    WebviewContext.getWebview().loadUrl("https://m.naver.com");
                }
            });
        }).start();
    }

    public static void callLoadUrl(String url){
        new Thread(() -> {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    WebviewContext.getWebview().loadUrl(url);
                }
            });
        }).start();
    }


    public static String getUrl(String url){
//      WebviewContext.getWebview().loadUrl(url);
        return WebviewContext.getWebview().getUrl();
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


    public static void callJs(String jsCode, int delayTime_s){
        new Thread(() -> {
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
                }
            }, delayTime_s*1000);
        }).start();

//        scrollMove();

//        int times = delayTime_s/10;
//        times++;
//        for(int i = 0; i<times; i++) {
//            new Thread(() -> {
//                webView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Random randomGenerator = new Random();
//                        double upOrDown = randomGenerator.nextDouble();
//                        if (upOrDown < 0.5) {
//                            WebviewContext.getWebview().flingScroll(0, (int) (500 * randomGenerator.nextDouble() + 3000));
//                        } else {
//                            WebviewContext.getWebview().flingScroll(0, (int) (200 * randomGenerator.nextDouble() + 3000));
//                        }
//                    }
//                }, 3000);
//            }).start();
//        }
    }


    public static void delay_(int time_s) {
        int oneSec = 1000;

        try {
            Thread.sleep((int)(10 * oneSec));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delay(int time_s) {
        int oneSec = 1000;
        Random r = new Random();
//        int add_sec = r.nextInt(30);
        int add_sec = 0;
        int moveScale = (time_s + add_sec) / 10;//10초에 한번 씩 움직이기

        for(int i=0; i<moveScale; i++)
        {
//            scrollMove();
            try {
                Thread.sleep((int)(10 * oneSec));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void scrollMove(){
        webView.post(new Runnable() {
            @Override
            public void run() {
                Random randomGenerator = new Random();
                double upOrDown = randomGenerator.nextDouble();
                if(upOrDown < 0.5) {
                    WebviewContext.getWebview().flingScroll(0, (int)(500 * randomGenerator.nextDouble()+3000));
                }
                else{
                    WebviewContext.getWebview().flingScroll(0, (int)(200 * randomGenerator.nextDouble()+3000));
                }
            }
        });
    }

}
