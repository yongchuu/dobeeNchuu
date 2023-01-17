package com.dnc.crawler.ui.home;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KeywordManager {
    public KeywordManager(){}


    public String getSearchKeyword()
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
    public String getSearchKeyword1()
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

}
