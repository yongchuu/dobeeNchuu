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
    boolean isGoing2Page = true;
    boolean isfirst_shopping = true;
    int shoppingState = 0;
    boolean isfirst_news = true;
    boolean randomFlag = true;
    double ptr = 0;
    int p = 0;


    @JavascriptInterface
    public void checkIP(String html){
        Document doc = Jsoup.parse(html);
        if (doc.title().equals("NAVER")) { // Main 화면
            // 검색
            String jsCode = "document.getElementById('query').value = '내 아이피';" +
                    "document.getElementsByClassName('sch_btn_search')[0].click();";
            WebviewContext.callJs(jsCode);
        }else if (doc.title().endsWith("통합검색")) { // Main 화면
            // 검색
            String jsCode = "window.history.back()";
            WebviewContext.callJs(jsCode);
            WebviewContext.delay(3);
            resetS1();
        }
    }

    @JavascriptInterface
    public void getHtml(String html){
        Document doc = Jsoup.parse(html);
        Log.d("step2","[title]" + doc.title());
        Log.d("html", doc.html());
        String t = doc.title();
        step1_keyWord = getSearchKeyword();

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
//            if(ptr < 0.2){
            goFoodBlog_k(doc);
//            }
//            else if(ptr < 0.4){
//                goShopping(doc);
//            }
//            else if(ptr < 1){
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
        WebviewContext.setSenarioTwoEnded(true);
        WebviewContext.cntUp();
    }
    private void resetS1()
    {
        isfirst_search = true;
        WebviewContext.setSenarioOneState(true);
        WebviewContext.cntUp();
        senario_step = 2;
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
//        if (doc.title().equals("NAVER") && isfirst_search) { // Main 화면
//            // 검색, 검색 버튼 클릭
////            String jsCode = "document.getElementById('query').value = '"+ step1_keyWord +"';"+
//            String jsCode = "document.getElementById('query').value = '김태희';"+
//                    "document.getElementsByClassName('sch_btn_search')[0].click();";
//            WebviewContext.callJs(jsCode);
////            WebviewContext.moveNdelay(10);
////            WebviewContext.delay(1);
//        } else if (doc.title().endsWith(": 네이버 통합검색") && isfirst_search) {
//            // 뉴스 클릭
//            String jsCode = "document.getElementsByClassName('news_tit')[0].click()";
//            WebviewContext.callJs(jsCode);
////            WebviewContext.delay(1);
//        } else if ((!doc.title().endsWith(": 네이버 통합검색")) && isfirst_search) {
//            // 구독 누르기
//            String jsCode = "document.getElementsByClassName('_channel_add ofhd_float_subscribe_btn _CHANNEL_BUTTON _CHANNEL_ADD')[0].click()";
//            String jsCode1 = "window.history.back()";
//
//            WebviewContext.callJs(jsCode);
//            WebviewContext.callJs(jsCode1);
//
//            isfirst_search = false;
//        }
//        else if (doc.title().endsWith(": 네이버 통합검색") && !isfirst_search) {
//            //                String jsCode = "document.getElementsByClassName('logo_link')[0].click()";
//            String jsCode = "window.history.back()";
//            //                WebviewContext.callJs(jsCode, 2);
//            WebviewContext.callJs(jsCode);
//
//            resetS1();
//        }



        if(isfirst_news) {
            isfirst_news = false;
            String jsCode = "document.getElementById('query').value = '"+ step1_keyWord +"';";
            WebviewContext.callJs(jsCode);
//                    WebviewContext.scrollMove();
//            WebviewContext.delay(3);
//                    WebviewContext.moveNdelay(10);
        }
        else if (doc.title().endsWith("뉴스검색") && !isfirst_news) {

            String jsCode = "document.getElementsByClassName('nav_link nav_news')[0].click()";
            String jsCode3 = "document.getElementsByClassName('cjs_link_dept')[0].click()";
            WebviewContext.callJs(jsCode);
            WebviewContext.callJs(jsCode3);

//                    WebviewContext.scrollMove();
//            WebviewContext.delay(3);
//                    WebviewContext.moveNdelay(10);
            resetS1();
        }else if (doc.title().endsWith("뉴스검색") && !isfirst_news) {
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



    private String getSearchKeyword()
    {
        List<String> locations = Arrays.asList("부산", "파주", "서면", "시청", "시흥", "강남", "양재", "이태원", "잠실", "마포");
        List<String> foods = Arrays.asList("삼겹살", "마라탕", "중식", "샐러드", "커피", "베이글", "아인슈페너", "목살", "육회", "가자미구이",
                "갈치구이",
                "고등어구이",
                "굴비구이",
                "꽁치구이",
                "꿩불고기",
                "닭갈비",
                "닭갈비",
                "닭꼬치",
                "더덕구이",
                "도미구이",
                "돼지갈비",
                "돼지갈비구이",
                "떡갈비",
                "병어구이",
                "불고기",
                "붕장어소금구이",
                "소곱창구이",
                "소불고기",
                "소양념갈비구이",
                "양념왕갈비",
                "양념장어구이",
                "임연수구이",
                "짚불구이곰장어",
                "햄버그스테이크",
                "황태구이",
                "훈제오리",
                "게국지",
                "곰치국",
                "굴국밥",
                "근대된장국",
                "김치국",
                "달걀국",
                "닭개장",
                "떡국",
                "떡만둣국",
                "만둣국",
                "맑은감자국",
                "매생이국",
                "무된장국",
                "미소된장국",
                "미역국",
                "미역오이냉국",
                "바지락조개국",
                "배추된장국",
                "뼈다귀해장국",
                "선지해장국",
                "선짓국",
                "소고기무국",
                "소고기미역국",
                "소머리국밥",
                "순대국",
                "시금치된장국",
                "시래기된장국",
                "쑥된장국",
                "아욱된장국",
                "어묵국",
                "오이냉국",
                "오징어국",
                "올갱이국",
                "우거지된장국",
                "우거지해장국",
                "우렁된장국",
                "탕국",
                "토란국",
                "홍합미역국",
                "황태해장국",
                "고추잡채",
                "깐풍기",
                "난자완스",
                "도토리묵말이",
                "라조기",
                "마파두부",
                "물회",
                "생선물회",
                "소고기샤브샤브",
                "양장피",
                "오징어물회",
                "오징어순대",
                "육회",
                "초당순두부",
                "탕수육",
                "팔보채",
                "팥빙수",
                "해파리냉채",
                "갓김치",
                "고들빼기",
                "깍두기",
                "깻잎김치",
                "나박김치",
                "동치미",
                "배추겉절이",
                "배추김치",
                "백김치",
                "부추김치",
                "열무김치",
                "열무얼갈이김치",
                "오이소박이",
                "총각김치",
                "파김치");

        List<String> endwords = Arrays.asList("소개팅", "맛집", "존맛", "인생맛집", "강추", "jmt");
        List<String> news_blogWords = Arrays.asList(
            "니케",
            "김새론",
            "흥국생명",
            "카투사",
            "천공",
            "PSG",
            "위메이드",
            "파월",
            "공습경보",
            "서유리",
            "손흥민 부상",
            "예비군",
            "나폴리",
            "도경완",
            "김태희",
            "한덕수",
            "용산경찰서장",
            "태양",
            "이란 사우디",
            "윤희근",
            "지진",
            "테이크오프",
            "한국시리즈",
            "FOMC",
            "영재",
            "다니엘기도회",
            "토끼머리띠",
            "커튼콜",
            "맨유",
            "박병화",
            "이상민",
            "박보연",
            "리버풀",
            "용산구청장 박희영",
            "서영석",
            "이찬원",
            "김원웅",
            "빅스마일데이",
            "인도 다리 붕괴",
            "이태원 토끼 머리띠",
            "할로윈",
            "이지한",
            "DRX",
            "BBC",
            "첼시",
            "도지코인",
            "연합뉴스",
            "본머스",
            "PSG",
            "이태원",
            "지진",
            "로또 1039회 당첨번호",
            "김태리",
            "나폴리",
            "소녀시대",
            "임주환",
            "마요르카");

        List<String> news_blogWords_end = Arrays.asList("근황", "정보", "info", "뭐지", "뉴스", "왜", "유튜브", "유튭", "동영상", "youtube");

        Random r = new Random();

        String loc = locations.get(r.nextInt(locations.size()));
        String food = foods.get(r.nextInt(foods.size()));
        String endword = endwords.get(r.nextInt(endwords.size()));
        String tmp = loc + " " + food + " " + endword;

        String news_prefix = news_blogWords.get(r.nextInt(news_blogWords.size()));
        String news_end = news_blogWords_end.get(r.nextInt(news_blogWords_end.size()));
        String newsBolgWord = news_prefix + " " + news_end;


        String retString = r.nextInt(2) ==0 ? tmp : newsBolgWord;

        return retString;
    }
    private String getSearchKeyword1()
    {
        List<String> news_blogWords = Arrays.asList(
                "니케",
                "김새론",
                "흥국생명",
                "카투사",
                "천공",
                "PSG",
                "위메이드",
                "파월",
                "공습경보",
                "서유리",
                "손흥민 부상",
                "예비군",
                "나폴리",
                "도경완",
                "김태희",
                "한덕수",
                "용산경찰서장",
                "태양",
                "이란 사우디",
                "윤희근",
                "지진",
                "테이크오프",
                "한국시리즈",
                "FOMC",
                "영재",
                "다니엘기도회",
                "토끼머리띠",
                "커튼콜",
                "맨유",
                "박병화",
                "이상민",
                "박보연",
                "리버풀",
                "용산구청장 박희영",
                "서영석",
                "이찬원",
                "김원웅",
                "빅스마일데이",
                "인도 다리 붕괴",
                "이태원 토끼 머리띠",
                "할로윈",
                "이지한",
                "DRX",
                "BBC",
                "첼시",
                "도지코인",
                "연합뉴스",
                "본머스",
                "PSG",
                "이태원",
                "지진",
                "로또 1039회 당첨번호",
                "김태리",
                "나폴리",
                "소녀시대",
                "임주환",
                "마요르카");

        List<String> news_blogWords_end = Arrays.asList("근황", "정보", "info", "뭐지", "뉴스", "왜", "유튜브", "유튭", "동영상", "youtube");

        Random r = new Random();
//
//        String loc = locations.get(r.nextInt(locations.size()));
//        String food = foods.get(r.nextInt(foods.size()));
//        String endword = endwords.get(r.nextInt(endwords.size()));
//        String tmp = loc + " " + food + " " + endword;

        String news_prefix = news_blogWords.get(r.nextInt(news_blogWords.size()));
        String news_end = news_blogWords_end.get(r.nextInt(news_blogWords_end.size()));
        String newsBolgWord = news_prefix + " " + news_end;


//        String retString = r.nextInt(2) ==0 ? tmp : newsBolgWord;

        return newsBolgWord;
    }
    private String getUA()
    {
        List<String> uaList = Arrays.asList("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                "Mozilla/5.0 (Linux; Android 9.0; SAMSUNG SM-F900U Build/PPR1.180610.011) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 10; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
        );
        Random r = new Random();
        return uaList.get(r.nextInt(uaList.size()));
    }
}
