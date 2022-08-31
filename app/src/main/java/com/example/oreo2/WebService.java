package com.example.oreo2;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class WebService {

    static final String COOKIES_HEADER = "Set-Cookie";
    static final String COOKIE = "Cookie";
    static StringBuilder output = new StringBuilder();
    static StringBuilder output_cookie = new StringBuilder();
    static CookieManager msCookieManager = new CookieManager();
    private static int responseCode;
    private List<String> urls = new ArrayList<String>();

    WebService()
    {
        urls.add("http://www.google.com");
        urls.add("http://www.tistory.com");
        urls.add("http://www.google.com");
        urls.add("http://www.google.com");
        urls.add("http://www.google.com");
    }

    public static String sendPost(String requestURL, String urlParameters) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                conn.setRequestProperty(COOKIE ,
                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            if (urlParameters != null) {
                writer.write(urlParameters);
            }
            writer.flush();
            writer.close();
            os.close();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            setResponseCode(conn.getResponseCode());

            if (getResponseCode() == HttpsURLConnection.HTTP_OK) {

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    // HTTP GET request
    public static String sendGet(String url) throws Exception {
        output.setLength(0);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.setDoInput(true);

        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla");
        /*
         * /programming/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
         * Get Cookies form cookieManager and load them to connection:
         */
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            con.setRequestProperty(COOKIE ,
                    TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            Log.d("Tag", msCookieManager.getCookieStore().getCookies().toString());
        }

        /*
         * /programming/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
         * Get Cookies form response header and load them to cookieManager:
         */
        Map<String, List<String>> headerFields = con.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                output_cookie.append(cookie+"\n");
            }
        }

        int responseCode = con.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader((new InputStreamReader(con.getInputStream())));
            String line = null;
            while(true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                output.append(line+"\n");
            }
//            Log.d("Tag", output.toString());

//            Document doc = (Document) Jsoup.parse(output.toString());
//            Elements iframeEle = doc.select("iframe");
//
//            boolean isEmpty = iframeEle.isEmpty(); //빼온 값 null체크
//            Log.d("Tag", "iframeEle is Null? : " + isEmpty); //로그캣 출력
//
//            if(isEmpty == false) { //null값이 아니면 크롤링 실행
//                Log.d("Tag",iframeEle.toString());
//                //tem = temele.get(0).text().substring(5); //크롤링 하면 "현재온도30'c" 이런식으로 뽑아와지기 때문에, 현재온도를 잘라내고 30'c만 뽑아내는 코드
//            }

            reader.close();
            con.disconnect();
        } else{
            output.append("request state : " + responseCode);
//            println("request state : " + resCode);
        }

//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();

//        return response.toString();
        return output.toString();
    }

    public static void setResponseCode(int responseCode) {
        WebService.responseCode = responseCode;
        Log.i("Milad", "responseCode" + responseCode);
    }


    public static int getResponseCode() {
        return responseCode;
    }

    public static String getHTTP(){
        return output.toString();
    }

    public static String getCookie(){
        return output_cookie.toString();
    }
}