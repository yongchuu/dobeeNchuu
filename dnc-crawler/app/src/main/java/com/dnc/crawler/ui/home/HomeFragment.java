package com.dnc.crawler.ui.home;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dnc.crawler.databinding.FragmentHomeBinding;
import com.dnc.crawler.ui.home.webview.WebviewContext;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button doButton = binding.doButton;
        Button doButton2 = binding.doButton2;

        doButton.setOnClickListener(this::doButton);
        doButton2.setOnClickListener(this::doButton2);

        WebView.setWebContentsDebuggingEnabled(true);

        WebviewContext.setWebView(binding.web);

        WebviewContext.getWebview().getSettings().setJavaScriptEnabled(true);
        WebviewContext.getWebview().getSettings().setDomStorageEnabled(true);
        WebviewContext.getWebview().addJavascriptInterface(new HomeJavascriptInterface(), "Android");
//        WebviewContext.getWebview().canResolveLayoutDirection();
        WebviewContext.getWebview().setWebViewClient(new WebViewClient(){
            Boolean loadingFinished = true;
            Boolean redirect = false;

            //            @TargetApi(Build.VERSION_CODES.N)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                final Uri uri = request.getUrl();
//                return handleUri(uri);
//            }

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
//                    Console.WriteLine("Finished Loading!!!");
                    view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
                }
                else {
                    redirect = false;
                }

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (Build.VERSION.SDK_INT >= 21){
                    Log.e("LOG_TAG", "HTTP error code : "+errorResponse.getStatusCode());
                }

//                webviewActions.onWebViewReceivedHttpError(errorResponse);
            }


            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
                Log.e("error","ReceivedError on WebView. ERROR CODE is " + errorCode);
                Log.e("error","description is " + description);
                Log.e("error","failingUrl is " + failingUrl);
//                try{
//                    view.loadUrl("file:///android_asset/www/error.html?errorCode=" + errorCode + "&errorDescription=" + description);
//                }catch  (Exception e) {
//                    Log.e("error", e.toString());
//                }
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

    void doButton(View v){
        WebviewContext.getWebview().loadUrl("https://www.naver.com");
    }

    void doButton2(View v) {
        WebviewContext.getWebview().loadUrl("https://news.naver.com/");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}