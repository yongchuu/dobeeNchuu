package com.dnc.crawler.ui.home;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HomeJavascriptInterface {
    String keyWord = "허그체어 학생 메쉬";
    String Mid = "1234";

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]"+doc.title());
        Log.d("html", doc.html());
        String t = doc.title();

        if(doc.title().equals("NAVER")){ // Main 화면
            String jsCode = "javascript:document.getElementById('query').value = '허그체어 학생 메쉬';" +
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
//            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        }
        else if(doc.title().endsWith(": 네이버 통합검색")){
            //String jsCode = "for(var value of document.getElementsByClassName('m')){if(value.innerText=='쇼핑'){value.parentElement.click();break;}}";
            //String jsCode = "document.getElementsByClassName('m')[6].parentElement.href";
            String jsCode = "document.getElementsByClassName('btn_tab_more _unfold')[0].click();" +
                    "for(var value of document.getElementsByClassName('m'))" +
                    "{" +
                        "if(value.innerText=='쇼핑'){" +
                            "value.parentElement.click();" +
                        "}" +
                    "};";
            WebviewContext.callJs(jsCode);
//            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        }
        else if(doc.title().endsWith("네이버쇼핑")) {
            String jsCode = "document.getElementsByClassName('product_info_main__piyRs linkAnchor')[0].click()";
//            String jsCode = "\n" +
//                    "for (var item of document.getElementsByClassName('product_list_item__b84TO')){\n" +
//                        "var midurl = item.getElementsByClassName('productExpandSub_link_report__lpwFp linkAnchor')[0]\n" +
//                        "if(midurl === undefined){\n" +
//                        "}else{\n" +
//                            "var parameters = midurl.href.split('?')[1].split('&')\n" +
//                            "for(var p of parameters) {\n" +
//                            "if(p.includes('Mid')) {\n" +
//                            "if(p.split('=')[1] === '83676497895'){\n" +
//                            "console.log(item)\n" +
//                            "item.getElementsByClassName('product_info_main__piyRs linkAnchor')[0].click()\n" +
//                            "}\n" +
//                            "}\n" +
//                            "}\n" +
//                        "}\n" +
//                    "}";

            WebviewContext.callJs(jsCode);
//            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        }
        else {
            //not implements yet
        }
    }
}
