package com.dnc.crawler.ui.home;

import android.annotation.TargetApi;
import android.app.Activity;
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

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    BackgroundTask task;
    final int repeatCnt = 50;
    int y = 0;
    View returnView;
    CookieManager cookieManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        returnView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtOne = (TextView) returnView.findViewById(R.id.textView);


        Button doButton = binding.doButton;
        Button doButton2 = binding.doButton2;
        Button doButton3 = binding.doButton3;
        Button doButton4 = binding.doButton4;

        doButton.setOnClickListener(this::doButton);
        doButton2.setOnClickListener(this::doButton2);
        doButton3.setOnClickListener(this::doButton3);
        doButton4.setOnClickListener(this::doButton4);

        WebView.setWebContentsDebuggingEnabled(true);

        WebviewContext.setWebView(binding.web);

        WebviewContext.getWebview().getSettings().setJavaScriptEnabled(true);
        WebviewContext.getWebview().getSettings().setDomStorageEnabled(true);
        WebviewContext.getWebview().addJavascriptInterface(new HomeJavascriptInterface(), "Android");
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

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    if(WebviewContext.isSenarioOneEnded() == true){
//                    if(WebviewContext.senarioEnded() == true &&
//                        WebviewContext.getCnt() < repeatCnt) {
//                        view.loadUrl("https://m.daum.net");

//                        view.loadUrl("https://m.naver.com");
                        WebviewContext.setSenarioOneState(false);

//                        try {
//                            changeIP();
//                        } catch (Exception e) {
//                            Toast.makeText(requireContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                        }

                        txtOne.setText("repeatCnt : " + WebviewContext.getCnt());

                        if (CookieManager.getInstance().hasCookies() == true){
                            //- 웹뷰 로드 주소를 사용해 저장된 쿠키 목록 확인
                            String _url = view.getUrl();
                            String cookies = CookieManager.getInstance().getCookie(_url);
                            Log.d("cookies", cookies);

                            view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
//                            PostManager pm = new PostManager();
//                            pm.SendCookie(cookies);
                        }
                    }
                    else{
                        view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
//                        view.loadUrl("javascript:window.Android.getHtml_kakao(document.getElementsByTagName('html')[0].outerHTML);");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

//                        if (CookieManager.getInstance().hasCookies() == true){
//                            //- 웹뷰 로드 주소를 사용해 저장된 쿠키 목록 확인
//                            String _url = view.getUrl();
//                            String cookies = CookieManager.getInstance().getCookie(_url);
//                            Log.d("cookies", cookies);
//                        }
                    }
                }
                else {
                    redirect = false;
                }
//                CookieManager.getInstance().setCookie();
                CookieManager.getInstance().flush();
            }

//            private boolean handleUri(final Uri uri) {
//                Log.i("TAG", "Uri =" + uri);
//                final String host = uri.getHost();
//                final String scheme = uri.getScheme();
//                // Based on some condition you need to determine if you are going to load the url
//                // in your web view itself or in a browser.
//                // You can use `host` or `scheme` or any part of the `uri` to decide.
//                if (true) {
//                    // Returning false means that you are going to load this url in the webView itself
//                    return false;
//                } else {
//                    // Returning true means that you need to handle what to do with the url
//                    // e.g. open web page in a Browser
//                    final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                    return true;
//                }
//            }
        });


        //WebviewContext.getWebview().loadUrl("https://www.naver.com");

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


    void doButton(View v){
//        new Thread(() -> {
//            PostManager pm = new PostManager();
//            pm.GetCookie();
//        }).start();

//        new Thread(() -> {
//            PostManager pm = new PostManager();
//            pm.SendCookie();
//        }).start();

//        try {
//            changeIP();
//        } catch (Exception e) {
//            Toast.makeText(requireContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }
//        WebviewContext.getWebview().loadUrl("https://m.daum.net");
        WebviewContext.getWebview().loadUrl("https://www.naver.com");
    }

    void doButton2(View v) {
//        WebviewContext.getWebview().loadUrl("https://www.naver.com");
        Random randomGenerator = new Random();
        double upOrDown = randomGenerator.nextDouble();
        if(upOrDown < 0.5) {
            WebviewContext.getWebview().flingScroll(0, (int)(500 * randomGenerator.nextDouble()+1000));
        }
        else{
            WebviewContext.getWebview().flingScroll(0, -(int)(200 * randomGenerator.nextDouble()+1000));
        }
    }


    void doButton3(View v) {
        WebviewContext.getWebview().loadUrl("https://www.naver.com");
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
        String jsCode = "document.getElementById('q').value = 'TEST'\n";
//                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()\n"+
//                "document.getElementsByClassName('ico_ksg ico_search')[0].click()";
        WebviewContext.callJs(jsCode);
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
        if(netinfo!=null){
            if(netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
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

    public boolean isDeviceOnMobile() {
        ConnectivityManager cm =
                (ConnectivityManager)requireContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if(netinfo!=null){
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
}