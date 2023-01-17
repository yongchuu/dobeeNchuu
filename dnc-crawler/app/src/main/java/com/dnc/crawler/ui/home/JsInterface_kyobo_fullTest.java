
package com.dnc.crawler.ui.home;
//package selendroid;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.dnc.crawler.ui.home.HomeFragment.shuffle;

import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

import static com.dnc.crawler.ui.home.HomeFragment.shuffle;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dnc.crawler.ui.home.webview.WebviewContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

public class JsInterface_kyobo_fullTest {

    String step1_keyWord = "삼국지";

    String keyWord = "허그체어 사무실 책상 컴퓨터 학생 메쉬 의자 노블헤드 화이트 DH100HMB";
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

        step1_keyWord = km.getSearchKeyword1();

        go_kyo(doc);
    }

    private void go_kyo(Document doc) {
        if (doc.title().endsWith("도서쇼핑몰")){
            //'교보문고 | 대한민국 최고의 도서쇼핑몰'
            // 앱 설치 팝업 끄기
            String jsCode = "document.getElementsByClassName('btn_burst_close')[0].click();";
            WebviewContext.callJs(jsCode, delayTime_s);

            // 광고 팝업 끄기
            String jsCode2 = "document.getElementsByClassName('btn_main_pop')[0].click();";
            WebviewContext.callJs(jsCode2, delayTime_s);

            // 돋보기 눌려서 product.kyobobook.co.kr로 진입
            String jsCode3 = "document.getElementsByClassName('btn_gnb_search')[0].click();";
            WebviewContext.callJs(jsCode3, delayTime_s);
        }
        else if(!doc.title().endsWith("도서쇼핑몰")) {
            //<초등 똑똑한 하루 시리즈>이벤트 | 이벤트 – 교보문고'
//            ClipboardManager clipboard = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
//            }
//            ClipData clip = ClipData.newPlainText("label", "Text to copy");
//            clipboard.setPrimaryClip(clip);

            // 검색하기 전 단계로 진입하기
            String jsCode = "document.getElementsByClassName('btn_header_search')[0].click();";// +
//                    "document.getElementsByClassName('ip_gnb_search')[0].value = '삼국지';";
//                    "$('input[id=searchKeyword]')[0].value = '사설';";
            WebviewContext.callJs(jsCode);

            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_B);

            // 검색어 입력하기
//            String jsCode2 = "document.getElementsByClassName('ip_gnb_search')[0].value = '삼국지';";
//            WebviewContext.callJs(jsCode2, 10);
//
//            // 돋보기 클릭
//            String jsCode3 = "document.getElementsByClassName('btn_gnb_search')[0].click();";
//            WebviewContext.callJs(jsCode3, delayTime_s);

        } else if(doc.title().endsWith("교보문고"))
        {
            // '교보문고'
            if(senario_step==1) {
                // 교보문고 | 대한민국 최고의 도서쇼핑몰
                senario_step = 2;
                String jsCode = "document.getElementsByClassName('btn_gnb_search')[0].click();";
                //"document.getElementsByClassName('ip_gnb_search')[0].value = '삼국지';";
                WebviewContext.callJs(jsCode, delayTime_s);
                //"document.getElementsByClassName('btn_gnb_search')[0].click()";



//                driver.find_element_by_xpath('//*[@id="searchMainKeyword"]').click()
//                driver.find_element_by_xpath('//*[@id="searchKeyword"]').send_keys('검색할 문장')
//                driver.find_element_by_xpath('//*[@id="searchKeyword"]').send_keys(Keys.RETURN)
            } else if(senario_step==2){
                String jsCode = "document.getElementsByClassName('ico_ksg ico_search')[0].click();";
                WebviewContext.callJs(jsCode, delayTime_s);
            }
        }
    }

    private void goFoodBlog_k(Document doc)
    {
        boolean blogClickFlag = false;
        if (doc.title().equals("Daum") && isfirst_search && !blogClickFlag) { // Main 화면
            // 검색
            blogClickFlag = true;
            String jsCode = "document.getElementById('q').value = 'TEST'\n"+
//                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()\n"+
                    "document.getElementsByClassName('ico_ksg ico_search')[0].click()";
            WebviewContext.callJs(jsCode);
//            String jsCode ="document.getElementsByClassName('tab_menu')[0].children[0].children[0].click()";
            WebviewContext.callJs(jsCode);
//            WebviewContext.moveNdelay(10);
//            WebviewContext.delay(1);
        }
//        else if (doc.title().endsWith("Daum 검색") && isfirst_search) {
//            int blogIdx = 2;
//            blogClickFlag = true;
//            // 맛집 클릭
////            String jsCode = "\n" +
////                    "for(var value of document.getElementsByClassName('tab_tit'))\n" +
////                    "{\n" +
////                    "        if(value.text=='블로그'){\n" +
////                    "\t\tvalue.click();\n" +
////                    "        }\n" +
////                    "};";
////            String jsCode2 ="document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[" + blogIdx + "].children[0].click()";
//////            document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[3].children[0].innerText.split('\n')[0]
////            WebviewContext.callJs(jsCode);
////            WebviewContext.callJs(jsCode2);
//////            WebviewContext.delay(1);
//            String jsCode = "\n" +
//                    "for(var value of document.getElementsByClassName('tab_tit'))\n" +
//                    "{\n" +
//                    "        if(value.text=='블로그'){\n" +
//                    "\t\tvalue.click();\n" +
//                    "        }\n" +
//                    "};\n" +
//                    "document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[" + blogIdx + "].children[0].click()";
////            document.getElementsByClassName('compo-fulltext ty_tit2')[0].childNodes[1].children[3].children[0].innerText.split('\n')[0]
//            WebviewContext.callJs(jsCode);
////            WebviewContext.callJs(jsCode2);
////            WebviewContext.delay(1);
//
//        } else if (blogClickFlag && isfirst_search) {
//            // 뒤로가기
//            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
//            String jsCode = "window.history.back()";
//            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
////            WebviewContext.delay(1);
//            isfirst_search = false;
//        }
//        else if (doc.title().endsWith("Daum 검색") && !isfirst_search) {
//            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
//            String jsCode = "window.history.back()";
//            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
//
//            resetS1();
//        }
    }
    private void goFoodBlog(Document doc)
    {
        int delayTime_s = 5;
        String t = doc.title();

//        TextView LogTxt = binding.textView;
//        LogTxt.setText("repeatCnt : " + WebviewContext.getCnt());

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
        } else if (((doc.title().endsWith("플레이스") || doc.title().endsWith("블로그")) &&
                !doc.title().endsWith(": 네이버 통합검색"))
                && isfirst_search) {
            // 뒤로가기
            //                String jsCode = "document.getElementsByClassName('DDfpb')[0].click()";
            String jsCode = "window.history.back()";
            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
            WebviewContext.delay(70);
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
        senario_step = 1;
        WebviewContext.setSenarioTwoEnded(true);
        WebviewContext.cntUp();
    }
    private void resetS1()
    {
        isGoingToNewsPage = true;
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        senario_step = 2;
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
            WebviewContext.delay(70);
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