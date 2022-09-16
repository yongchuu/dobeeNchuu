package com.example.oreo2;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;

import org.apache.commons.httpclient.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    TextView textView_cookie;
    WebService webService;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//
//        try {
//            Object result = engine.eval("Math.min(2, 3)");
//
//            if (result instanceof Integer) {
//                System.out.println(result);
//            }
//        } catch (ScriptException e) {
//            System.err.println(e);
//        }

//        editText = findViewById(R.id.http_edittext);
        textView = findViewById(R.id.http_textview);
        textView_cookie = findViewById(R.id.http_textview2);
        webService = new WebService();

        Button button = findViewById(R.id.http_bttn);
        Button button2 = findViewById(R.id.http_bttn2);



        button2.setOnClickListener(view -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> urls = new ArrayList<>();
                    urls.add("https://www.naver.com");
                    urls.add("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=허그체어+학생+메쉬");
                    urls.add("https://search.shopping.naver.com/search/all?where=all&frm=NVSCTAB&query=허그체어+학생+메쉬");
                    urls.add("https://cr.shopping.naver.com/adcr.nhn?x=E6QFfv%2FUYMiZJDbiTOKZa%2F%2F%2F%2Fw%3D%3Dsotq%2Fzu%2FDB4WCs1H3CGkaNO4xSREZuYZ2WodnWLCEY9dBz1vuM2SOI7oibFli4j6TB%2Fh4nyf4W0boVT5npv%2BDtdr4LIQiYdxFziVUDc%2BiZ1sIAbztuhR26OIa22vl4jdBFrEj19yTrrJOs8nllN0%2Bqsc7NS5DH%2B%2BB0AnkucmYuzO7lcNxnWgwL8Q0owt3J7dmf9tprPlvE4kEDc%2FzhzDBRRENhMtcE%2B6r7RoGEohIfmX5wHP2qPLLPK3cmZXOPMRWw6ZjbtkbN7RD61dGUzg7G1qsmhWtsS26cBgiQoajA6qqWkQfXNpD2nBEcqIIKG23JS8AVWrm2%2FRtyPUiup%2Bx6cja5XIzAKN3O26KxyWVmNAAv2PkFiVSgPl9kuNpInbQxDV8eVY5PWFsmB4sA7yjsZ93EpgSMm4odyJu7HaFfCmNeqrFWKh%2FGVmgj6k5PLO5I8T7xUl%2FIAW3GNwslezDdZy6Cs%2BN4RObH8Z0RIvGtPALiM5oMty4B0COHADXy6PfHZyWBtTYLWsfHl%2B86pl486%2B6QpPjkV%2BwiJnQHbI1uIbwP2x4ZFBbrtL8oTn5x7%2BMkAlOKwPahCEyYd9E%2BlamjjnwuIA6tm%2Ff3ml3gW245wMJjxSqwKm39pf3hNiHBQEYzzDzwSPU74liSi8oO49JNR0cjwVm1BUYVknE7IEMpz4%3D&amp;nvMid=83676493016&amp;catId=50003299");

                    WebClient webClient = new WebClient();
                    webClient.setRedirectEnabled(true);
                    webClient.setJavaScriptEnabled(true);
                    webClient.setThrowExceptionOnFailingStatusCode(false);
                    webClient.setThrowExceptionOnScriptError(false);
                    webClient.setCssEnabled(false);

//                    webClient.setThrowExceptionOnScriptError(false);
//                    webClient.setCssEnabled(false);
//                    webClient.setJavaScriptEnabled(true);

//                    CookieManager cm = webClient.getCookieManager();
//                    Cookie cookie = new Cookie();
//                    cm.addCookie(cookie);

//                    GetTestProp2().forEach((k,v)->{
//                        webClient.addRequestHeader(k,v);
//                    });

                    urls.forEach(url->{
                        HtmlPage htmlPage=null;
                        try {
                            htmlPage = webClient.getPage(url);
                            printCookieln(htmlPage.getTitleText());

                        } catch (IOException e) {
                            e.printStackTrace();
                            printCookieln(e.toString());
                        }
                    });

                }
            }).start();
        });

