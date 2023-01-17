package com.example.oreo1;

import static android.telephony.ServiceState.STATE_IN_SERVICE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

//import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {
    String source;
    private WebView webView;
    private WebSettings mWebSettings;
    private Button button;
    private ProgressBar pb;
    private boolean flag = false;
    private static final String TAG = "AirplaneModeActivity";
    private final String COMMAND_FLIGHT_MODE_1 = "settings put global airplane_mode_on";
    private final String COMMAND_FLIGHT_MODE_2 = "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state";
    ServiceState ser;
    AirplaneModeService airplaneModeService = new AirplaneModeService();
    boolean wifiEnabled = false;
//    List<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Runtime.getRuntime().exec("su");
            Toast.makeText(getApplicationContext(), "has root permission!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "has not root permission!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        PackageManager pm = getApplicationContext().getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);

//        for(PackageInfo pi : list) {
//            ApplicationInfo ai = null;
//            try {
//                ai = pm.getApplicationInfo(pi.packageName, 0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(">>>>>>packages is<<<<<<<<" + ai.publicSourceDir);
//
//            if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
//                System.out.println(">>>>>>packages is system package"+pi.packageName);
//                Toast.makeText(getApplicationContext(), "packages is system package!", Toast.LENGTH_LONG).show();
//            }
//        }


        webView = (WebView) findViewById(R.id.webview);
        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        webView.getSettings().setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                // 자바스크립트 기본 메소드로 html 소스를 통째로 지정해서 인자로 넘김
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);");
//                view.loadUrl("");

                final String[] versionString = {
                        null
                };


//                String js = "javascript:(function(){"+
//                        "l=document.getElementsByClassName('pri_btn')[0];"+
//                        "l.click();"+
//                        "})()";
//                view.evaluateJavascript(js, new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String s) {
//                        String result = s;
//                        Log.d("Tag", result);
//                    }
//                });

                // 웹페이지가 불린 이후에 실행되는 코드
                //
//                view.evaluateJavascript("(function(){return document.getElementsByClassName(\"api_list_scroll lst_sch\")})();",
//                        new ValueCallback< String >() {
//                            @Override
//                            public void onReceiveValue(String html) {
//
//                                Document doc = Jsoup.parse(html);
//                                Element searchEle = doc.getElementById("search_btn");
//                                String onClickFunction = searchEle.attr("onclick");
//
////                                String getFullUrl = String.format("https://cchat.in", versionString[0]);
////                                view.loadUrl(getFullUrl);
//                            }
//                        });
            }
        });
//                (new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view,url,favicon);
//            }
//            @Override
//            public void onPageFinished(WebView view, final String url) {
//                super.onPageFinished(view,url);
//            }
//        });

        webView.loadUrl("https://www.naver.com");



        Button button1 = (Button) findViewById(R.id.button1) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "button1 Clicked!", Toast.LENGTH_LONG).show();

//                ChangePage("https://blog.naver.com/dduchelin/222038871857");
                ChangePage("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=메트리스+추천");
//                ChangePage("https://section.blog.naver.com");
//                ChangePage("https://www.tistory.com");
                //연속적으로  url호출이 적용 안됨.
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", source);
                Toast.makeText(getApplicationContext(), source, Toast.LENGTH_LONG).show();
//                mWebView.loadUrl("https://www.tistory.com");
//                mWebView.evaluateJavascript("https://www.tistory.com");
//                webView.loadUrl("https://section.blog.naver.com");
//                mWebView.getUrl("https://blog.naver.com/dduchelin/222042185772");
//                ChangePage("https://blog.naver.com/dduchelin/222042185772");
//                ChangePage("https://blog.naver.com/dduchelin/222038871857");
//                ChangePage("https://section.blog.naver.com");
//                ChangePage("https://www.tistory.com");
                //연속적으로  url호출이 적용 안됨.
            }
        });

        Button toggle_btn = (Button) findViewById(R.id.toggle_btn);
        toggle_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check current state first
                boolean state = isAirplaneMode();
                Log.d("airplaneMode","AirPlaneMode : " + state);
                Toast.makeText(getApplicationContext(), "AirPlaneMode : " + state, Toast.LENGTH_LONG).show();
                // toggle the state
                toggleAirplaneMode(state);

                ser = new ServiceState();
                ser.setState(STATE_IN_SERVICE);


