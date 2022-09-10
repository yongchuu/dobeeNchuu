package com.example.oreo2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;

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

//        ContextFactory factory = ContextFactory.getGlobal();
//        Context cx = factory.enterContext();
//        cx.setOptimizationLevel(-1);// without 64kb limit
//        Scriptable shared = cx.initStandardObjects();
//        Scriptable scope = cx.newObject(shared);
////        cx.evaluateReader(scope, new java.io.FileReader("env.rhino.js"), "", 1, null);
//        String source = "";
//        source += "var div = document.createElement(\"div\");";
//        source += "div.innerHTML = \"korea\";";
//        source += "document.body.appendChild(div);";
//        source += "document.body.innerHTML;";
//        source += "console.log(div);";
//        Object result = cx.evaluateString(scope, source, "", 1, null);
//        System.out.println(result);//"<div>korea</div>"

//        ContextFactory f = new ContextFactory();
//        Context c = f.enterContext();
//        Scriptable s = c.initStandardObjects();
//
//        String js = "java.lang.System.out.println('Hello world!')";
//        c.evaluateString(s, js, null, 1, null);


//        JSContext context = new JSContext();
//        context.evaluateScript(js);
//        context.evaluateScript("question.vote(0);");

        button2.setOnClickListener(view -> {
            JsEngine je = new JsEngine();
            Object p[] =  new Object[] {"p1", "p2"};

            String f1 = "function f1(a,b)\n" +
                    "{\n" +
                    "return a+b"+
                    "}";

            String f2 = "function f2(a)\n" +
                    "{\n" +
                    "return a+10"+
                    "}";

            je.addFunction(f1);
            je.addFunction(f2);

            je.callFunction("f1", p);

            Object p2[] = new Object[]{19};
            je.callFunction("f2", p2);

        });

        button.setOnClickListener(view -> {
            // final String url_1 = "https://blog.naver.com/dduchelin/222042185772";
            // final String url_1 = "https://blog.naver.com/dduchelin/222031115434";   //제레미
            final String url_1 = "https://blog.naver.com/dobee0101/222222434066";
            final String url_2 = "https://www.google.com";
            final String url_3 = "https://cswon.tistory.com/entry/%EC%9C%84%EB%A1%80-%ED%94%BC%EC%9E%90-%ED%8C%8C%EC%8A%A4%ED%83%80-%EB%A7%9B%EC%A7%91-%EB%9D%BC%EB%B2%A8%EB%A1%9CRavello";

            Map<String, String> reqProp_1 = GetTestProp();
            Log.i("Tag", "!!!start!!!");

            int nowHtmlDepth = 0; // zero is init request

            new Thread(new Runnable(){
                @Override
                public void run(){
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
                        println("fst_res" + webService.getHTTP());
                        //Log.d("resDoc", resDoc);

                        Document doc =  Jsoup.parse(resDoc.toString());

                        //.js를 get한 return이면?
                        String ext = _url.substring(_url.lastIndexOf(".") + 1);
                        if(ext == "js"){
                            Log.d("Tag", "_url is .js");
                            javascriptCode.append(doc.toString());
                            continue;
                        }

                        //case 1
                        // android에서 api에서 호출 시 return되는 js 파싱을 위한
                        Elements scrLanJs = doc.select("script[language=JavaScript]");
                        if(!scrLanJs.isEmpty()) { //null값이 아니면 크롤링 실행
                            Log.d("Tag", "lan=js is not Empty, size : " + scrLanJs.size());
                            scrLanJs.forEach(el -> {
                                String jsCode = el.data();
                                Log.d("Tag jsCode", jsCode);

                                String parsed[] = jsCode.split("[\\(\\)]");
                                String parsed_url = parsed[1];
                                parsed_url = parsed_url.replace("\\", "");
                                parsed_url = parsed_url.replace("'", "");
                                Log.d("Tag parsed url", parsed_url);
                                todoQ.add(new RequestPacket(parsed_url, false, nowHtmlDepth));
                            });
                        }

                        //case 2
                        // 두 번째 return으로 오는 html문서를 파싱하기 위한 select
                        // ex) <script type="text/javascript" src="https://ssl.pstatic.net/t.static.blog/mylog/versioning/JindoComponent-190469086_https.js" charset="utf-8"></script>
                        Elements srcEles = doc.select("script[type=text/javascript]");
                        if(!srcEles.isEmpty()) {
                            Log.d("Tag", "type=text/javascript is not Empty, size : " + srcEles.size());
                            srcEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                //변수 = "" <- 이 패턴으로 이루어져있는 js파싱해서 하나하나 다 실행해줘야
                                if(srcPath=="") {
                                    // javascript를 쌓아두기
                                    Log.d("AddPath", srcPath);
                                    javascriptCode.append(_el.data());
                                }
                                else {
                                    Log.d("AddPath", srcPath);
                                    todoQ.add(new RequestPacket(srcPath, false, nowHtmlDepth));
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
                            iframeEles.forEach(_el -> {
                                String srcPath = null;
                                srcPath = _el.attr("src");
                                //변수 = "" <- 이 패턴으로 이루어져있는 js파싱해서 하나하나 다 실행해줘야
                                if(srcPath=="") {
                                    // javascript를 쌓아두기
                                    Log.d("AddPath", srcPath);
                                    javascriptCode.append(_el.data());
                                }
                                else {
                                    Log.d("AddPath", srcPath);
                                    todoQ.add(new RequestPacket(srcPath, false, nowHtmlDepth));
                                }
                            });
                        }


                    }
                }
            }).start();
        });
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