//            button2.setOnClickListener(view -> {
//            AssetManager am = getResources().getAssets() ;
//            InputStream is = null ;
//
//            try {
//                is = am.open("env.rhino.js") ;
//
//                // TODO : use is(InputStream).
//
//            } catch (Exception e) {
//                e.printStackTrace() ;
//            }
//
//            if (is != null) {
//                try {
//                    is.close() ;
//                } catch (Exception e) {
//                    e.printStackTrace() ;
//                }
//            }
//
//            JsEngine je = new JsEngine(is.toString());
//            Object p[] =  new Object[] {"p1", "p2"};
//
//            String f1 = "function f1(a,b)\n" +
//                    "{\n" +
//                    "return a+b"+
//                    "}";
//
//            String f2 = "function f2(a)\n" +
//                    "{\n" +
//                    "return a+10"+
//                    "}";
//
//            je.addFunction(f1);
//            je.addFunction(f2);
//
//            je.callFunction("f1", p);
//
//            Object p2[] = new Object[]{19};
//            je.callFunction("f2", p2);
//        });

        button.setOnClickListener(view -> {
            // final String url_1 = "https://blog.naver.com/dduchelin/222042185772";
            // final String url_1 = "https://blog.naver.com/dduchelin/222031115434";   //제레미
            final String url_1 = "https://search.shopping.naver.com/search/all?where=all&frm=NVSCTAB&query=허그체어+컴퓨터";
//            final String url_1 = "https://blog.naver.com/dobee0101/222222434066";
            final String url_2 = "https://www.google.com";
            final String url_3 = "https://cswon.tistory.com/entry/%EC%9C%84%EB%A1%80-%ED%94%BC%EC%9E%90-%ED%8C%8C%EC%8A%A4%ED%83%80-%EB%A7%9B%EC%A7%91-%EB%9D%BC%EB%B2%A8%EB%A1%9CRavello";

            //Map<String, String> reqProp_1 = GetTestProp();
            Map<String, String> reqProp_1 = new HashMap<>();
            Log.i("Tag", "!!!start!!!");

            new Thread(new Runnable(){
                @Override
                public void run(){
                    AssetManager am = getResources().getAssets() ;
                    InputStream is = null ;

                    String envJs = GetJsEnvStr();
                    JsEngine je = new JsEngine(envJs);

                    int nowHtmlDepth = 0; // zero is init request
                    Queue<RequestPacket> todoQ = new LinkedList<>(); //int형 queue 선언, linkedlist 이용
                    todoQ.add(new RequestPacket(url_1, true, nowHtmlDepth));

                    while(!todoQ.isEmpty())
                    {
                        RequestPacket rp = todoQ.poll();
                        String _url = rp.url;

                        String resDoc = null;

                        try {
                            Log.d("reqGetUrl", _url);
                            resDoc = webService.sendGet(_url, reqProp_1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("reqException",e.toString());
                        }
//                        println("fst_res" + webService.getHTTP());

                        //.js를 get한 return이면?
                        // case 0
                        String ext = _url.substring(_url.lastIndexOf(".") + 1);

                        if(ext.equals("js")){
                            Log.d("Tag", "_url is ended with .js");
//                            javascriptCode.append(doc.toString());
                            je.addFunction(resDoc.toString());
                            continue;
                        }

                        Document doc = Jsoup.parse(resDoc.toString());
                        String ss = doc.outerHtml();
                        Log.d("Tag", ss);
                        String t = doc.title();

                        Elements eles = doc.getAllElements();
                        eles.forEach(element -> {
//                            String tmp = element.toString();
                            String _Tag = element.tag().toString();
                            Attributes attributes = element.attributes();
                            Map<String, String> att = new HashMap<>();

                            attributes.forEach(attribute -> {
                                att.put(attribute.getKey(), attribute.getValue());
                            });

                            //add rhino
                            if(_Tag.equals("script")){
                                //document.append
                            }
                        });
                        String exCode = "var script = document.createElement(\"script\");" +
                                "script.setAttribute(\"type\", \"text/javascript\");" +
                                "script.setAttribute(\"src\", \"https://ssl.pstatic.net/t.static.blog/mylog/versioning/Frameset-347491577_https.js\");" +
                                "script.setAttribute(\"charset\", \"UTF-8\");";
                        je.addFunction(exCode);
//                        doc.getElementById("script").attr("src");

                        Object p[] =  new Object[] {"script"};
                        je.callFunction("getElementsByTagName", p);




                        doc.location();
//                        doc.getElementById()
                        Elements allElements = doc.getAllElements();
//doc.body().appendChild()
                        //allElements.

                        //case 1
                        // android에서 api에서 호출 시 return되는 js 파싱을 위한
                        Elements scrLanJs = doc.select("script[language=JavaScript]");
                        if(!scrLanJs.isEmpty()) { //null값이 아니면 크롤링 실행
                            Log.d("Tag", "lan=js is not Empty, size : " + scrLanJs.size());
                            final int depth = nowHtmlDepth;
                            scrLanJs.forEach(el -> {
                                String jsCode = el.data();
                                Log.d("Tag jsCode", jsCode);

                                String parsed[] = jsCode.split("[\\(\\)]");
                                String parsed_url = parsed[1];
                                parsed_url = parsed_url.replace("\\", "");
                                parsed_url = parsed_url.replace("'", "");
                                Log.d("Tag parsed url", parsed_url);
                                todoQ.add(new RequestPacket(parsed_url, false, depth));
                            });
                        }

                        //case 2-1
                        // 두 번째 return으로 오는 html문서를 파싱하기 위한 select
                        // ex) <script type="text/javascript" src="https://ssl.pstatic.net/t.static.blog/mylog/versioning/JindoComponent-190469086_https.js" charset="utf-8"></script>
                        Elements srcEles = doc.select("script[type=text/javascript]");
                        if(!srcEles.isEmpty()) {
                            Log.d("Tag", "type=text/javascript is not Empty, size : " + srcEles.size());
                            final int depth = nowHtmlDepth;

                            //attr src first
                            srcEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                String _ext = srcPath.substring(srcPath.lastIndexOf(".") + 1);
                                if(srcPath != "" && _ext.equals("js")) {
//                                    Log.d("AddPath", srcPath);
//                                    todoQ.add(new RequestPacket(srcPath, false, depth));
                                    try {
                                        Map<String, String> empty = new HashMap<>();
                                        Log.d("reqGetUrl", srcPath);
                                        String retJsCode = webService.sendGet(srcPath, empty);
                                        je.addFunction(retJsCode);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("reqException",e.toString());
                                    }
                                }
                            });
                        }

                        //case 2-2
                        // 두 번째 return으로 오는 html문서를 파싱하기 위한 select
                        // ex)   <script type="text/javascript" charset="UTF-8">
                        //var photoContent="";
                        //var postContent="";
                        //...</script>
                        Elements srcEles_2 = doc.select("script[type=text/javascript]");
                        if(!srcEles_2.isEmpty()) {
                            Log.d("Tag", "type=text/javascript is not Empty, size : " + srcEles.size());
                            final int depth = nowHtmlDepth;

                            srcEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                //변수 = "" <- 이 패턴으로 이루어져있는 js파싱해서 하나하나 다 실행해줘야
                                if(srcPath == "") {
                                    // javascript를 쌓아두기
                                    Log.d("AddPath", srcPath);
                                    je.addFunction(_el.data());
                                }
                            });
                        }

                        //case 3
                        // 두 번째 return으로 오는 html문서를 파싱하기 위한 select
                        // ex) <script type="text/javascript" src="https://ssl.pstatic.net/t.static.blog/mylog/versioning/JindoComponent-190469086_https.js" charset="utf-8"></script>
                        //<iframe id="mainFrame" name="mainFrame" allowfullscreen="true" src="/PostView.naver?blogId=dobee0101&logNo=222222434066&redirect=Dlog&widgetTypeCall=true&directAccess=false" scrolling="auto"
                        // onload="oFramesetTitleController.start(self.frames['mainFrame'], self, sTitle);
                        // oFramesetTitleController.onLoadFrame();
                        // oFramesetUrlController.start(self.frames['mainFrame']);oFramesetUrlController.onLoadFrame()" allowfullscreen>
                        // </iframe>
                        Elements iframeEles = doc.select("iframe[type=mainFrame]");
                        if(!iframeEles.isEmpty()) {
                            Log.d("Tag", "type=text/javascript is not Empty, size : " + srcEles.size());
                            final int depth = nowHtmlDepth;
                            iframeEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                //변수 = "" <- 이 패턴으로 이루어져있는 js파싱해서 하나하나 다 실행해줘야
                                if(srcPath=="") {
                                    // javascript를 쌓아두기
                                    Log.d("AddPath", srcPath);
                                    je.addFunction(_el.data());
                                }
                                else {
                                    Log.d("AddPath", srcPath);
                                    todoQ.add(new RequestPacket(srcPath, true, depth+1));
                                }

                                String jsCode =null;
                                jsCode = _el.attr("onload");
                                if(jsCode!="") {
                                    je.addFunction(jsCode);
                                }
                            });
                        }


                    }
                }
            }).start();
        });
    }

    public HtmlPage getHtmlUnit(String url, WebClient webClient)
    {
        HtmlPage htmlPage=null;
//        String searchUrl = "https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=허그체어+컴퓨터+공부";
        try {
            htmlPage = webClient.getPage(url);
//            webClient.getCookieManager();
            printCookieln(htmlPage.getTitleText());

        } catch (IOException e) {
            e.printStackTrace();
            printCookieln(e.toString());
        }
        return htmlPage;
    }



    public String GetJsEnvStr(){
        String retStr = null;
        InputStream is;
        AssetManager am = getResources().getAssets() ;
        try {
            is = am.open("env.rhino.js") ;
            byte buf[] = new byte[is.available()] ;
            if (is.read(buf) > 0) {
                retStr = new String(buf) ;
            }

            is.close() ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }
        return retStr;
    }

    public Map<String, String> GetTestProp2()
    {
        String cookieStr = "NNB=YZPA4MWBFADGG; nx_ssl=2; SB_MODE=plusdeal; ASID=3d62cccb000001830e0078bf00016baf; JSESSIONID=FD836B497F38DE32EFF37150EC104AB4.jvm1; CBI_SES=PjT6fCsrasr6o0Tm/i49DWWsqxMAazREpUH2py5bnWfHReF5dhqCUVuFHfP8ZU0CZqcZ8fJSEP2btONPW1+ePshgQpzNw1wraf73LL9GRU8ZjHwly3eX4N8xiB6xxaMkqDp04QatlQjxQLZQ+mtbEO4BdvOhbLFKT1sXIVajgTiCHmXAqLv7UP5sN8R/E3R9u/IQFDsq/atwpbQ65LnOpykoaQwotsjMgP7lVLzIelA2lqfRLCkrPOkah3+tJD7MF3DCAlT/9MbKlmpRoD4IRW8ChsmJlNxhjSsZhn6D7tYpbpEageFTo7mbOpl+7pwWDXHWW5Bitv59R0XN8vzStLFUoi+khUsFf8w1ypHSwecw8X9Lr/Ecl33Fvo4+7jdMQ+WSKkz4BizRCWjCPMcXy8R6/JkDnlW1rwkRq8Yh3HVXkRRjcqbrPm8lGLGAZhcN; NFS=2; NM_TOP_PROMOTION=1; CBI_CHK=\"r5V0mf9uRUZHZ/vmLGy3ez7f4/k4aqWXL5o03eN68fqwOAwUjJMs8y7llUhljjULi3OhZcTCmq4mzUdgCHVl5GwIw9A9j8mbaJhee3UkrWUbdAmCpO0LmzdZrIWxv0dshMIs5zEfaVYvWyYVCkSNnNC0DYvMUGi21BhCYBK7Fd4=\"; nid_inf=1393432957; NID_AUT=xBiSHiMeoP7NUFKwO00VARKjIHiPbgRFSdIhizqX+kq0S3MmnL4IYnslwMyncihq; NID_JKL=Ks3wcANZ0wQvbHHZ0I/8LScyNkYzPBP5j21GOJliGng=; page_uid=hyeonwp0J1sssdIElMZssssstGG-034477; PM_CK_loc=f565d4a6f8b5ae26636fa1a2d206d77e1de1ead5af690fd24ee6c2a240a944db; NID_SES=AAABhDS49Ogs16vQSY3jC9ePIDPdZKmpXKmLW34wHY3ye3BEPtFjt8JX2mj8f6jkJ8ygr3Su7GwyZ69U4EBjo77f9B0vurFlCxDTFPHdZHF4H+Iw6xPfsf4cAyh+wg+zt3HnpBw0NlxdgqSfoMYewKhqi46w76b0POHYUuD/t1/1DF/q8mbTcTBWlCJqkOwKcmmBkoV1ro6NnwipnP2orb0MBp78UYmmEPIm8BAOO5FfxDULTDMdqHahKOWU4SddnpktehGmKb6RuFk3cKgUprm1VRdrNvg171ETl+JINd/YpXh5b7uFKDeUMQcta+tSDwNg5ij1lXJc0cxT7iS7BBJ+ZWUsuIORMYdS75a6VD1VMmd+eTQrrlHFC13iRgAHrk8ATKdHARR/63WoCdcSrcZZ+Hfw30XGbQHz9IkfZQncPu2eJgcrSDRxgfJCFle/W7PUWdWcs0vcmOQV7No2vqFniY0eyKWZtOfoHJSK/Hz6YAPj3nhDPu/3XgsRERZvS3y/dqxHo37aEQDvVT/q8BVRbRU=; BMR=s=1663336568178&r=https://news.naver.com/&r2=https://www.naver.com/";
        
        String[] cookieKV = cookieStr.split("; ");

        Map<String, String> reqProp_1 = new HashMap<>();
        for(String kv :cookieKV)
        {
            String[] _kv = kv.split("=");
            if(_kv.length==2)
                reqProp_1.put(_kv[0],_kv[1]);
            else
                reqProp_1.put(_kv[0],"");
        }

        return reqProp_1;
    }

    public Map<String, String> GetTestProp()
    {
        Map<String, String> reqProp_1 = new HashMap<>();
        reqProp_1.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        //        //reqProp_1.put("accept-encoding","gzip, deflate, br");
        reqProp_1.put("accept-language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        reqProp_1.put("cache-control","max-age=0");
        reqProp_1.put("cookie","NNB=YZPA4MWBFADGG; nx_ssl=2; page_uid=hwhpSwp0J1ZssvF8MIKssssstcR-061183; BMR=s=1661435254662&r=https%3A%2F%2Fm.blog.naver.com%2FPostView.naver%3FisHttpsRedirect%3Dtrue%26blogId%3Ddt3141592%26logNo%3D221226691809&r2=https%3A%2F%2Fwww.google.com%2F; JSESSIONID=9E791A7A6840B6ECD9981A4B9551F8C6.jvm1");
        reqProp_1.put("sec-ch-ua","\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"");
        reqProp_1.put("sec-ch-ua-mobile","?0");
        reqProp_1.put("sec-ch-ua-platform","macOS");
        reqProp_1.put("sec-fetch-dest","document");
        reqProp_1.put("sec-fetch-mode","navigate");
        reqProp_1.put("sec-fetch-site","none");
        reqProp_1.put("sec-fetch-user","?1");
        reqProp_1.put("upgrade-insecure-requests","1");
        reqProp_1.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        return reqProp_1;
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
