package com.dnc.crawler.ui.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dnc.crawler.R;
import com.dnc.crawler.databinding.FragmentHomeBinding;
import com.dnc.crawler.ui.home.webview.Utils;
import com.dnc.crawler.ui.home.webview.WebviewContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import android.content.ClipData;
import android.content.ClipboardManager;
import static android.content.Context.CLIPBOARD_SERVICE;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    BackgroundTask task;
    final int repeatCnt = 50;
    int y = 0;
    View returnView;
    CookieManager cookieManager;
    boolean goFlag = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        returnView = inflater.inflate(R.layout.fragment_home, container, false);

        Button doButton = binding.doButton;
        Button doButton2 = binding.doButton2;
        Button doButton3 = binding.doButton3;
        Button doButton4 = binding.doButton4;

        doButton.setOnClickListener(this::doButton);
        doButton2.setOnClickListener(this::doButton2);
        doButton3.setOnClickListener(this::doButton3);
        doButton4.setOnClickListener(this::doButton4);
        TextView LogTxt = binding.textView;
        LogTxt.setText("Home page entered");
        WebView.setWebContentsDebuggingEnabled(true);

        WebviewContext.setWebView(binding.web);

        WebviewContext.getWebview().getSettings().setJavaScriptEnabled(true);
        WebviewContext.getWebview().getSettings().setDomStorageEnabled(true);
//        WebviewContext.getWebview().addJavascriptInterface(new HomeJavascriptInterface(), "Android");
//        WebviewContext.getWebview().addJavascriptInterface(new JsInterface_naver_fullTest_v3(), "Android");
        WebviewContext.getWebview().addJavascriptInterface(new JsInterface_kyobo_fullTest(), "Android");

//        WebviewContext.getWebview().canResolveLayoutDirection();

        try{
            cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebviewContext.getWebview().getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                cookieManager.setAcceptThirdPartyCookies(WebviewContext.getWebview(), true); // false 설정 시 오류 발생
            }
        }catch(Exception e){
        }


        WebviewContext.getWebview().setWebViewClient(new WebViewClient(){
            Boolean loadingFinished = true;
            Boolean redirect = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                final Uri uri = request.getUrl();
                String url = uri.toString();
                view.loadUrl(url);
//                WebviewContext.callLoadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                if (!redirect) {
                    loadingFinished = true;
                }
                Log.d("cwon", "onPageFinished");
//                if(!goFlag)
//                    return;

                int nowCnt = WebviewContext.getCnt();
                if (loadingFinished && !redirect) {
//                    if(WebviewContext.isSenarioOneEnded() == true){
                    if(nowCnt > repeatCnt){
                        Log.d("cwon", "repeat count over!");
                        return;
                    }

// cookie stack -----
//                    if(WebviewContext.isSenarioOneEnded() == true ) {
//                        WebviewContext.setSenarioOneState(false);
// // cookie stack end -----

// full senario test -----
                    boolean s2isEnded = WebviewContext.isSenarioTwoEnded();
                    Log.d("cwon", "s2isEnded : " + s2isEnded);
                    if(WebviewContext.isSenarioTwoEnded() == true ) {
                        Log.d("cwon", "prepare next lap!");
                        WebviewContext.setSenarioTwoEnded(false);
// full senario end -----
                        try {
                            changeIP();
                        } catch (Exception e) {
                            Toast.makeText(requireContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }

                        String beforeUA = view.getSettings().getUserAgentString();
                        String nowUA = getUA();
                        view.getSettings().setUserAgentString(nowUA);
                        Log.d("cwon", "beforeUA : " + beforeUA);
                        Log.d("cwon", "nowUA : " + nowUA);

                        TextView LogTxt = binding.textView;
                        LogTxt.setText("repeatCnt : " + WebviewContext.getCnt() +"\n" + nowUA);

                        if (CookieManager.getInstance().hasCookies() == true) {
                            //- 웹뷰 로드 주소를 사용해 저장된 쿠키 목록 확인
                            String _url = view.getUrl();
                            String cookies = CookieManager.getInstance().getCookie(_url);
                            Log.d("cwon", nowCnt + " / " + cookies);
//                            new Thread(() -> {
//                                PostManager pm = new PostManager();
//                                pm.SendCookie(cookies);
//                            }).start();

                            view.clearCache(true);
                            view.clearHistory();
                            CookieManager.getInstance().removeAllCookies(null);
//                            CookieManager.getInstance().flush();
                        }

                        boolean checkLeftCookies = CookieManager.getInstance().hasCookies();
                        Log.d("cwon", "checkLeftCookies : " + checkLeftCookies);


                        view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
//                        view.loadUrl("javascript:window.Android.checkIP(document.getElementsByTagName('html')[0].outerHTML);");
                    }
                    else {
                        String _url = view.getUrl();
                        String cookies = CookieManager.getInstance().getCookie(_url);
                        Log.d("cwon", "url : " + _url + " / " + cookies);
                        // 순회 과정 중
                        view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
//                        view.loadUrl("javascript:window.Android.checkIP(document.getElementsByTagName('html')[0].outerHTML);");
                    }
                }
                else {
                    redirect = false;
                }
                CookieManager.getInstance().flush();
            }
        });

        return root;
    }




    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();

                //네트워크 중에서 IP가 할당된 넘들에 대해서 뺑뺑이를 한 번 더 돕니다.
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    //네트워크에는 항상 Localhost 즉, 루프백(LoopBack)주소가 있으며, 우리가 원하는 것이 아닙니다.
                    //IP는 IPv6와 IPv4가 있습니다.
                    //IPv6의 형태 : fe80::64b9::c8dd:7003
                    //IPv4의 형태 : 123.234.123.123
                    //어떻게 나오는지는 찍어보세요.
                    if(inetAddress.isLoopbackAddress()) {
                        Log.i("IPAddress", intf.getDisplayName() + "(loopback) | " + inetAddress.getHostAddress());
                    }
                    else
                    {
                        Log.i("IPAddress", intf.getDisplayName() + " | " + inetAddress.getHostAddress());
                    }

                    //루프백이 아니고, IPv4가 맞다면 리턴~~~
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
//                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
//                        return inetAddress.getHostAddress().toString();
//                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getUA()
    {
        List<String> uaList = Arrays.asList("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                "Mozilla/5.0 (Linux; Android 9.0; SAMSUNG SM-F900U Build/PPR1.180610.011) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 10; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
        );
        Random r = new Random();
        return uaList.get(r.nextInt(uaList.size()));
    }

    void doButton(View v){
        TextView txtOne = (TextView) returnView.findViewById(R.id.textView);
        txtOne.setText("loading...");
//        new Thread(() -> {
//            PostManager pm = new PostManager();
//            pm.GetCookie();
//        }).start();

//        new Thread(() -> {
//            PostManager pm = new PostManager();
//            pm.SendCookie();
//        }).start();

        Log.d("cwon", "start button");
//        try {
//            changeIP();
//        } catch (Exception e) {
//            Toast.makeText(requireContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }

        WebviewContext.getWebview().loadUrl("https://www.kyobobook.co.kr");
//        WebviewContext.getWebview().loadUrl("https://www.daum.net");
//        WebviewContext.getWebview().loadUrl("https://www.naver.com");
    }

    void doButton2(View v) {

        ClipboardManager clipboard = null;



        WebviewContext.getWebview().loadUrl("https://www.naver.com");
//        Random randomGenerator = new Random();
//        double upOrDown = randomGenerator.nextDouble();
//        if(upOrDown < 0.5) {
//            WebviewContext.getWebview().flingScroll(0, (int)(500 * randomGenerator.nextDouble()+1000));
//        }
//        else{
//            WebviewContext.getWebview().flingScroll(0, -(int)(200 * randomGenerator.nextDouble()+1000));
//        }
    }


    void doButton3(View v) {
        Instrumentation inst = new Instrumentation();
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_B);


