

package com.dnc.crawler.ui.home;

        import static com.dnc.crawler.ui.home.HomeFragment.shuffle;

        import android.content.Context;
        import android.util.Log;
        import android.webkit.JavascriptInterface;

        import com.dnc.crawler.ui.home.webview.WebviewContext;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;

        import java.util.Random;

public class JsInterface_naver_gotoPdt {
    // news만 순회 100번 돌려보겠음
    String step1_keyWord = "강남 삼겹살 맛집";

    String keyWord = "허그체어 사무실 책상 컴퓨터 학생 메쉬 의자 노블헤드 화이트 DH100HMB";
    int senario_step = 1;
    boolean isfirst_search = true;
    boolean isGoingToTargetPage = true;
    boolean isStartInFirstStep_news = true;
    boolean isGoingToNewsPage = true;
    int p = 0;

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        KeywordManager km = new KeywordManager();

        step1_keyWord = km.getSearchKeyword1();

        clickTarget_n(doc);
    }


    private void clickTarget_n(Document doc)
    {
        if (doc.title().equals("NAVER") && isGoingToTargetPage) { // Main 화면
            String[] keyWords = keyWord.split(" ");
            shuffle(keyWords);
            String search_word = String.join(" ", keyWords);
            String jsCode = "document.getElementById('query').value = '" + search_word + "';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
//            String jsCode = "javascript:document.getElementById('query').value = '" + search_word + "';" +
//                    "document.getElementsByClassName('sch_btn_search')[3].click();";
            WebviewContext.callJs(jsCode);
        } else if (doc.title().endsWith(": 네이버 통합검색") && isGoingToTargetPage) {
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
        } else if (doc.title().endsWith("네이버쇼핑") && isGoingToTargetPage) {
            String jsCode = "document.getElementsByClassName('product_info_main__piyRs linkAnchor')[2].click()";
//            String jsCode = "\n" +
//                    "for (var item of document.getElementsByClassName('product_list_item__b84TO')){\n" +
//                        "var midurl = item.getElementsByClassName('productExpandSub_link_report__lpwFp linkAnchor')[0]\n" +
//                        "if(midurl === undefined){\n" +
//                        "}else{\n" +
//                            "var parameters = midurl.href.split('?')[1].split('&')\n" +
//                            "for(var p of parameters) {\n" +
//                                "if(p.includes('Mid')) {\n" +
//                                    "if(p.split('=')[1] === '84053471681'){\n" +
//                                        "console.log(item)\n" +
//                                        "item.getElementsByClassName('product_info_main__piyRs linkAnchor')[0].click()\n" +
//                                    "}\n" +
//                                "}\n" +
//                            "}\n" +
//                        "}\n" +
//                    "}";
            WebviewContext.callJs(jsCode);
        } else if(doc.body().className().contains("smartstore") && isGoingToTargetPage){
            // 목적 페이지에서 뒤로가기
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode);
            isGoingToTargetPage = false;

        } else if (doc.title().endsWith("네이버쇼핑") && !isGoingToTargetPage) {
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode);
        }
        else if (doc.title().endsWith(": 네이버 통합검색") && !isGoingToTargetPage) {
            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);
            resetS2();
        }
    }


    private void resetS2()
    {
        isfirst_search = true;
        isStartInFirstStep_news=true;
        isGoingToTargetPage = true;
//        senario_step = 1;
        WebviewContext.setSenarioTwoEnded(true);
        WebviewContext.cntUp();
    }

}


/*



 * */