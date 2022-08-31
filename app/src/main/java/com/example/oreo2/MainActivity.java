package com.example.oreo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import java.util.LinkedList; //import
import java.util.Queue; //import


public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    TextView textView_cookie;
    WebService webService;
    Handler handler = new Handler();
    StringBuilder javascriptCode = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editText = findViewById(R.id.http_edittext);
        textView = findViewById(R.id.http_textview);
        textView_cookie = findViewById(R.id.http_textview2);
        webService = new WebService();

        Button button = findViewById(R.id.http_bttn);
        Button button2 = findViewById(R.id.http_bttn2);

        button.setOnClickListener(view -> {

//        final String url_1 = "https://blog.naver.com/dduchelin/222042185772";
        final String url_1 = "https://blog.naver.com/dduchelin/222031115434";   //제레미
//            final String url_1 = "https://blog.naver.com/dobee0101/222222434066";
        Map<String, String> reqProp_1 = new HashMap<>();
//        reqProp_1.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
////        //reqProp_1.put("accept-encoding","gzip, deflate, br");
//        reqProp_1.put("accept-language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
//        reqProp_1.put("cache-control","max-age=0");
//        reqProp_1.put("cookie","NNB=YZPA4MWBFADGG; nx_ssl=2; page_uid=hwhpSwp0J1ZssvF8MIKssssstcR-061183; BMR=s=1661435254662&r=https%3A%2F%2Fm.blog.naver.com%2FPostView.naver%3FisHttpsRedirect%3Dtrue%26blogId%3Ddt3141592%26logNo%3D221226691809&r2=https%3A%2F%2Fwww.google.com%2F; JSESSIONID=9E791A7A6840B6ECD9981A4B9551F8C6.jvm1");
//        reqProp_1.put("sec-ch-ua","\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"");
//        reqProp_1.put("sec-ch-ua-mobile","?0");
//        reqProp_1.put("sec-ch-ua-platform","macOS");
//        reqProp_1.put("sec-fetch-dest","document");
//        reqProp_1.put("sec-fetch-mode","navigate");
//        reqProp_1.put("sec-fetch-site","none");
//        reqProp_1.put("sec-fetch-user","?1")    ;
//        reqProp_1.put("upgrade-insecure-requests","1");
//        reqProp_1.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
            Log.d("Tag", "!!!start!!!");
            new Thread(new Runnable(){
                @Override
                public void run(){
                    Queue<String> todoQ = new LinkedList<>(); //int형 queue 선언, linkedlist 이용
                    todoQ.add(url_1);

                    while(!todoQ.isEmpty())
                    {
                        String _url = todoQ.poll();
                        String resDoc = null;

                        try {
                            Log.d("reqGet", _url);
                            resDoc = webService.sendGet(_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("reqException",e.toString());
                        }
                        println("fst_res" + webService.getHTTP());
                        Log.d("getUrl", resDoc);

                        Document doc =  Jsoup.parse(resDoc.toString());

                        // android에서 api에서 호출 시 return되는 js 파싱을 위한
                        Elements scrLanJs = doc.select("script[language=JavaScript]");
                        if(!scrLanJs.isEmpty()) { //null값이 아니면 크롤링 실행
                            Log.d("Tag", "lan=js is not Empty" + scrLanJs.size());
                            scrLanJs.forEach(el -> {
                                String jsCode = el.data();
                                Log.d("Tag", jsCode);

                                String parsed[] = jsCode.split("[\\(\\)]");
                                String parsed_url = parsed[1];
                                parsed_url = parsed_url.replace("\\", "");
                                parsed_url = parsed_url.replace("'", "");
                                Log.d("Tag", parsed_url);
                                todoQ.add(parsed_url);
                            });
                        }

                        // 두 번째 return으로 오는 html문서를 파싱하기 위한 select
                        // ex) <script type="text/javascript" src="https://ssl.pstatic.net/t.static.blog/mylog/versioning/JindoComponent-190469086_https.js" charset="utf-8"></script>
                        Elements srcEles = doc.select("script[type=text/javascript]");
                        if(!srcEles.isEmpty()) {
                            Log.d("Tag", "type=text/javascript is not Empty" + srcEles.size());
                            srcEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                if(srcPath=="") {
                                    // javascript를 쌓아두기
                                    javascriptCode.append(_el.data());
                                }
                                else {
                                    Log.d("AddPath", srcPath);
                                    todoQ.add(srcPath);
                                }

                            });
                        }
                    }
                }
            }).start();
        });

    }


    public String GetProp()
    {

        return "test";
    }

    public void println(final String data){
        handler.post(new Runnable(){
            public void run(){

                textView.append(data+"\n");
            }
        });
    }

    public void printCookieln(final String data){
        handler.post(new Runnable(){
            public void run(){
                textView_cookie.append(data+"\n");
            }
        });
    }
}
