package com.example.oreo2;


public class RequestPacket {
    public RequestPacket(String _url, boolean _isHtml, int _d){
        url = _url;
        isHTML = _isHtml;
        depth = _d;
    }
    int depth;
    String url;
    boolean isHTML;


    // cookie, request header도 추가해야하나?
}
