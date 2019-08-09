package com.GUI.Entity;

public class URLData {

    public String url;

    public String data;

    public URLData(String url, String data){
        this.url = url;
        this.data = data;
    }

    @Override
    public String toString() {
        return "url='" + url + ",   ------------  data=" + data;
    }
}
