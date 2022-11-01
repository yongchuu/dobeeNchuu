package com.dnc.crawler.ui.home;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomeJavascriptInterface {
    String step1_keyWord = "강남 삼겹살 맛집";
//    String keyWord = "허그체어 노블헤드 게이밍";
    String keyWord = "허그체어 학생 컴퓨터";
    String Mid = "1234";
    int senario_step = 1;
    boolean isfirst_search = true;
    boolean isfirst_shopping = true;
    boolean isfirst_news = true;
    boolean randomFlag = true;
    double ptr = 0;

    private String getSearchKeyword()
    {
        List<String> locations = Arrays.asList("부산", "파주", "서면", "시청", "시흥", "강남", "양재", "이태원", "잠실", "마포");
        List<String> foods = Arrays.asList("삼겹살", "마라탕", "중식", "샐러드", "커피", "베이글", "아인슈페너", "목살", "육회");
        List<String> endwords = Arrays.asList("소개팅", "맛집", "존맛", "인생맛집", "강추", "jmt");

        Random r = new Random();

        String loc = locations.get(r.nextInt(locations.size()));
        String food = foods.get(r.nextInt(foods.size()));
        String endword = endwords.get(r.nextInt(endwords.size()));

        String tmp = loc + " " + food + " " + endword;

        return tmp;
    }

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        step1_keyWord = getSearchKeyword();

        if(senario_step==1) {
            Random randomGenerator = new Random();

//            if(randomFlag) {
//                randomFlag = false;
//                ptr = randomGenerator.nextDouble();
//            }

            ptr = 0.1;
            if(ptr < 0.2){
                goFoodBlog(doc);
            }
            else if(ptr < 0.4){
                goShopping(doc);
            }
            else if(ptr < 1){
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
    @JavascriptInterface
    public void getHtml_kakao(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        step1_keyWord = getSearchKeyword();

        if(senario_step==1) {
            Random randomGenerator = new Random();
//            if(randomFlag) {
//                randomFlag = false;
//                ptr = randomGenerator.nextDouble();
//            }

            ptr = 0.1;
            if(ptr < 0.2){
                goFoodBlog_k(doc);
            }
            else if(ptr < 0.4){
                goShopping(doc);
            }
            else if(ptr < 1){
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




    private void clickTarget_n(Document doc)
    {
        if (doc.title().equals("NAVER")) { // Main 화면
            String jsCode = "javascript:document.getElementById('query').value = '" + keyWord + "';" +
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
    //            WebviewContext.getWebview().loadUrl("javascript:" + jsCode);
        } else if (doc.title().endsWith(": 네이버 통합검색")) {
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
        } else if (doc.title().endsWith("네이버쇼핑")) {
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
            resetS2();
        } else {
    //                } else if(doc.body().className().contains("smartstore")){
            //not implements yet
    //                    isfirst_search=true;
    //                    senario_step = 1;
    //                    WebviewContext.setSenarioState(true);
    //                    WebviewContext.cntUp();
    //                    WebviewContext.goHome(30);

            //not implements yet
        }
    }
    private void resetS2()
    {
        isfirst_search = true;
        senario_step = 1;
        WebviewContext.setSenarioTwoState(true);
        WebviewContext.cntUp();
    }
    private void resetS1()
    {
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        senario_step = 1;
        randomFlag = true;
    }

    private void goFoodBlog(Document doc)
    {
        if (doc.title().equals("NAVER") && isfirst_search) { // Main 화면
            // 검색
            String jsCode = "document.getElementById('query').value = '"+ step1_keyWord +"';"+
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
//            WebviewContext.moveNdelay(10);
//            WebviewContext.delay(1);
        } else if (doc.title().endsWith(": 네이버 통합검색") && isfirst_search) {
            // 맛집 클릭
            //String jsCode = "document.getElementsByClassName('tzwk0')[0].click()";
            String jsCode = "document.getElementsByClassName('api_txt_lines total_tit _cross_trigger')[0].click()";
            WebviewContext.callJs(jsCode);
//            WebviewContext.delay(1);
        } else if ((doc.title().endsWith("플레이스") || doc.title().endsWith("블로그"))
                && isfirst_search) {
            // 뒤로가기
            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);
//            WebviewContext.delay(1);
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

    private void goFoodBlog_k(Document doc)
    {
        boolean blogClickFlag = false;
        if (doc.title().equals("Daum") && isfirst_search && !blogClickFlag) { // Main 화면
            // 검색
            blogClickFlag = true;
//            String jsCode = "document.getElementById('q').value = 'TEST'\n"+
////                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()\n"+
//                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()";
//             WebviewContext.callJs(jsCode);
            String jsCode ="document.getElementsByClassName('tab_menu')[0].children[0].children[0].click()";
            WebviewContext.callJs(jsCode);
//            WebviewContext.moveNdelay(10);
//            WebviewContext.delay(1);
        } else if (doc.title().endsWith("Daum 검색") && isfirst_search) {
            int blogIdx = 2;
            blogClickFlag = true;
            // 맛집 클릭
//            String jsCode = "\n" +
//                    "for(var value of document.getElementsByClassName('tab_tit'))\n" +
//                    "{\n" +
//                    "        if(value.text=='블로그'){\n" +
//                    "\t\tvalue.click();\n" +
//                    "        }\n" +
//                    "};";
//            String jsCode2 ="document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[" + blogIdx + "].children[0].click()";
////            document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[3].children[0].innerText.split('\n')[0]
//            WebviewContext.callJs(jsCode);
//            WebviewContext.callJs(jsCode2);
////            WebviewContext.delay(1);
            String jsCode = "\n" +
                    "for(var value of document.getElementsByClassName('tab_tit'))\n" +
                    "{\n" +
                    "        if(value.text=='블로그'){\n" +
                    "\t\tvalue.click();\n" +
                    "        }\n" +
                    "};\n" +
                    "document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[" + blogIdx + "].children[0].click()";
//            document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[3].children[0].innerText.split('\n')[0]
            WebviewContext.callJs(jsCode);
//            WebviewContext.callJs(jsCode2);
//            WebviewContext.delay(1);

        } else if (blogClickFlag && isfirst_search) {
            // 뒤로가기
            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);
//            WebviewContext.delay(1);
            isfirst_search = false;
        }
        else if (doc.title().endsWith("Daum 검색") && !isfirst_search) {
            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
            WebviewContext.callJs(jsCode);

            resetS1();
        }
    }

    private void goShopping(Document doc)
    {
        if(isfirst_shopping) {
            isfirst_shopping = false;
            // 한 페이지 내에서 다 이루어짐
            // 쇼핑 라이브 클릭 -> 라이브 영상 시청
            String jsCode = "document.getElementsByClassName('nav_link nav_shop_live')[0].click()";
            String jsCode2 = "document.getElementsByClassName('clc_link_live _MM_SHOP_LIVE_NATIVE_VIEWER')[0].click()";
            String jsCode3 = "document.getElementsByClassName('cb_link')[0].click()";
            WebviewContext.callJs(jsCode);
//                    WebviewContext.moveNdelay(10);
            WebviewContext.callJs("window.setTimeout(() => console.log(\"after\"), 10000)");
            WebviewContext.callJs(jsCode3);
//                    WebviewContext.moveNdelay(10);
        }
        else if (doc.title().endsWith("쇼핑라이브") && !isfirst_shopping) {
//                    WebviewContext.moveNdelay(10);
            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);

            WebviewContext.callJs(jsCode);
            resetS1();
        }
    }

    private void goNewsPage(Document doc)
    {
        if(isfirst_news) {
            isfirst_news = false;
            // 한 페이지 내에서 다 이루어짐
            // 쇼핑 라이브 클릭 -> 라이브 영상 시청
            String jsCode = "document.getElementsByClassName('nav_link nav_news')[0].click()";
            String jsCode3 = "document.getElementsByClassName('cjs_link_dept')[0].click()";
            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode3);

//                    WebviewContext.scrollMove();
            WebviewContext.delay(3);
//                    WebviewContext.moveNdelay(10);
        }
        else if (!isfirst_news) {
            isfirst_news = true;
            Elements metaTags = doc.getElementsByTag("meta");
//                    WebviewContext.scrollMove();
            WebviewContext.delay(5);
//                    boolean findFlag = false;
//                    for (Element metaTag : metaTags) {
//                        String content = metaTag.attr("twitter:site");
//                        if(content == "네이버 뉴스") {
//                            findFlag = true;
//                            break;
//                        }
//                    }
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode);
            resetS1();
        }
    }

}
