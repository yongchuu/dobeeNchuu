package com.dnc.crawler.ui.home;

import static com.dnc.crawler.ui.home.HomeFragment.shuffle;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

public class JsInterface_naver_fullTest_v3 {
    // 테스트 시나리오
    /*
        1. 2회 각 다른 블로그 클릭 후 약 1:30초 체류
        본 제품 클릭
    * */
    String step1_keyWord = "";
//    String target_keyWord = "허그체어 사무실 책상 컴퓨터 학생 메쉬 의자 노블헤드 화이트 DH100HMB";
    String target_keyWord = "허그체어 사무실 사무용 책상 컴퓨터 학생 메쉬의자 노블헤드 화이트 DH100HMW";
    int senario_step = 1;
    int step1Cnt = 0;
    boolean isfirst_search = true;
    boolean isGoingToTargetPage = true;
    boolean isStartInFirstStep_news = true;
    boolean isGoingToNewsPage = true;
    int p = 0;
    int delayTime_s = 3;
    int page_delayTime_s = 90;

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        KeywordManager km = new KeywordManager();

        step1_keyWord = km.getSearchKeyword();

        if(senario_step == 1) {
            Random r = new Random();
//            p = r.nextInt(2);
            p = 1;

            if(p == 1){
                //이건 안정적으로 동작하는거 확인함 11.02
                goFoodBlog(doc);
            }
//            else if(p == 2){
//                goShopping(doc);
//            }
//            else if(p == 3){
//                // 되는데 브레이크 포인트가 안먹히네?? 뭐지 11.02
//                goNewsPage(doc);
//            }
//            else if(ptr < 0.8){
////                3. 트렌드쇼핑 클릭 -> 체류(이거는 CPA라 무조건 될듯하다.)
//            }
//            else if(ptr < 0.9){
////                4. 네이버 아이디 아래 -> 시행사 클릭 -> 체류
//            }
//            else if(ptr < 1){
////                5. 네이버 날씨 클릭 -> 오늘 내일 오전 오후 확인
//            }
        }
        else if(senario_step == 2){
            clickTarget_n(doc);
        }
    }

    private void goFoodBlog(Document doc)
    {
        String t = doc.title();

//        TextView LogTxt = binding.textView;
//        LogTxt.setText("repeatCnt : " + WebviewContext.getCnt());
        Log.d("cwon", "step1_keyWord : "+step1_keyWord);
        if (doc.title().equals("NAVER") && isfirst_search) { // Main 화면
            // 검색
            String jsCode = "document.getElementById('query').value = '"+ step1_keyWord +"';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
//            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode, delayTime_s);
        } else if (doc.title().endsWith(": 네이버 통합검색") && isfirst_search) {
            // 맛집 클릭
            //String jsCode = "document.getElementsByClassName('tzwk0')[0].click()";
            String jsCode = "document.getElementsByClassName('api_txt_lines total_tit _cross_trigger')[0].click()";
//            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode, delayTime_s);
        } else if (((doc.title().endsWith("플레이스") || doc.title().endsWith("블로그")) &&
                !doc.title().endsWith(": 네이버 통합검색"))
                && isfirst_search) {
            // 뒤로가기
            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode, page_delayTime_s);
            isfirst_search = false;
        }
        else if (doc.title().endsWith(": 네이버 통합검색") && !isfirst_search) {
            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode, delayTime_s);
            resetS1();
        }
    }

    private void clickTarget_n(Document doc)
    {
        if (doc.title().equals("NAVER") && isGoingToTargetPage) { // Main 화면
            String[] keyWords = target_keyWord.split(" ");
            shuffle(keyWords);
            String search_word = String.join(" ", keyWords);
            String jsCode = "document.getElementById('query').value = '" + search_word + "';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
//            String jsCode = "javascript:document.getElementById('query').value = '" + search_word + "';" +
//                    "document.getElementsByClassName('sch_btn_search')[3].click();";
            WebviewContext.callJs(jsCode, delayTime_s);
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
            WebviewContext.callJs(jsCode, delayTime_s);
            //            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        } else if (doc.title().endsWith("네이버쇼핑") && isGoingToTargetPage) {
            String jsCode = "document.getElementsByClassName('product_info_main__piyRs linkAnchor')[0].click()";
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
            WebviewContext.callJs(jsCode, delayTime_s);
        } else if(doc.body().className().contains("smartstore") && isGoingToTargetPage){
            // 목적 페이지에서 뒤로가기
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode, page_delayTime_s);
            isGoingToTargetPage = false;

        } else if (doc.title().endsWith("네이버쇼핑") && !isGoingToTargetPage) {
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode, delayTime_s);
//            WebviewContext.goHome();
//            resetS2();
        }
        else if (doc.title().endsWith(": 네이버 통합검색") && !isGoingToTargetPage) {
                            String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
//            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode, delayTime_s);
            resetS2();
//            WebviewContext.getWebview().loadUrl("https://www.naver.com");
        }
    }

    private void resetS2()
    {
        isfirst_search = true;
        isStartInFirstStep_news=true;
        isGoingToTargetPage = true;
        senario_step = 1;
        WebviewContext.setSenarioTwoEnded(true);
        WebviewContext.cntUp();
    }

    private void resetS1()
    {
        isGoingToNewsPage = true;
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        step1Cnt = step1Cnt + 1;
        if(step1Cnt > 1){
            step1Cnt=0;
            senario_step = 2;
        }

    }
    private void repeatS1()
    {
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        senario_step = 2;
        isStartInFirstStep_news = true;
        isGoingToNewsPage = true;
    }


    private void goNewsPage(Document doc)
    {
        if (doc.title().equals("NAVER") && isStartInFirstStep_news) { // Main 화면
//            String[] keyWords = keyWord.split(" ");
//            shuffle(keyWords);
//            String search_word = String.join(" ", keyWords);

            String jsCode = "document.getElementById('query').value = '" + step1_keyWord + "';"+
//            String jsCode = "document.getElementById('query').value = '인도 다리 붕괴 youtube';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
            isStartInFirstStep_news = false;
        } else if (doc.title().endsWith(": 네이버 통합검색") && !isStartInFirstStep_news && isGoingToNewsPage) {
            String jsCode = "document.getElementsByClassName('btn_tab_more _unfold')[0].click();" +
                    "for(var value of document.getElementsByClassName('m'))" +
                    "{" +
                    "if(value.innerText=='뉴스'){" +
                    "value.parentElement.click();" +
                    "}" +
                    "};";
            WebviewContext.callJs(jsCode);
            //            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        }
        else if (doc.title().endsWith(": 네이버 뉴스검색") && !isStartInFirstStep_news && isGoingToNewsPage) {
            if(!doc.getElementsByClass("head").isEmpty()) {
                WebviewContext.goHome();
                isStartInFirstStep_news = true;
            }
            String jsCode = "document.getElementsByClassName('news_tit')[0].click()";
            WebviewContext.callJs(jsCode);
        } else if ((!doc.title().endsWith(": 네이버 뉴스검색")) && !isStartInFirstStep_news && isGoingToNewsPage) {
            WebviewContext.delay(page_delayTime_s);
            // 구독 누르기
            String jsCode = "document.getElementsByClassName('_channel_add ofhd_float_subscribe_btn _CHANNEL_BUTTON _CHANNEL_ADD')[0].click()";
            String jsCode1 = "window.history.back()";
            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode1);
            isGoingToNewsPage = false;
        } else if (doc.title().endsWith(": 네이버 뉴스검색") && !isGoingToNewsPage) {
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);
        } else if (doc.title().endsWith(": 네이버 통합검색")&& !isGoingToNewsPage) {
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);

            resetS1();
//            repeatS1();
        }
    }
}


/*



 * */