//                airplaneModeService.run(getApplicationContext());
            }
        });

        Button toggle_btn2 = (Button) findViewById(R.id.toggle_btn2);
        toggle_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiEnabled = getWifiStatus();
                toggleWifiMode(!wifiEnabled);
                wifiEnabled = getWifiStatus();
                Toast.makeText(getApplicationContext(), "wifi mode : " + wifiEnabled, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void toggleWifiMode(boolean isWifiOn){
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(isWifiOn);
    }

    private boolean getWifiStatus() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        boolean wifiEnabled = wifiManager.isWifiEnabled();
        return wifiEnabled;
    }


    public boolean isAirplaneMode() {
        return android.provider.Settings.Global.getInt(this.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
//        return Settings.Global.getInt(this.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
    }
    public void toggleAirplaneMode(boolean state) {
        // toggle airplane mode
        try {
            Toast.makeText(getApplicationContext(), "toggleAirplaneMode_1", Toast.LENGTH_LONG).show();
//            android.provider.Settings.System.putInt(this.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, state ? 0 : 1);
//            android.provider.Settings.Global.putInt(this.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, state ? 0 : 1);
            Settings.System.putInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, state ? 0 : 1);

            // broadcast an intent to inform
            Toast.makeText(getApplicationContext(), "toggleAirplaneMode_2", Toast.LENGTH_LONG).show();

            //세팅 창으로 넘어감
//            Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);


            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !state);
            sendBroadcast(intent);
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mediaMountUri));
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            Log.d("airplaneMode",e.toString());
        }
    }


/*
<button id="search_btn" type="submit" title="검색" tabindex="3" class="btn_submit" onclick="window.nclick(this,'sch.action','','',event);" style="">
<span class="blind">검색</span>
<span class="ico_search_submit"></span>
</button>
 */

    public class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            // https://blog.naver.com/shino1025
            //위 자바스크립트가 호출되면 여기로 html이 반환됨

//            Toast.makeText(getApplicationContext(), "hi, getHtml!", Toast.LENGTH_LONG).show();
            source = html;

            Document doc = Jsoup.parse(html);
            Element searchEle = doc.getElementById("search_btn");
            String onClickFunction = searchEle.attr("onclick");
            webView.loadUrl(onClickFunction);
        }
    }

//     #1
    @SuppressLint("InlinedApi")
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @SuppressLint("NewApi")
    public void airplaneModeOn(View view) {
        try {
            android.provider.Settings.System.putInt(getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON,
                    isAirplaneModeOn(getApplicationContext()) ? 0 : 1);

            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !isAirplaneModeOn(getApplicationContext()));
            sendBroadcast(intent);
        } catch (Exception e) {
            Log.d("Tag", e.toString());
            Toast.makeText(this, "Exception occured during Airplane Mode ON", Toast.LENGTH_LONG)
                    .show();
        }
    }

    protected void ChangePage(String url)
    {
        flag = true;

        //TODO : Load Cookies from DB server

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.setCookie("http://192.168.18.13:808", "name" + "=" + name);
//        cookieManager.setCookie("http://192.168.18.13:808", "action" + "=" + action);
//        cookieManager.setCookie("url","123");
//        cookieManager.removeAllCookies(null);

        cookieManager.setAcceptThirdPartyCookies(webView, true);
        webView.loadUrl(url);

        Toast.makeText(getApplicationContext(), url + "_Flag : " + flag, Toast.LENGTH_LONG).show();
    }


}




//
//public class MyRunnable implements Runnable {
//
//    private String url;
//    private WebView wv;
//
//    public MyRunnable(String url, WebView wv) {
//        this.url = url;
//        this.wv = wv;
//    }
//
//    public void run() {
//        wv.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
//
//        wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                wv.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//            }
//        });
//    }
//}