//        new Thread(() -> {
//            PostManager pm = new PostManager();
//            pm.SendCookie("cookies!!");
//        }).start();

//        WebviewContext.getWebview().loadUrl("https://www.naver.com");
//        boolean wifiEnabled = getWifiStatus();
//        toggleWifiMode(!wifiEnabled);
//
//        // 원래 on되어있으면 off하고 마무리
//        if (wifiEnabled==true){
//            return;
//        }
//
//        while(!isDeviceOnWifi()) {
//            try {
//                Thread.sleep(500);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//        toggleWifiMode(false);
//        while(!isDeviceOnMobile()) {
//            try {
//                Thread.sleep(500);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    void doButton4(View v) {

        WebviewContext.getWebview().loadUrl("https://www.naver.com");
//        goFlag = !goFlag;
//        Log.d("debug", "goFlag : " + goFlag);

//        LogTxt.setText("Start!!!");
//        txtOne.setText("button4 clicked");
//        String jsCode = "document.getElementById('q').value = 'TEST'\n";
////                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()\n"+
////                "document.getElementsByClassName('ico_ksg ico_search')[0].click()";
//        WebviewContext.callJs(jsCode);
    }

    private void changeIP()
    {
        boolean wifiEnabled = getWifiStatus();
        toggleWifiMode(!wifiEnabled);

        // 원래 on되어있으면 off하고 마무리
        if (wifiEnabled==true){
            return;
        }

        //wifi on 후 wifi연결될때 까지 기다림
        while(!isDeviceOnWifi()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        //wifi of 후 data가 연결 될 때 까지 기다림
        toggleWifiMode(false);
        while(!isDeviceOnMobile()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isDeviceOnWifi() {
        ConnectivityManager cm =
                (ConnectivityManager)requireContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null) {
            if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean isDeviceOnMobile() {
        ConnectivityManager cm =
                (ConnectivityManager)requireContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo!=null) {
            if(netinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void toggleWifiMode(boolean onIsTrue){
        WifiManager wifiManager = (WifiManager) requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(onIsTrue);
    }

    private boolean getWifiStatus() {
        WifiManager wifiManager = (WifiManager) requireContext().getSystemService(Context.WIFI_SERVICE);

        boolean wifiEnabled = wifiManager.isWifiEnabled();
        return wifiEnabled;
    }

    private String getSearchKeyword()
    {
        List<String> locations = Arrays.asList("강남", "양재", "이태원", "잠실", "마포");
        List<String> foods = Arrays.asList("삼겹살", "마라탕", "중식", "샐러드", "커피");
        List<String> endwords = Arrays.asList("소개팅", "맛집", "존맛", "인생맛집", "강추");

        Random r = new Random();

        String loc = locations.get(r.nextInt(locations.size()));
        String food = foods.get(r.nextInt(foods.size()));
        String endword = endwords.get(r.nextInt(endwords.size()));

        String tmp = loc + " " + food + " " + endword;

        return tmp;
    }

    public static String[] shuffle(String[] arr){
        for(int x=0;x<arr.length;x++){
            int i = (int)(Math.random()*arr.length);
            int j = (int)(Math.random()*arr.length);

            String tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        return arr;
    }
}