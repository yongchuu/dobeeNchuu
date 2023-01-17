package com.dnc.crawler.ui.home;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JsInterface_naver_stackCookies_v1 extends HomeJavascriptInterface {
    String step1_keyWord = "강남 삼겹살 맛집";
    //    String keyWord = "허그체어 노블헤드 게이밍";
    int senario_step = 1;
    boolean isfirst_search = true;
    boolean isGoing2Page = true;
    boolean isfirst_shopping = true;
    int shoppingState = 0;
    boolean isfirst_news = true;
    boolean randomFlag = true;
    double ptr = 0;
    int p = 0;


    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        KeywordManager km = new KeywordManager();

        step1_keyWord = km.getSearchKeyword();

        if(senario_step==1) {
            Random r = new Random();
//            p = r.nextInt(2);
            p = 1;

            if(p == 1){
                //이건 안정적으로 동작하는거 확인함 11.02
                goFoodBlog(doc);
            }
            else if(p == 2){
                goShopping(doc);
            }
            else if(p == 3){
                // 되는데 브레이크 포인트가 안먹히네?? 뭐지 11.02
                goNewsPage(doc);
            }
            else if(ptr < 0.8){
//                3. 트렌드쇼핑 클릭 -> 체류(이거는 CPA라 무조건 될듯하다.)
            }
            else if(ptr < 0.9){
//                4. 네이버 아이디 아래 -> 시행사 클릭 -> 체류
            }
            else if(ptr < 1){
//                5. 네이버 날씨 클릭 -> 오늘 내일 오전 오후 확인
            }
        }
        else if(senario_step == 2){
//            if (CookieManager.getInstance().hasCookies() == true){
//                //- 웹뷰 로드 주소를 사용해 저장된 쿠키 목록 확인
//                String _url = WebviewContext.getWebview().getUrl();
//                String cookies = CookieManager.getInstance().getCookie(_url);
//                Log.d("cookies", cookies);
//            }
//            clickTarget_n(doc);
            resetS2();
        }
    }



    private void resetS2()
    {
        isfirst_search = true;
        senario_step = 1;
        WebviewContext.setSenarioTwoEnded(true);
        WebviewContext.cntUp();
    }
    private void resetS1()
    {
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        WebviewContext.cntUp();
        senario_step = 1;
        randomFlag = true;
    }

    private void goFoodBlog(Document doc)
    {
        int delayTime_s = 5;
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
//            WebviewContext.delay(1);
            WebviewContext.callJs(jsCode, delayTime_s);
        } else if (((doc.title().endsWith("플레이스") || doc.title().endsWith("블로그")) ||
                !doc.title().endsWith(": 네이버 통합검색"))
                && isfirst_search) {
            // 뒤로가기
            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
            WebviewContext.delay(30);
            WebviewContext.callJs(jsCode, delayTime_s);
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

    private void goShopping(Document doc)
    {
        if(isfirst_shopping && shoppingState==0) {
            isfirst_shopping = false;
            // 한 페이지 내에서 다 이루어짐
            // 쇼핑 라이브 클릭
            String jsCode = "document.getElementsByClassName('nav_link nav_shop_live')[0].click()";
            WebviewContext.callJs(jsCode);
            String jsCode1 = "document.getElementsByClassName('clc_link_live _MM_SHOP_LIVE_NATIVE_VIEWER')[0].click()";
            WebviewContext.callJs(jsCode1);
            shoppingState = 1;
        } else if(doc.title() != "NAVER" && shoppingState==1) {
            // 쇼핑 라이브 시청 종료( x버튼 눌려 뒤로가기)
            String jsCode2 = "document.getElementsByClassName('HeaderButton_wrap_YBlo0')[2].click()";
            WebviewContext.callJs(jsCode2);
            shoppingState = 3;
        } else if (shoppingState == 3 && !isfirst_shopping) {
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode);
            resetS1();
        }
//        if(isfirst_shopping && shoppingState==0) {
//            isfirst_shopping = false;
//            // 한 페이지 내에서 다 이루어짐
//            // 쇼핑 라이브 클릭
//            String jsCode = "document.getElementsByClassName('nav_link nav_shop_live')[0].click()";
//            WebviewContext.callJs(jsCode);
////                    WebviewContext.moveNdelay(10);
//            shoppingState=1;
//        }
//        else if(!isfirst_shopping && shoppingState==1) {
//            // 쇼핑 라이브 영상 시청
//            String jsCode = "document.getElementsByClassName('clc_link_live _MM_SHOP_LIVE_NATIVE_VIEWER')[0].click()";
//            WebviewContext.callJs(jsCode);
//            shoppingState=2;
//
//        }else if(!isfirst_shopping && shoppingState==2) {
//            // 쇼핑 라이브 시청 종료( x버튼 눌려 뒤로가기)
//            String jsCode = "document.getElementsByClassName('HeaderButton_wrap_YBlo0')[2].click()";
//            WebviewContext.callJs(jsCode);
//            shoppingState=3;
//        } else if (shoppingState==3 && !isfirst_shopping) {
//            String jsCode = "window.history.back()";
//            WebviewContext.callJs(jsCode);
//            resetS1();
//        }
    }

    private void goNewsPage(Document doc)
    {
        if (doc.title().equals("NAVER") && isfirst_search) { // Main 화면
            // 검색, 검색 버튼 클릭
//            String jsCode = "document.getElementById('query').value = '"+ step1_keyWord +"';"+
            String jsCode = "document.getElementById('query').value = '김태희';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
//            WebviewContext.moveNdelay(10);
//            WebviewContext.delay(1);
        } else if (doc.title().endsWith(": 네이버 통합검색") && isfirst_search) {
            // 뉴스 클릭
            String jsCode = "document.getElementsByClassName('news_tit')[0].click()";
            WebviewContext.callJs(jsCode);
//            WebviewContext.delay(1);
        } else if ((!doc.title().endsWith(": 네이버 통합검색")) && isfirst_search) {
            // 구독 누르기
            String jsCode = "document.getElementsByClassName('_channel_add ofhd_float_subscribe_btn _CHANNEL_BUTTON _CHANNEL_ADD')[0].click()";
            String jsCode1 = "window.history.back()";

            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode1);

            isfirst_search = false;
        }
        else if (doc.title().endsWith(": 네이버 통합검색") && !isfirst_search) {
            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);

            resetS1();
        }
    }


}
