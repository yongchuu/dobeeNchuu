package com.dnc.crawler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        doButton.setOnClickListener(this::doButton);

        WebviewContext.setWebView(binding.web);

        WebviewContext.getWebview().getSettings().setJavaScriptEnabled(true);
        WebviewContext.getWebview().addJavascriptInterface(new HomeJavascriptInterface(), "Android");

        WebviewContext.getWebview().setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].outerHTML);");
            }
        });
        WebviewContext.getWebview().loadUrl("https://www.naver.com");

        return root;
    }

    void doButton(View v){
        WebviewContext.getWebview().loadUrl("https://www.naver.com");
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}