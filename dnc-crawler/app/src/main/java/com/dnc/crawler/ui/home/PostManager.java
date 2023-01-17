package com.dnc.crawler.ui.home;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


//
public class PostManager {
    public PostManager(){}

    String retStr;

    public void select_doProcess() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://20.196.196.177:8081/dnc/client/addcookie.do");
        ArrayList<NameValuePair> nameValues =
                new ArrayList<NameValuePair>();

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            nameValues.add(new BasicNameValuePair(
                    "clientId", URLDecoder.decode("112", "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "cookie", URLDecoder.decode("ddddd", "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "userAgent", URLDecoder.decode("uaua", "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "version", URLDecoder.decode("1", "UTF-8")));


            //HttpPost에 넘길 값을들 Set해주기
            post.setEntity(
                    new UrlEncodedFormEntity(
                            nameValues, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.e("Insert Log", ex.toString());
        }

        try {
            //설정한 URL을 실행시키기
            HttpResponse response = client.execute(post);
            //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
            Log.i("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetCookie() {
        String myResult = "";
        try {
            URL url = new URL("http://20.196.196.177:8081/dnc/client/getcookie.do");
            String result = "";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream outputStream = conn.getOutputStream();
//            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            Log.i("PHPRequest","No Problem");
            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            myResult = builder.toString();

            conn.disconnect();
        } catch (Exception ex) {
            Log.d("PostManager",ex.toString());
        }
    }

    public void SendCookie(String cookies) {
        String myResult = "";
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
//            URL url = new URL("http://korea-com.org/foxmann/lesson01.php");       // URL 설정
            URL url = new URL("http://20.196.196.177:8081/dnc/client/addcookie.do");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
//            buffer.append(cookies);
            buffer.append("clientId").append("=").append("1").append("&");                 // php 변수에 값 대입
            buffer.append("cookie").append("=").append(cookies).append("&");   // php 변수 앞에 '$' 붙이지 않는다
            buffer.append("userAgent").append("=").append("testUA").append("&");           // 변수 구분은 '&' 사용
            buffer.append("version").append("=").append("2");           // 변수 구분은 '&' 사용
//            buffer.append("subject").append("=").append(mySubject);
//            http://20.196.196.177:8081/dnc/client/addcookie.do?clientId=66666&cookie=madecookie&userAgent=android80
            try {
                OutputStream os = http.getOutputStream();
//                OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
                OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();
            } catch (Exception e) {
                Log.d("tag", e.toString());
            }
            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            myResult = builder.toString();                       // 전송결과를 전역 변수에 저장

//            ((TextView) (findViewById(R.id.textView))).setText(myResult);
//            Toast.makeText(MainActivity.this, "전송 후 결과 받음", 0).show();
        } catch (MalformedURLException e) {
            Log.d("PostManager",e.toString());
        } catch (IOException e) {
            Log.d("PostManager",e.toString());
        }
    }
}
