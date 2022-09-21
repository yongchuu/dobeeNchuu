package com.dnc.crawler.ui.home;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HomeJavascriptInterface {
    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]"+doc.title());
        Log.d("html", doc.html());

        if(doc.title().equals("NAVER")){ // Main 화면
            String jsCode = "document.getElementById('query').value = '허그체어 학생 메쉬';" +
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
        }
        else if(doc.title().endsWith(": 네이버 통합검색")){
            //String jsCode = "for(var value of document.getElementsByClassName('m')){if(value.innerText=='쇼핑'){value.parentElement.click();break;}}";
            String jsCode = "document.getElementsByClassName('m')[6].parentElement.href";
            WebviewContext.callJs(jsCode);
        }
        else if(doc.title().endsWith("네이버쇼핑")) {
            String jsCode = "for (var value of document.getElementsByClassName('product_mall__v9966')){" +
                    "    if(value.innerText=='허그체어'){" +
                    "        value.click();" +
                    "    }" +
                    "}";
            WebviewContext.callJs(jsCode);
        }
        else {
            //not implements yet
        }
    }
}
